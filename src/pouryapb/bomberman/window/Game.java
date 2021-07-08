package pouryapb.bomberman.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import pouryapb.bomberman.framework.BufferedImageLoader;
import pouryapb.bomberman.framework.ID;
import pouryapb.bomberman.framework.KeyInput;
import pouryapb.bomberman.framework.Texture;
import pouryapb.bomberman.objects.Block;
import pouryapb.bomberman.objects.Enemy;
import pouryapb.bomberman.objects.Player;

/**
 * THE GAME!!!
 * 
 * @author Pourya
 *
 */

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 5604017237193095774L;
	public static final int WIDTH = 767;
	public static final int HEIGHT = WIDTH / 12 * 9;
	private Thread thread;
	private boolean running = false;
	private Handler handler;
	private HUD hud;
	private Camera cam;

	/**
	 * level is loaded from a picture
	 */
	private BufferedImage level;

	/**
	 * textures
	 */
	private static Texture tex;

	/**
	 * maps width for Camera use and spawning system
	 */
	public static int levelWidth;

	/**
	 * the spawning system
	 */
	private Spawn spawner;

	/**
	 * game over screen
	 */
	private GameOver gameOver;

	/**
	 * win screen
	 */
	private Win win;

	/**
	 * menu screen
	 */
	private Menu menu;

	/**
	 * bombs that are exploded by player
	 */
	public static int bombs;

	/**
	 * enemies that are killed by player
	 */
	public static int killedEnemys;

	/**
	 * time of play
	 */
	public static int time;

	/**
	 * to count the time of play
	 */
	private Timer timer;

	/**
	 * for debugging purposes
	 */
	public static boolean spawned = false;

	/**
	 * for debugging purposes
	 */
	private boolean timerStarted = false;

	/**
	 * Game States Values
	 * 
	 * @author Pourya
	 *
	 */
	public enum STATE {
		GAME, DEAD, MENU, WIN, ONLINE_MENU, HOST, JOIN
	}

	/**
	 * current State of the Game
	 */
	public STATE gameState = STATE.MENU;

	/**
	 * making a window and assigning Game instance to it
	 */
	public Game() {

		tex = new Texture();
		gameOver = new GameOver(this);
		win = new Win(this);
		menu = new Menu(this);
		// onlineMenu = new OnlineMenu(this);

		handler = new Handler();
		this.addKeyListener(new KeyInput(handler));

		cam = new Camera(0, 0);

		hud = new HUD();

		// if game is started object would be made
		if (gameState == STATE.GAME) {
			reload();
		}

		new Window(WIDTH, HEIGHT, "Bomber Man!", this);
	}

	/**
	 * makes object in the game and resets score values
	 */
	public void reload() {

		hud.setHelath(2);

		bombs = 0;
		killedEnemys = 0;
		time = 0;

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			public void run() {
				time++;
			}
		}, 1000, 1000);
		timerStarted = true;

		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("/pouryapb/bomberman/resources/level.png"); // loading level

		spawner = new Spawn(this, handler, hud);

		loadImageLevel(level);

	}

	/**
	 * window uses this method to start game in a thread
	 */
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	/**
	 * to stop thread!
	 */
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * the game loop
	 */
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		var amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		var timer = System.currentTimeMillis();
		var frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running)
				render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}

	/**
	 * jobs that game or game object should do
	 */
	private void tick() {

		// game stopped!
		if (gameState != STATE.GAME && timerStarted) {
			timer.cancel();
			timer.purge();
			timerStarted = false;
		}

		// game on!
		if (gameState == STATE.GAME) {
			if (!timerStarted) {

				timerStarted = true;

				timer = new Timer();
				timer.scheduleAtFixedRate(new TimerTask() {

					public void run() {
						time++;
					}
				}, 1000, 1000);
			}

			try {
				handler.tick();
			} catch (NullPointerException | IndexOutOfBoundsException e) {
			}

			hud.tick();
			spawner.tick();

			if (!spawned)
				spawned = true;

			// finding the player object and giving it to camera to follow!
			for (int i = 0; i < handler.object.size(); i++) {
				if (handler.object.get(i).getId() == ID.PLAYER) {
					cam.tick(handler.object.get(i));
				}
			}

			// adding mouse events when is necessary
			if (this.getMouseListeners().length > 0) {
				this.removeMouseListener(gameOver);
				this.removeMouseListener(win);
				this.removeMouseListener(menu);
			}
		}

		// player is dead, timer is off and game over screen is on!
		if (gameState == STATE.DEAD) {

			timer.cancel();
			timer.purge();
			timerStarted = false;

			if (this.getMouseListeners().length <= 0)
				this.addMouseListener(gameOver);

			gameOver.tick();
		}

		// player won, timer off and win screen on!
		if (gameState == STATE.WIN) {

			timer.cancel();
			timer.purge();
			timerStarted = false;

			if (this.getMouseListeners().length <= 0) {
				this.addMouseListener(win);
			}

			win.tick();
		}

		// menu screen is on!
		if (gameState == STATE.MENU) {
			try {
				timer.cancel();
				timer.purge();
				timerStarted = false;
			} catch (Exception e) {
			}

			if (this.getMouseListeners().length <= 0) {
				this.addMouseListener(menu);
			}

			menu.tick();
		}

		// if (gameState == STATE.OnlineMenu) {
		//
		// this.removeMouseListener(menu);
		// this.addMouseListener(onlineMenu);
		//
		// menu.tick();
		// }

		// if (gameState == STATE.Host) {
		// if (!oneTime) {
		//
		// oneTime = true;
		// try {
		//
		// new Server(handler, hud, 8085, this);
		//
		// client = new Client("localhost", 8085);
		//
		// } catch (IOException | ClassNotFoundException e) {
		// e.printStackTrace();
		// }
		// }
		//
		// client.tick();
		// }

		// if (gameState == STATE.Join) {
		//
		// if(!oneTime){
		//
		// oneTime = true;
		// try {
		//
		// client = new Client("localhost", 8085);
		//
		// } catch (ClassNotFoundException | IOException e) {
		// e.printStackTrace();
		// }
		// }
		//
		// try {
		// client.tick();
		// }catch (Exception e) {
		// }
		// }
	}

	/**
	 * commands all objects to render them self
	 */
	private void render() {
		/**
		 * to bring down frame rates
		 */
		var bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		var g = bs.getDrawGraphics();
		var g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		/////////////////////////////////////

		/**
		 * to prevent flashing
		 */
		g.setColor(Color.decode("#39ba16"));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// game on, render these :/
		if (gameState == STATE.GAME) {

			// everything is affected by camera after this code
			g2d.translate(cam.getX(), cam.getY());

			handler.render(g);

			// avoiding of affects of camera after this code
			g2d.translate(-cam.getX(), -cam.getY());

			hud.render(g);
		}

		// player is dead render game over screen
		if (gameState == STATE.DEAD) {
			gameOver.render(g);
		}

		// plyer has won render win screen
		if (gameState == STATE.WIN) {
			win.render(g);
		}

		// render menu screen
		if (gameState == STATE.MENU) {
			menu.render(g);
		}

		// if (gameState == STATE.OnlineMenu) {
		// onlineMenu.render(g);
		// }

		// if (gameState == STATE.Host || gameState == STATE.Join) {
		// try {
		// client.render(g);
		// }catch (Exception e) {
		// }
		// }

		/////////////////////////////////////
		g.dispose();
		bs.show();
	}

	/**
	 * loads a painted level not a random level -_-
	 * 
	 * @param image : level to be loaded
	 */
	private void loadImageLevel(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();

		for (var xx = 0; xx < w; xx++) {
			for (int yy = 0; yy < h; yy++) {
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = pixel & 0xff;

				if (red == 255 && green == 255 && blue == 255) {
					levelWidth = (xx * 32) + 32;
				}

				if (red == 255 && green == 255 && blue == 255)
					handler.addObject(new Block(xx * 32, yy * 32, ID.STONE_WALL));
				if (red == 0 && green == 0 && blue == 255)
					handler.addObject(new Player(xx * 32, yy * 32, ID.PLAYER, handler, hud, this));
				if (red == 255 && green == 100 && blue == 0)
					handler.addObject(new Block(xx * 32, yy * 32, ID.BRICK_WALL));
				if (red == 255 && green == 0 && blue == 0)
					handler.addObject(new Enemy(xx * 32, yy * 32, ID.ENEMY_RED, handler, hud));
				if (red == 255 && green == 216 && blue == 0)
					handler.addObject(new Enemy(xx * 32, yy * 32, ID.ENEMY_YELLOW, handler, hud));
			}
		}
	}

	/**
	 * to check limitation
	 * 
	 * @param variable : variables value to be checked
	 * @param min      : minimum value
	 * @param max      : maximum value
	 * @return - if value isn't beyond limitations returns variables value - if
	 *         passed maximum returns maximum - if passed minimum returns minimum
	 */
	public static int clamp(int variable, int min, int max) {
		if (variable >= max)
			return max;
		else if (variable <= min)
			return min;
		else
			return variable;
	}

	/**
	 * any object in the game can get its texture with this method
	 * 
	 * @return
	 */
	public static Texture getInstance() {
		return tex;
	}

	public static void main(String[] args) {
		new Game();
	}

}
