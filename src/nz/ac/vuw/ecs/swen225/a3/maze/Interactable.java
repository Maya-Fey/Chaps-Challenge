package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.commons.Visible;

/**
 * An interactable object on top of a tile.
 */
public interface Interactable extends MazeObject, Visible, Cloneable{

	/**
	 * @return Whether this object can be pushed, or whether it can be stood on like
	 * a button.
	 */
	boolean isPushable();
	
	/**
	 * @param actor The actor trying to walk on it
	 * @param obj Model access
	 * @return If this object isn't pushable, can we walk on it?
	 */
	boolean isWalkable(Actor actor, ModelAccessObject obj);
	
	/**
	 * @param actor The actor trying to walk on it
	 * @param obj Model access
	 * @return if it's walkable, is it safe
	 */
	boolean isSafe(Actor actor, ModelAccessObject obj);

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
