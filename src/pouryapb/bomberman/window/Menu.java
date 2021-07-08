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
public class Menu extends MouseAdapter {

	private Game game;

	public Menu(Game game) {
		this.game = game;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		// single player button
		if (mouseOver(mx, my, 280, 200, 190, 50)) {
			Game.spawned = false;
			game.reload();
			game.gameState = STATE.GAME;
		}

		// Multy player button
		// if (mouseOver(mx, my, 280, 260, 190, 50)) {
		// new OnlineMenu(game);
		// game.gameState = STATE.OnlineMenu;
		// }
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
		var f2 = new Font("Arial", 1, 16);

		g.setFont(f1);
		g.setColor(Color.black);
		g.drawString("BomberMan!", 280, 150);

		// button 1
		g.setColor(new Color(255, 255, 255, 130));
		g.fillRect(280, 200, 190, 50);

		// button text 1
		g.setFont(f2);
		g.setColor(Color.black);
		g.drawString("Single Player", 320, 235);

		// button 2
		g.setColor(new Color(255, 255, 255, 130));
		g.fillRect(280, 260, 190, 50);

		// button text 2
		g.setFont(f2);
		g.setColor(Color.black);
		g.drawString("Multiplayer", 330, 290);
	}

}
