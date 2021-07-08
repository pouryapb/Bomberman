package pouryapb.bomberman.framework;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * a simple class to store a BufferedImage and give it to user
 * @author Pourya
 *
 */

public class BufferedImageLoader {

	private BufferedImage image;
	
	/**
	 * 
	 * @param path : location of the file to be loaded
	 * @return returns a BufferedImage
	 */
	
	public BufferedImage loadImage(String path) {
		try {
			image = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
}
