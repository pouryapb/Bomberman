package pouryapb.bomberman.framework;

import java.awt.image.BufferedImage;

/**
 * Separates a sprite sheet and returns BufferedImages
 * 
 * @author Pourya
 *
 */

public class SpriteSheet {

	private BufferedImage image;

	/**
	 * gets a BufferedImage as a sprite sheet to separate
	 * 
	 * @param image sprite sheet
	 */

	public SpriteSheet(BufferedImage image) {
		this.image = image;
	}

	/**
	 * Separates sprite sheet
	 * 
	 * @param col    : column that the picture is in
	 * @param row    : row that the picture is in
	 * @param width  : width of each picture
	 * @param height : height of each picture
	 * @return separated image from sprite sheet
	 */

	public BufferedImage grabImage(int col, int row, int width, int height) {
		return image.getSubimage((col * width) - width, (row * height) - height, width, height);
	}

}
