package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.JsonObject;

import nz.ac.vuw.ecs.swen225.a3.commons.Persistable;
import nz.ac.vuw.ecs.swen225.a3.commons.Visible;

/**
 * An item existing in the player's inventory or on the ground
 *
 * @author James
 */
public interface Item extends Persistable, Visible, Cloneable {
		
	default JsonObject persist()
	{
		return this.getBuilder().build();
	}

	/**
	 * @return clone of item
	 */
	Item clone();

}
