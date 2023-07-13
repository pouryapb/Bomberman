package pouryapb.bomberman.objects;

import java.awt.Graphics;
import java.awt.Rectangle;

import pouryapb.bomberman.framework.GameObject;
import pouryapb.bomberman.framework.ID;
import pouryapb.bomberman.framework.Texture;
import pouryapb.bomberman.window.Game;

/**
 * blocks in the game brick walls that can be destroyed with bombs stone walls
 * that are just for blocking!
 * 
 * @author Pourya
 *
 */

public class Block extends GameObject {

	Texture tex = Game.getInstance();

	public Block(int x, int y, ID id) {
		super(x, y, id);
	}

	public void tick() {
		// a block does nothing
	}

	public Rectangle getBounds() {

		return new Rectangle((int) x, (int) y, 32, 32);
	}

	public void render(Graphics g) {

		if (id == ID.STONE_WALL) {
			g.drawImage(tex.block[0], (int) x, (int) y, null);
		}
		if (id == ID.BRICK_WALL) {
			g.drawImage(tex.block[1], (int) x, (int) y, null);
		}

	}

}
