package pouryapb.bomberman.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

import pouryapb.bomberman.framework.Animation;
import pouryapb.bomberman.framework.GameObject;
import pouryapb.bomberman.framework.ID;
import pouryapb.bomberman.framework.Texture;
import pouryapb.bomberman.objects.Blast.BlastType;
import pouryapb.bomberman.window.Game;
import pouryapb.bomberman.window.Handler;

/**
 * bomb objects can be made in the game with space bar
 * 
 * @author Pourya
 *
 */

public class Bomb extends GameObject {

	/**
	 * to know that if the player is in the bomb or not for some debugging purposes
	 */

	private boolean playerIn = true;

	/**
	 * is the bomb exploded?!
	 */
	private boolean explode = false;

	/**
	 * to add blasts to the game
	 */
	private Handler handler;

	/**
	 * range of the explosion it is set by player object
	 */
	private int range;

	/**
	 * player object :|
	 */
	private GameObject player;

	/**
	 * bomb animation
	 */
	private Animation a;

	/**
	 * to get bomb textures
	 */
	private Texture tex = Game.getInstance();

	public Bomb(int x, int y, ID id, Handler handler, GameObject player) {
		super(x, y, id);
		this.handler = handler;
		this.player = player;
		this.range = ((Player) player).getRange();

		Game.bombs++;

		a = new Animation(13, tex.bomb[0], tex.bomb[1]);

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			public void run() {
				explode = true;
				timer.cancel();
				timer.purge();

			}
		}, 3000, 1);
	}

	public void tick() {
		a.runAnimation();

		if (explode) {
			handler.addObject(new Blast(x, y, ID.BLAST, handler, BlastType.CENTER));

			// top blast
			l1: for (var i = 0; i < range; i++) {
				var empty = false;

				for (var j = 0; j < handler.object.size(); j++) {
					GameObject tempObject = handler.object.get(j);

					if (tempObject.getBounds().intersects(new Rectangle(x, (y - ((i + 1) * 32)), 32, 32))
							&& tempObject.getId() != ID.PLAYER && tempObject.getId() != ID.ENEMY_RED
							&& tempObject.getId() != ID.ENEMY_YELLOW) {
						if (tempObject.getId() == ID.BRICK_WALL) {
							handler.removeObject(tempObject);
						}
						empty = false;
						break l1;
					} else {
						empty = true;
					}
				}
				if (empty) {
					handler.addObject(new Blast(x, (y - ((i + 1) * 32)), ID.BLAST, handler, BlastType.VERTICAL));
					empty = false;
				}
			}
			// Bottom blast
			l2: for (int i = 0; i < range; i++) {

				boolean empty = false;
				for (int j = 0; j < handler.object.size(); j++) {
					GameObject tempObject = handler.object.get(j);

					if (tempObject.getBounds().intersects(new Rectangle(x, (y + ((i + 1) * 32)), 32, 32))
							&& tempObject.getId() != ID.PLAYER && tempObject.getId() != ID.ENEMY_RED
							&& tempObject.getId() != ID.ENEMY_YELLOW) {
						if (tempObject.getId() == ID.BRICK_WALL) {
							handler.removeObject(tempObject);
						}
						empty = false;
						break l2;
					} else {
						empty = true;
					}
				}
				if (empty) {
					handler.addObject(new Blast(x, (y + ((i + 1) * 32)), ID.BLAST, handler, BlastType.VERTICAL));
					empty = false;
				}
			}
			// right blast
			l3: for (int i = 0; i < range; i++) {

				boolean empty = false;
				for (int j = 0; j < handler.object.size(); j++) {
					GameObject tempObject = handler.object.get(j);

					if (tempObject.getBounds().intersects(new Rectangle((x + ((i + 1) * 32)), y, 32, 32))
							&& tempObject.getId() != ID.PLAYER && tempObject.getId() != ID.ENEMY_RED
							&& tempObject.getId() != ID.ENEMY_YELLOW) {
						if (tempObject.getId() == ID.BRICK_WALL) {
							handler.removeObject(tempObject);
						}
						empty = false;
						break l3;
					} else {
						empty = true;
					}
				}
				if (empty) {
					handler.addObject(new Blast((x + ((i + 1) * 32)), y, ID.BLAST, handler, BlastType.HORIZONTAL));
					empty = false;
				}
			}
			// left blast
			l4: for (int i = 0; i < range; i++) {

				boolean empty = false;
				for (int j = 0; j < handler.object.size(); j++) {
					GameObject tempObject = handler.object.get(j);

					if (tempObject.getBounds().intersects(new Rectangle((x - ((i + 1) * 32)), y, 32, 32))
							&& tempObject.getId() != ID.PLAYER && tempObject.getId() != ID.ENEMY_RED
							&& tempObject.getId() != ID.ENEMY_YELLOW) {
						if (tempObject.getId() == ID.BRICK_WALL) {
							handler.removeObject(tempObject);
						}
						empty = false;
						break l4;
					} else {
						empty = true;
					}
				}
				if (empty) {
					handler.addObject(new Blast((x - ((i + 1) * 32)), y, ID.BLAST, handler, BlastType.HORIZONTAL));
					empty = false;
				}
			}

			// removing bomb after blasts are made
			handler.removeObject(this);
			((Player) player).setBombLimit(((Player) player).getBombLimit() + 1);
		}
	}

	public void render(Graphics g) {
		a.drawAnimation(g, x, y);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}

	/**
	 * is player inside the bomb?
	 * 
	 * @return true if player is in the bomb else false
	 */

	public boolean getPlayerIn() {
		return playerIn;
	}

	public void setPlayerIn(boolean b) {
		playerIn = b;
	}

}
