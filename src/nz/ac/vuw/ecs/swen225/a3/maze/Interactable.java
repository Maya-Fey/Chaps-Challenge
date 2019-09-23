package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * An interactable object on top of a tile.
 */
public interface Interactable {
	
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

}
