package pouryapb.bomberman.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

import pouryapb.bomberman.framework.GameObject;
import pouryapb.bomberman.framework.ID;
import pouryapb.bomberman.framework.Texture;
import pouryapb.bomberman.window.Handler;

/**
 * bombs blasts
 * 
 * @author Pourya
 *
 */

public class Blast extends GameObject {

	private boolean destroy = false;
	private Handler handler;
	private boolean hit = false;

	/**
	 * Type of a blast to draw the right blast in game
	 * 
	 * @author Pourya
	 *
	 */

	public enum BlastType {
		HORIZONTAL, VERTICAL, CENTER
	}

	private BlastType type;
	private Texture tex = new Texture();

	public Blast(int x, int y, ID id, Handler handler, BlastType type) {
		super(x, y, id);
		this.handler = handler;
		this.type = type;

		var timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			public void run() {
				destroy = true;
				timer.cancel();
				timer.purge();
			}
		}, 500, 1);
	}

	public void tick() {
		if (destroy) {
			handler.removeObject(this);
		}
	}

	public void render(Graphics g) {

		if (type == BlastType.HORIZONTAL) {
			g.drawImage(tex.blast[1], x, y, null);
		} else if (type == BlastType.VERTICAL) {
			g.drawImage(tex.blast[0], x, y, null);
		} else if (type == BlastType.CENTER) {
			g.drawImage(tex.blast[2], x, y, null);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x + 2, y + 2, 30, 30);
	}

	public void setHit(boolean b) {
		hit = b;
	}

	public boolean getHit() {
		return hit;
	}

}
