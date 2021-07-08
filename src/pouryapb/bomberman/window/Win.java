package pouryapb.bomberman.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import pouryapb.bomberman.window.Game.STATE;

/**
 * win screen :/
 * 
 * @author Pourya
 *
 */
public class Win extends MouseAdapter {

	private Game game;

	public Win(Game game) {
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
		// not needed
	}

	public void tick() {
		// not needed
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
		g.drawString("You Won!", 305, 150);

		int score = (int) (Math.pow(Game.killedEnemys, 4) / (Game.time + Math.log(Game.bombs)));

		g.setColor(Color.black);
		g.drawString("Score: " + score, 290, 200);

		// button
		g.setColor(new Color(255, 255, 255, 130));
		g.fillRect(280, 260, 190, 50);

		var f2 = new Font("Arial", 1, 16);

		// button text
		g.setFont(f2);
		g.setColor(Color.black);
		g.drawString("Start again", 335, 290);
	}

}
