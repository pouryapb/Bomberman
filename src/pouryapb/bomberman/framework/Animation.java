package pouryapb.bomberman.framework;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Create an animation and draw some images on screen with the speed you want
 * 
 * @author Pourya
 *
 */

public class Animation {

	private int speed;
	private int frames;

	private int index = 0;
	private int count = 0;

	private BufferedImage[] images;
	private BufferedImage currentImg;

	/**
	 * 
	 * @param speed : speed of changing frames less means fast
	 * @param args  : frames
	 */

	public Animation(int speed, BufferedImage... args) {
		this.speed = speed;
		images = Arrays.copyOf(args, args.length);

		frames = args.length;

	}

	/**
	 * starts looping in frames
	 */

	public void runAnimation() {
		index++;
		if (index > speed) {
			index = 0;
			nextFrame();
		}
	}

	/**
	 * a private method to loop in frames
	 */

	private void nextFrame() {
		for (var i = 0; i < frames; i++) {
			if (count == i)
				currentImg = images[i];
		}

		count++;

		if (count > frames)
			count = 0;
	}

	/**
	 * 
	 * this method draws the animation on a Graphics instance
	 * 
	 * @param g : an instance of Graphics class to draw images on
	 * @param x : x-coordinate
	 * @param y : y-coordinate
	 */

	public void drawAnimation(Graphics g, int x, int y) {
		g.drawImage(currentImg, x, y, null);
	}

	/**
	 * 
	 * this method draws the animation on a Graphics instance
	 * 
	 * @param g      : an instance of Graphics class to draw images on
	 * @param x      : x-coordinate
	 * @param y      : y-coordinate
	 * @param scaleX : to change the width image if its not large or small enough
	 * @param scaleY : to change the height image if its not large or small enough
	 */

	public void drawAnimation(Graphics g, int x, int y, int scaleX, int scaleY) {
		g.drawImage(currentImg, x, y, scaleX, scaleY, null);
	}

}
