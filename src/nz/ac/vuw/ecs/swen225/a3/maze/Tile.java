package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.commons.Visible;

/**
 * A tile on the board.
 *
 * @author Claire 300436297
 */
public interface Tile extends MazeObject, Visible, Cloneable {

	/**
	 * @return Whether interactables and actors can be on top of this tile or not.
	 */
	boolean isFloor();

	/**
	 * @param actor The actor that is now on top of the tile
	 * @param obj Model Access
	 *
	 * @return true if this actor can walk on top of this tile, false otherwise
	 */
	boolean isSafe(Actor actor, ModelAccessObject obj);

	/**
	 * Called when an interactable object (e.g. dirt) stands on top
	 * of this object.
	 *
	 * @param interactable The interactable
	 * @param obj Model access
	 */
	void onEnter(Interactable interactable, ModelAccessObject obj);

	/**
	 * Called when an actor object (e.g. the player) stands on top
	 * of this object.
	 *
	 * @param actor The actor
	 * @param obj Model access
	 */
	void onEnter(Actor actor, ModelAccessObject obj);

	/**
	 * @return clone of tile
	 */
	Tile clone();

	default int zIndex()
	{
		return 10;
	}

}
