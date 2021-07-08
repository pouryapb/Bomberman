package pouryapb.bomberman.objects;

/**
 * in game item Star
 */

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

import pouryapb.bomberman.framework.GameObject;
import pouryapb.bomberman.framework.ID;
import pouryapb.bomberman.framework.Texture;
import pouryapb.bomberman.window.Game;
import pouryapb.bomberman.window.Handler;

public class Star extends GameObject {

	/**
	 * to do stuff when colliding with player giving more bomb range and destroying
	 * itself
	 */
	private Handler handler;

	/**
	 * self destruction after 20 seconds
	 */
	private Timer timer;

	/**
	 * for self destruction purposes
	 */
	private Star star;
	private Texture tex = Game.getInstance();

	public Star(int x, int y, ID id, Handler handler) {
		super(x, y, id);

		this.handler = handler;

		timer = new Timer();
		star = this;
	}

	public void tick() {

		// begins counting the time to destroy itself
		timer.scheduleAtFixedRate(new TimerTask() {

			public void run() {
				handler.removeObject(star);
				timer.cancel();
				timer.purge();
				return;
			}
		}, 20000, 1);

		collision();
	}

	/**
	 * collision codes on colliding with player gives player bomb range boost and
	 * destroys itself
	 */
	private void collision() {

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.PLAYER) {
				if (getBounds().intersects(tempObject.getBounds())) {

					((Player) tempObject).setRange(((Player) tempObject).getRange() * 2);

					timer.cancel();
					timer.purge();
					handler.removeObject(this);
				}
			}
		}
	}

	public void render(Graphics g) {

		g.drawImage(tex.star, x, y, null);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}

}
