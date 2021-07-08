package pouryapb.bomberman.window;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import pouryapb.bomberman.framework.GameObject;

/**
 * stores all game object and ticks and renders them all!
 * 
 * @author Pourya
 *
 */
public class Handler {

	public static final List<GameObject> object = new LinkedList<>();

	public void tick() throws NullPointerException, IndexOutOfBoundsException {
		for (var i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);

			tempObject.tick();
		}
	}

	public void render(Graphics g) {
		for (var i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);

			tempObject.render(g);
		}
	}

	public void addObject(GameObject object) {
		Handler.object.add(object);
	}

	public void removeObject(GameObject object) {
		Handler.object.remove(object);
	}
}
