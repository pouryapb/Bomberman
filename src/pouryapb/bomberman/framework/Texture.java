package pouryapb.bomberman.framework;

import java.awt.image.BufferedImage;

/**
 * a place to store textures
 * 
 * @author Pourya
 *
 */

public class Texture {

	private SpriteSheet bs;
	private SpriteSheet ps;
	private SpriteSheet es;
	private SpriteSheet bls;
	private SpriteSheet bbs;
	private BufferedImage blockSheet = null;
	private BufferedImage playerSheet = null;
	private BufferedImage enemySheets = null;
	private BufferedImage blastSheets = null;
	private BufferedImage bombSheet = null;

	public final BufferedImage[] block = new BufferedImage[2];
	public final BufferedImage[] player = new BufferedImage[12];
	public final BufferedImage[] enemy = new BufferedImage[8];
	public final BufferedImage[] blast = new BufferedImage[3];
	public final BufferedImage[] bomb = new BufferedImage[2];
	public BufferedImage star;

	public Texture() {

		var loader = new BufferedImageLoader();

		try {
			blockSheet = loader.loadImage("/pouryapb/bomberman/resources/blocks.png");
			playerSheet = loader.loadImage("/pouryapb/bomberman/resources/player.png");
			enemySheets = loader.loadImage("/pouryapb/bomberman/resources/enemy.png");
			blastSheets = loader.loadImage("/pouryapb/bomberman/resources/blast.png");
			bombSheet = loader.loadImage("/pouryapb/bomberman/resources/bomb.png");
		} catch (Exception e) {
			e.printStackTrace();
		}

		bs = new SpriteSheet(blockSheet);
		ps = new SpriteSheet(playerSheet);
		es = new SpriteSheet(enemySheets);
		bls = new SpriteSheet(blastSheets);
		bbs = new SpriteSheet(bombSheet);

		getTextures();
	}

	/**
	 * private method to store all textures in arrays
	 */

	private void getTextures() {

		// blocks textures
		block[0] = bs.grabImage(1, 1, 32, 32); // StoneBlock
		block[1] = bs.grabImage(2, 1, 32, 32); // BrickBlock

		// player textures
		player[0] = ps.grabImage(1, 1, 32, 32); // Idle down facing Player
		player[1] = ps.grabImage(2, 1, 32, 32); // player moving down
		player[2] = ps.grabImage(3, 1, 32, 32); // player moving down

		player[3] = ps.grabImage(1, 2, 32, 32); // Idle up facing Player
		player[4] = ps.grabImage(2, 2, 32, 32); // player moving up
		player[5] = ps.grabImage(3, 2, 32, 32); // player moving up

		player[6] = ps.grabImage(1, 4, 32, 32); // Idle right facing Player
		player[7] = ps.grabImage(2, 4, 32, 32); // player moving right
		player[8] = ps.grabImage(3, 4, 32, 32); // player moving right

		player[9] = ps.grabImage(1, 3, 32, 32); // Idle left facing Player
		player[10] = ps.grabImage(2, 3, 32, 32); // player moving left
		player[11] = ps.grabImage(3, 3, 32, 32); // player moving left

		// yellow enemy textures
		enemy[0] = es.grabImage(1, 1, 32, 32); // yellow enemy down
		enemy[1] = es.grabImage(2, 1, 32, 32); // yellow enemy up
		enemy[2] = es.grabImage(3, 1, 32, 32); // yellow enemy left
		enemy[3] = es.grabImage(4, 1, 32, 32); // yellow enemy right

		// red enemy textures
		enemy[4] = es.grabImage(1, 2, 32, 32); // red enemy down
		enemy[5] = es.grabImage(2, 2, 32, 32); // red enemy up
		enemy[6] = es.grabImage(3, 2, 32, 32); // red enemy left
		enemy[7] = es.grabImage(4, 2, 32, 32); // red enemy right

		// blast textures
		blast[0] = bls.grabImage(1, 1, 32, 32); // vertical blast
		blast[1] = bls.grabImage(2, 1, 32, 32); // horizontal blast
		blast[2] = bls.grabImage(3, 1, 32, 32); // central blast

		// bomb textures
		bomb[0] = bbs.grabImage(1, 1, 32, 32);
		bomb[1] = bbs.grabImage(2, 1, 32, 32);

		// star texture
		star = new BufferedImageLoader().loadImage("/pouryapb/bomberman/resources/star.png");
	}

}
