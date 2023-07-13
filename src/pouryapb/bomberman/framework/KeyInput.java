package pouryapb.bomberman.framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import pouryapb.bomberman.objects.Bomb;
import pouryapb.bomberman.objects.Player;
import pouryapb.bomberman.window.Handler;

/**
 * keys to move player and place bombs
 * 
 * @author Pourya
 *
 */

public class KeyInput extends KeyAdapter {

	private Handler handler;

	public KeyInput(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		/**
		 * searches for a game object with id of player to set its velX and velY in
		 * order to player object move
		 */

		for (var i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.PLAYER) {

				if (key == KeyEvent.VK_W)
					tempObject.setVelY(-3);
				if (key == KeyEvent.VK_A)
					tempObject.setVelX(-3);
				if (key == KeyEvent.VK_S)
					tempObject.setVelY(3);
				if (key == KeyEvent.VK_D)
					tempObject.setVelX(3);

			}

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		/**
		 * searches for a game object with id of player to set velX and velY to 0 in
		 * order to stop player from moving
		 */

		for (var i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.PLAYER) {

				if (key == KeyEvent.VK_W)
					tempObject.setVelY(0);
				if (key == KeyEvent.VK_A)
					tempObject.setVelX(0);
				if (key == KeyEvent.VK_S)
					tempObject.setVelY(0);
				if (key == KeyEvent.VK_D)
					tempObject.setVelX(0);

				// making bomb with space bar
				if (key == KeyEvent.VK_SPACE && ((Player) tempObject).getBombLimit() > 0) {
					int x = (int) ((tempObject.getX() + 16) / 32);
					int y = (int) ((tempObject.getY() + 16) / 32);

					handler.addObject(new Bomb(x * 32, y * 32, ID.BOMB, handler, tempObject));
					((Player) tempObject).setBombLimit(((Player) tempObject).getBombLimit() - 1);
				}
			}
		}

	}

}
