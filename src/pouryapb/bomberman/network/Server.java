package pouryapb.bomberman.network;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import pouryapb.bomberman.framework.BufferedImageLoader;
import pouryapb.bomberman.framework.ID;
import pouryapb.bomberman.objects.Block;
import pouryapb.bomberman.objects.OnlinePlayer;
import pouryapb.bomberman.objects.OnlinePlayer.ONLINEID;
import pouryapb.bomberman.window.Game;
import pouryapb.bomberman.window.HUD;
import pouryapb.bomberman.window.Handler;

public class Server {

	private Handler handler;
	private ServerSocket serverSocket;
	private Socket[] sockets = new Socket[2];
	private ObjectInputStream[] in = new ObjectInputStream[2];
	private ObjectOutputStream[] out = new ObjectOutputStream[2];
	public static SERVERSTATE serverState;
	private HUD hud;
	private Game game;

	public Server(Handler handler, HUD hud, int port, Game game) throws IOException {

		this.handler = handler;
		this.hud = hud;
		this.game = game;

		run(port);

	}

	public enum SERVERSTATE {
		Start, Stop
	}

	private void run(int port) throws IOException {
		serverSocket = new ServerSocket(port);

		serverState = SERVERSTATE.Start;

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			public void run() {
				serverState = SERVERSTATE.Stop;
				timer.cancel();
				timer.purge();
				return;
			}
		}, 120000, 1);

		getClients();

		loadLevel();

		tick();

	}

	private void getClients() throws IOException {
		for (int i = 0; i < 2; i++) {
			sockets[i] = serverSocket.accept();
			in[i] = new ObjectInputStream(sockets[i].getInputStream());
			out[i] = new ObjectOutputStream(sockets[i].getOutputStream());
		}
	}

	private void tick() throws IOException {
		out[0].writeObject(handler);
		out[1].writeObject(handler);
	}

	private void loadLevel() {

		BufferedImage image;
		image = new BufferedImageLoader().loadImage("/online map.png");

		int w = image.getWidth();
		int h = image.getHeight();

		for (int xx = 0; xx < w; xx++) {
			for (int yy = 0; yy < h; yy++) {
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = pixel & 0xff;

				if (red == 255 && green == 255 && blue == 255) {
					Game.levelWidth = (xx * 32) + 32;
				}

				if (red == 255 && green == 255 && blue == 255)
					handler.addObject(new Block(xx * 32, yy * 32, ID.STONE_WALL));
				if (red == 255 && green == 100 && blue == 0)
					handler.addObject(new Block(xx * 32, yy * 32, ID.BRICK_WALL));
				if (red == 0 && green == 148 && blue == 255)
					handler.addObject(
							new OnlinePlayer(xx * 32, yy * 32, ID.ONLINE_PLAYER, ONLINEID.Player1, handler, hud, game));
				if (red == 76 && green == 255 && blue == 0)
					handler.addObject(
							new OnlinePlayer(xx * 32, yy * 32, ID.ONLINE_PLAYER, ONLINEID.Player2, handler, hud, game));
			}
		}
	}
}
