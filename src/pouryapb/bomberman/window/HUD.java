package pouryapb.bomberman.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Heads Up Display displays health and time
 * 
 * @author Pourya
 *
 */

public class HUD {

	private int health = 2;
	private static final String ARIAL = "arial";

	public void tick() {

		health = Game.clamp(health, 0, 2);

	}

	public void render(Graphics g) {

		g.setColor(new Color(255, 255, 255, 170));
		g.fillRect(1, 1, 95, 25);

		for (var i = 0; i < 2; i++) {
			var font = new Font(ARIAL, 1, 25);

			g.setColor(Color.darkGray);
			g.setFont(font);
			g.drawString("\u2665", 15 * i + 2, 20);
		}
		for (var i = 0; i < health; i++) {
			var font = new Font(ARIAL, 1, 25);

			g.setColor(Color.red);
			g.setFont(font);
			g.drawString("\u2665", 15 * i + 2, 20);
		}

		var font = new Font(ARIAL, 1, 20);

		g.setFont(font);
		g.setColor(Color.black);
		g.drawString(String.valueOf(Game.time), 55, 20);
	}

	public void setHelath(int health) {
		this.health = health;
	}

	public int getHealth() {
		return health;
	}

}
