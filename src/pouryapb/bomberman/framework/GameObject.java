package pouryapb.bomberman.framework;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * all game objects should have a specific constructor and some required methods
 * 
 * @author Pourya
 *
 */

public abstract class GameObject {

	protected double x, y;
	protected ID id;
	protected double velX;
	protected double velY;

	/**
	 * GameObject Constructor
	 * 
	 * @param x  : x-coordinate of object
	 * @param y  : y-coordinate of object
	 * @param id : objects ID
	 */

	protected GameObject(double x, double y, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	/**
	 * jobs that game objects should do
	 */

	public abstract void tick();

	/**
	 * shape of game objects to be rendered
	 * 
	 * @param g Graphics instance
	 */

	public abstract void render(Graphics g);

	/**
	 * an invisible rectangle that shows where the object is
	 * 
	 * @return a rectangle that object is in it
	 */

	public abstract Rectangle getBounds();

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public ID getId() {
		return id;
	}

	public void setVelX(double velX) {
		this.velX = velX;
	}

	public void setVelY(double velY) {
		this.velY = velY;
	}

	public double getVelX() {
		return velX;
	}

	public double getVelY() {
		return velY;
	}

}
