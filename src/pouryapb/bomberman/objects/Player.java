package pouryapb.bomberman.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

import pouryapb.bomberman.framework.Animation;
import pouryapb.bomberman.framework.GameObject;
import pouryapb.bomberman.framework.ID;
import pouryapb.bomberman.framework.Texture;
import pouryapb.bomberman.window.Game;
import pouryapb.bomberman.window.Game.STATE;
import pouryapb.bomberman.window.HUD;
import pouryapb.bomberman.window.Handler;

/**
 * player object with many animations :/
 * 
 * @author Pourya
 *
 */

public class Player extends GameObject {

	private int width = 32, height = 32;

	/**
	 * to detect collisions with other game objects
	 */
	Handler handler;

	/**
	 * textures of the player
	 */
	Texture tex = Game.getInstance();

	/**
	 * all player animations going down, right, up or left
	 */
	private Animation playerWalkDown, playerWalkUp, playerWalkLeft, playerWalkRight;

	/**
	 * to detect witch one of players textures should be loaded when its not moving
	 * 0 = down, 1 == up, 2 = right, 3 = left
	 */
	private int facing = 0;

	/**
	 * Heads Up Display health an timer on left top corner of the screen
	 */
	private HUD hud;

	/**
	 * to not die at once when moving in a bombs blast
	 */
	private boolean coolDown = false;

	/**
	 * bombs limits
	 */
	private int bombLimit = 2;

	/**
	 * to change the STATE of the game when game is over
	 */
	private Game game;

	/**
	 * range of fire of players bombs
	 */
	private int range = 2;

	/**
	 * maximum range of bomb fires
	 */
	private final int maxRange = 8;

	/**
	 * ranges can go up with special items in game and this lasts 40 seconds this
	 * variable is to check the time
	 */
	private Timer rangeTimer;

	/**
	 * time left for the bonus bomb range
	 */
	private int rangeTimeLeft;

	/**
	 * for debugging purposes
	 */
	private boolean rangeTimerStarted = false;

	public Player(int x, int y, ID id, Handler handler, HUD hud, Game game) {
		super(x, y, id);
		this.handler = handler;
		this.hud = hud;
		this.game = game;

		playerWalkDown = new Animation(8, tex.player[1], tex.player[2]);
		playerWalkUp = new Animation(8, tex.player[4], tex.player[5]);
		playerWalkRight = new Animation(8, tex.player[7], tex.player[8]);
		playerWalkLeft = new Animation(8, tex.player[10], tex.player[11]);
	}

	public void tick() {
		x += velX;
		y += velY;

		// detecting which animation to run
		if (velX > 0)
			facing = 2;
		if (velX < 0)
			facing = 3;
		if (velY > 0)
			facing = 0;
		if (velY < 0)
			facing = 1;

		// x = Game.clamp(x, 0, Game.WIDTH - 38);
		// y = Game.clamp(y, 35, Game.HEIGHT - 60);

		collision();

		// player died
		if (hud.getHealth() == 0) {
			handler.object.clear();
			game.gameState = STATE.DEAD;
		}

		// are all enemies dead?
		int enemyCount = 0;
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.ENEMY_RED || handler.object.get(i).getId() == ID.ENEMY_YELLOW) {
				enemyCount++;
			}
		}

		// player won
		if (enemyCount == 0 && Game.spawned && hud.getHealth() > 0) {
			handler.object.clear();
			game.gameState = STATE.WIN;
			hud.setHelath(2);
		}

		// for not dying when running in a bomb blast
		if (coolDown) {
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {

				public void run() {
					coolDown = false;
					timer.cancel();
					timer.purge();
					return;
				}
			}, 700, 1);
		}

		playerWalkDown.runAnimation();
		playerWalkUp.runAnimation();
		playerWalkLeft.runAnimation();
		playerWalkRight.runAnimation();
	}

	private void collision() {

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			// collision with blocks
			if (tempObject.getId() == ID.BRICK_WALL || tempObject.getId() == ID.STONE_WALL) {

				// collision code

				// Top
				if (getBoundsTop().intersects(tempObject.getBounds())) {
					y = (int) tempObject.getBounds().getY() + 32;
				}
				// Bottom
				if (getBoundsButtom().intersects(tempObject.getBounds())) {
					y = (int) tempObject.getBounds().getY() - 32;
				}
				// Right
				if (getBoundsRight().intersects(tempObject.getBounds())) {
					x = (int) tempObject.getBounds().getX() - 32;
				}
				// Left
				if (getBoundsLeft().intersects(tempObject.getBounds())) {
					x = (int) tempObject.getBounds().getX() + 32;
				}
			}

			// collision with bomb
			if (tempObject.getId() == ID.BOMB) {
				if (getBounds().intersects(tempObject.getBounds())) {
					if (!((Bomb) tempObject).getPlayerIn()) {
						// Top
						if (getBoundsTop().intersects(tempObject.getBounds())) {
							y = (int) tempObject.getBounds().getY() + 32;
						}
						// Bottom
						if (getBoundsButtom().intersects(tempObject.getBounds())) {
							y = (int) tempObject.getBounds().getY() - 32;
						}
						// Right
						if (getBoundsRight().intersects(tempObject.getBounds())) {
							x = (int) tempObject.getBounds().getX() - 32;
						}
						// Left
						if (getBoundsLeft().intersects(tempObject.getBounds())) {
							x = (int) tempObject.getBounds().getX() + 32;
						}
					}
				} else {
					((Bomb) tempObject).setPlayerIn(false);
				}
			}

			// collision with bomb blast
			if (tempObject.getId() == ID.BLAST) {
				if (getBounds().intersects(tempObject.getBounds())) {
					if (!((Blast) tempObject).getHit() && !coolDown) {
						hud.setHelath(hud.getHealth() - 1);
						((Blast) tempObject).setHit(true);
						coolDown = true;
					}
				}
			}
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public Rectangle getBoundsButtom() {
		return new Rectangle((int) x + (width / 2) - ((width / 2) / 2), (int) y + height / 2, width / 2, height / 2);
	}

	public Rectangle getBoundsTop() {
		return new Rectangle((int) x + (width / 2) - ((width / 2) / 2), (int) y, width / 2, height / 2);
	}

	public Rectangle getBoundsRight() {
		return new Rectangle((int) x + width - 5, (int) y + 5, 5, height - 10);
	}

	public Rectangle getBoundsLeft() {
		return new Rectangle((int) x, (int) y + 5, 5, height - 10);
	}

	public void render(Graphics g) {

		// the right animation when moving
		if (velX != 0 || velY != 0) {
			if (velX > 0)
				playerWalkRight.drawAnimation(g, (int) x, (int) y);
			if (velX < 0)
				playerWalkLeft.drawAnimation(g, (int) x, (int) y);
			if (velY > 0)
				playerWalkDown.drawAnimation(g, (int) x, (int) y);
			if (velY < 0)
				playerWalkUp.drawAnimation(g, (int) x, (int) y);
		}
		// the right texture when not moving
		else {
			if (facing == 0)
				g.drawImage(tex.player[0], (int) x, (int) y, null);
			if (facing == 1)
				g.drawImage(tex.player[3], (int) x, (int) y, null);
			if (facing == 2)
				g.drawImage(tex.player[6], (int) x, (int) y, null);
			if (facing == 3)
				g.drawImage(tex.player[9], (int) x, (int) y, null);
		}
	}

	public void setBombLimit(int bombLimit) {
		this.bombLimit = bombLimit;
	}

	public int getBombLimit() {
		return bombLimit;
	}

	/**
	 * 
	 * @return bombs fire range
	 */
	public int getRange() {
		return Game.clamp(range, 2, maxRange);
	}

	/**
	 * setting bombs fire range with limitation minimum 2 and maximum 8 if set to
	 * anything beyond limitation it will be handled
	 * 
	 * @param range : bombs range
	 */
	public void setRange(int range) {
		this.range = Game.clamp(range, 2, maxRange);

		// if range is changed timer begins
		if (!rangeTimerStarted) {
			rangeTimerStarted = true;

			rangeTimeLeft = 40;

			rangeTimer = new Timer();
			rangeTimer.scheduleAtFixedRate(new TimerTask() {

				public void run() {
					rangeTimeLeft--;
					if (rangeTimeLeft <= 0) {
						rangeTimerStarted = false;
						Player.this.range = 2;

						rangeTimer.cancel();
						rangeTimer.purge();
						return;
					}
				}
			}, 1000, 1000);
		}
	}

}
