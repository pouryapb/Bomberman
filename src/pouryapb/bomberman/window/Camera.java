package pouryapb.bomberman.window;

import pouryapb.bomberman.framework.GameObject;

/**
 * dige doorbin doorbine dg -_-
 * donbale player mire hichvaght ham az biroone map film nemigire :/
 * 
 * @author Pourya
 *
 */

public class Camera {

	private double x, y;

	public Camera(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void tick(GameObject player) {
		if (player.getX() < Game.WIDTH / 2) {
			x = 0;
		} else if (player.getX() < (Game.levelWidth - Game.WIDTH / 2)) {
			x = Game.WIDTH / 2 - player.getX();
		}
	}

	public double getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
