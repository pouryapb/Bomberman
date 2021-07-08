package pouryapb.bomberman.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import pouryapb.bomberman.window.Game.STATE;

/**
 * game over screen :/
 * 
 * @author Pourya
 *
 */
public class GameOver extends MouseAdapter {

	private Game game;

	public GameOver(Game game) {
		this.game = game;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (mouseOver(mx, my, 280, 260, 190, 50)) {
			Game.spawned = false;
			game.reload();
			game.gameState = STATE.GAME;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// no need for this
	}

	public void tick() {
		// no nead for this
	}

	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			return my > y && my < y + height;
		} else {
			return false;
		}
	}

	public void render(Graphics g) {
		g.setColor(new Color(255, 255, 255, 50));
		g.fillRect(200, 100, 350, 250);

		var f1 = new Font("Arial", 1, 30);

		g.setFont(f1);
		g.setColor(Color.black);
		g.drawString("GameOver", 290, 150);

		int score = (int) (Math.pow(Game.killedEnemys, 1) / (Game.time + Math.log(Game.bombs)));

		g.setColor(Color.black);
		g.drawString("Score: " + score, 290, 200);

		// button
		g.setColor(new Color(255, 255, 255, 130));
		g.fillRect(280, 260, 190, 50);

		var f2 = new Font("Arial", 1, 16);

		// button text
		g.setFont(f2);
		g.setColor(Color.black);
		g.drawString("Try again", 335, 290);
	}

}
