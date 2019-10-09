package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.commons.Persistable;
import nz.ac.vuw.ecs.swen225.a3.commons.Visible;

/**
 * An interactable object on top of a tile.
 */
public interface Interactable extends MazeObject, Persistable, Visible, Cloneable{

	/**
	 * @return Whether this object can be pushed, or whether it can be stood on like
	 * a button.
	 */
	boolean isPushable();

	/**
	 * Called when a actor enters this interactable
	 *
	 * @param actor The actor now standing atop this interactable
	 * @param obj Access object
	 */
	void onEnter(Actor actor, ModelAccessObject obj);

	/**
	 * Clone this interactable
	 * @return clone
	 */
	Interactable clone();

}
