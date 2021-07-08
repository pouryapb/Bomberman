package pouryapb.bomberman.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import pouryapb.bomberman.framework.GameObject;
import pouryapb.bomberman.framework.ID;
import pouryapb.bomberman.framework.Texture;
import pouryapb.bomberman.window.Game;
import pouryapb.bomberman.window.HUD;
import pouryapb.bomberman.window.Handler;

/**
 * enemy objects RED and YELLOW
 * 
 * @author Pourya
 *
 */

public class Enemy extends GameObject {

	/**
	 * to check collision
	 */
	private Handler handler;
	private int width = 32;
	private int height = 32;
	/**
	 * to change direction
	 */
	private int timer = 96;

	/**
	 * enemy textures
	 */
	private Texture tex = Game.getInstance();

	/**
	 * for changing directions
	 */
	private Random r = new Random();

	/**
	 * for decreasing players life
	 */
	private HUD hud;

	public Enemy(int x, int y, ID id, Handler handler, HUD hud) {
		super(x, y, id);

		this.handler = handler;
		this.hud = hud;
	}

	public void tick() {

		x += velX;
		y += velY;

		int[] vel = { 1, -1 };

		timer++;

		// changing directions in a period of time
		if (timer >= 96) {
			if (r.nextBoolean()) {
				velX = vel[r.nextInt(2)];
				velY = 0;
			} else {
				velY = vel[r.nextInt(2)];
				velX = 0;
			}
			timer = 0;
		}

		collision();
	}

	public void render(Graphics g) {

		if (id == ID.ENEMY_RED) {

			if (velX > 0)
				g.drawImage(tex.enemy[7], x, y, null);
			if (velX < 0)
				g.drawImage(tex.enemy[6], x, y, null);
			if (velY > 0)
				g.drawImage(tex.enemy[4], x, y, null);
			if (velY < 0)
				g.drawImage(tex.enemy[5], x, y, null);
		}
		if (id == ID.ENEMY_YELLOW) {

			if (velX > 0)
				g.drawImage(tex.enemy[3], x, y, null);
			if (velX < 0)
				g.drawImage(tex.enemy[2], x, y, null);
			if (velY > 0)
				g.drawImage(tex.enemy[0], x, y, null);
			if (velY < 0)
				g.drawImage(tex.enemy[1], x, y, null);
		}
	}

	/**
	 * for detecting the collision between objects
	 */

	private void collision() {
		for (var i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			// collision with BrickWalls or StoneWall or Bombs stops enemies
			if (tempObject.getId() == ID.BRICK_WALL || tempObject.getId() == ID.STONE_WALL
					|| tempObject.getId() == ID.BOMB) {

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

			// collision with player
			if (tempObject.getId() == ID.PLAYER) {
				if (getBounds().intersects(tempObject.getBounds())) {
					hud.setHelath(hud.getHealth() - 1);
				}
			}

			// collision with player destroys them
			if (tempObject.getId() == ID.PLAYER) {
				if (getBounds().intersects(tempObject.getBounds())) {
					Game.killedEnemys++;
					handler.removeObject(this);
				}
			}

			// collision with bomb blast destroys them
			if (tempObject.getId() == ID.BLAST) {
				if (getBounds().intersects(tempObject.getBounds())) {
					Game.killedEnemys++;
					handler.removeObject(this);
				}
			}
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x + 3, y + 3, width - 6, height - 6);
	}

	public Rectangle getBoundsButtom() {
		return new Rectangle(x + (width / 2) - ((width / 2) / 2), y + height / 2, width / 2, height / 2);
	}

	public Rectangle getBoundsTop() {
		return new Rectangle(x + (width / 2) - ((width / 2) / 2), y, width / 2, height / 2);
	}

	public Rectangle getBoundsRight() {
		return new Rectangle(x + width - 5, y + 5, 5, height - 10);
	}

	public Rectangle getBoundsLeft() {
		return new Rectangle(x, y + 5, 5, height - 10);
	}

}
