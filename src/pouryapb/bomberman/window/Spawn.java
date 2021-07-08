package pouryapb.bomberman.window;

import java.awt.Rectangle;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import pouryapb.bomberman.framework.GameObject;
import pouryapb.bomberman.framework.ID;
import pouryapb.bomberman.objects.Block;
import pouryapb.bomberman.objects.Enemy;
import pouryapb.bomberman.objects.Heart;
import pouryapb.bomberman.objects.Star;
import pouryapb.bomberman.window.Game.STATE;

/**
 * spawn system for now spawns 5 red enemies and 5 yellow enemies and 200
 * BrickBlocks!
 * 
 * @author Pourya
 *
 */
public class Spawn {

	private Handler handler;
	private Random r = new Random();
	private boolean spawned = false;

	private boolean heartSpawnerStarted = false;
	private boolean starSpawnerStarted = false;
	private Timer timer = new Timer();
	private Timer timer2;
	private HUD hud;
	private Game game;
	private int walls = 200;
	private int redEnemy = 10;
	private int yellowEnemy = 10;

	public Spawn(Game game, Handler handler, HUD hud) {
		this.handler = handler;
		this.hud = hud;
		this.game = game;
	}

	public void tick() {

		// spawning star
		if (!starSpawnerStarted) {
			starSpawnerStarted = true;
			timer2 = new Timer();
			timer2.scheduleAtFixedRate(new TimerTask() {

				public void run() {

					if (game.gameState != STATE.GAME) {
						timer2.cancel();
						timer2.purge();
						return;
					}

					for (int i = 0; i < 1; i++) {
						int x = Game.clamp(((r.nextInt(Game.levelWidth / 32)) * 32), (1 * 32), Game.levelWidth - 32);
						int y = Game.clamp(((r.nextInt(Game.HEIGHT / 32)) * 32), (1 * 32), Game.HEIGHT - 32);

						// Detecting empty places
						boolean empty = false;
						for (int j = 0; j < handler.object.size(); j++) {
							GameObject tempObject = handler.object.get(j);

							if (tempObject.getBounds().intersects(new Rectangle(x, y, 32, 32))) {
								empty = false;
								break;
							} else {
								empty = true;
							}
						}
						if (empty) {
							handler.addObject(new Star(x, y, ID.STAR, handler));
							empty = false;
							break;
						} else {
							i--;
						}
					}

					starSpawnerStarted = false;
					timer2.cancel();
					timer2.purge();
				}
			}, Game.clamp(r.nextInt(90), 10, 90) * 1000, Game.clamp(r.nextInt(90), 10, 90) * 1000);
		}

		// spawning heart
		if (!heartSpawnerStarted) {
			heartSpawnerStarted = true;
			timer.scheduleAtFixedRate(new TimerTask() {

				public void run() {

					if (game.gameState != STATE.GAME) {
						timer.cancel();
						timer.purge();
						return;
					}

					for (var i = 0; i < 1; i++) {
						int x = Game.clamp(((r.nextInt(Game.levelWidth / 32)) * 32), (1 * 32), Game.levelWidth - 32);
						int y = Game.clamp(((r.nextInt(Game.HEIGHT / 32)) * 32), (1 * 32), Game.HEIGHT - 32);

						// Detecting empty places
						var empty = false;
						for (int j = 0; j < handler.object.size(); j++) {
							GameObject tempObject = handler.object.get(j);

							if (tempObject.getBounds().intersects(new Rectangle(x, y, 32, 32))) {
								empty = false;
								break;
							} else {
								empty = true;
							}
						}
						if (empty) {
							handler.addObject(new Heart(x, y, ID.HEART, handler, hud));
							empty = false;
							break;
						} else {
							i--;
						}
					}

				}
			}, 40000, 40000);
		}

		int x, y;

		if (!spawned) {
			// spawning brick walls
			for (int i = 0; i < walls; i++) {
				x = Game.clamp(((r.nextInt(Game.levelWidth / 32)) * 32), (1 * 32), Game.levelWidth - 32);
				y = Game.clamp(((r.nextInt(Game.HEIGHT / 32)) * 32), (1 * 32), Game.HEIGHT - 32);

				// Detecting empty places
				boolean empty = false;
				for (int j = 0; j < handler.object.size(); j++) {
					GameObject tempObject = handler.object.get(j);

					if (tempObject.getBounds().intersects(new Rectangle(x, y, 32, 32))) {
						empty = false;
						break;
					} else {
						empty = true;
					}
				}
				if (empty && !(x < (32 * 3) && y < (32 * 3))) {
					handler.addObject(new Block(x, y, ID.BRICK_WALL));
					empty = false;
				} else {
					i--;
				}
			}
			// spawning red enemies
			for (int i = 0; i < redEnemy; i++) {
				x = Game.clamp((((r.nextInt(Game.levelWidth / 64) * 2) + 1) * 32), (5 * 32),
						((Game.levelWidth / 64 * 2) * 32));
				y = Game.clamp((((r.nextInt(Game.HEIGHT / 64) * 2) + 1) * 32), (5 * 32),
						(((Game.HEIGHT / 64) * 2) * 32));

				// Detecting empty places
				boolean empty = false;
				for (int j = 0; j < handler.object.size(); j++) {
					GameObject tempObject = handler.object.get(j);

					if (tempObject.getBounds().intersects(new Rectangle(x, y, 32, 32))) {
						empty = false;
						break;
					} else {
						empty = true;
					}
				}
				if (empty) {
					handler.addObject(new Enemy(x, y, ID.ENEMY_RED, handler, hud));
					empty = false;
				} else {
					i--;
				}

			}
			// spawning yellow enemies
			for (int i = 0; i < yellowEnemy; i++) {
				x = Game.clamp((((r.nextInt(Game.levelWidth / 64) * 2) + 1) * 32), (5 * 32),
						((Game.levelWidth / 64 * 2) * 32));
				y = Game.clamp((((r.nextInt(Game.HEIGHT / 64) * 2) + 1) * 32), (5 * 32),
						(((Game.HEIGHT / 64) * 2) * 32));

				// Detecting empty places
				boolean empty = false;
				for (int j = 0; j < handler.object.size(); j++) {
					GameObject tempObject = handler.object.get(j);

					if (tempObject.getBounds().intersects(new Rectangle(x, y, 32, 32))) {
						empty = false;
						break;
					} else {
						empty = true;
					}
				}
				if (empty) {
					handler.addObject(new Enemy(x, y, ID.ENEMY_YELLOW, handler, hud));
					empty = false;
				} else {
					i--;
				}

			}
			spawned = true;
		}
	}

}
