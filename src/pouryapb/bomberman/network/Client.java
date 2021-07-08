package pouryapb.bomberman.network;

import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import pouryapb.bomberman.window.Handler;

public class Client {

	private Handler handler;
	private ObjectInputStream in;
//	private ObjectOutputStream out;
	private Socket socket;
	
	public Client(String address, int port) throws UnknownHostException, IOException, ClassNotFoundException {
		
		socket = new Socket(address, port);
		in = new ObjectInputStream(socket.getInputStream());
//		out = new ObjectOutputStream(socket.getOutputStream());
		
		handler = (Handler) in.readObject();
	}
	
	public void tick() {
		handler.tick();
	}
	
	public void render(Graphics g) {
		handler.render(g);
	}
	
}
