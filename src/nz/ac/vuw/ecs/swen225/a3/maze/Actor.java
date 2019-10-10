package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.commons.Visible;

/**
 * An actor in maze. Capable of its own locomotion
 */
public interface Actor extends MazeObject, Visible, Cloneable {

	/**
	 * Move this actor forward by one tick. During the tick this actor
	 * may move.
	 * @param obj Model Access
	 */
	void tick(ModelAccessObject obj);

	/**
	 * @return clone of object
	 */
	Actor clone();

	/**
	 * @return Is this actor the player?
	 */
	boolean isPlayer();

	/**
	 * Called when another actor collides with this one
	 *
	 * @param actor The other actor
	 * @param obj Model access
	 */
	void onCollide(Actor actor, ModelAccessObject obj);

	default int zIndex()
	{
		return 0;
	}
}
