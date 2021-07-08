package pouryapb.bomberman.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import pouryapb.bomberman.window.Game.STATE;

/**
 * menu screen :/
 * 
 * @author Pourya
 *
 */
public class OnlineMenu extends MouseAdapter {

	private Game game;

	public OnlineMenu(Game game) {
		this.game = game;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (mouseOver(mx, my, 280, 200, 190, 50)) {
			game.gameState = STATE.HOST;
		}

		if (mouseOver(mx, my, 280, 200, 190, 50)) {
			game.gameState = STATE.JOIN;
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

		Font f1 = new Font("Arial", 1, 30);
		Font f2 = new Font("Arial", 1, 16);

		g.setFont(f1);
		g.setColor(Color.black);
		g.drawString("Choose one!", 280, 150);

		// button 1
		g.setColor(new Color(255, 255, 255, 130));
		g.fillRect(280, 200, 190, 50);

		// button text 1
		g.setFont(f2);
		g.setColor(Color.black);
		g.drawString("Host", 320, 235);

		// button 2
		g.setColor(new Color(255, 255, 255, 130));
		g.fillRect(280, 260, 190, 50);

		// button text 2
		g.setFont(f2);
		g.setColor(Color.black);
		g.drawString("Join", 330, 290);
	}

}
