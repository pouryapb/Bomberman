package pouryapb.bomberman.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

import pouryapb.bomberman.framework.GameObject;
import pouryapb.bomberman.framework.ID;
import pouryapb.bomberman.window.HUD;
import pouryapb.bomberman.window.Handler;

/**
 * in game Items Heart fills players life on collision
 * 
 * @author Pourya
 *
 */

public class Heart extends GameObject {

	private Handler handler;
	private HUD hud;
	private Timer timer;
	private Heart heart;

	public Heart(int x, int y, ID id, Handler handler, HUD hud) {
		super(x, y, id);

		this.handler = handler;
		this.hud = hud;

		timer = new Timer();
		heart = this;
	}

	public void tick() {

		timer.scheduleAtFixedRate(new TimerTask() {

			public void run() {
				handler.removeObject(heart);
				timer.cancel();
				timer.purge();
				return;
			}
		}, 20000, 1);

		collision();
	}

	/**
	 * detecting collision of player with Heart objects
	 */
	private void collision() {

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.PLAYER) {
				if (getBounds().intersects(tempObject.getBounds())) {
					hud.setHelath(2);
					timer.cancel();
					timer.purge();
					handler.removeObject(this);
				}
			}
		}
	}

	public void render(Graphics g) {

		Font f = new Font("Arial", 1, 50);

		g.setFont(f);
		g.setColor(Color.red);
		g.drawString("\u2665", (int) x, (int) y + 32);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

}
