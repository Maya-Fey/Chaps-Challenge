package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * An access object for the model. Used by maze objects to create side effects.
 * method is given all variables storing the board state
 * they are then modified within other classes and returned after being processed within class
 * and update the ChapsModelImpl to reflect these changes
 *
 * @author Claire
 */
public interface ModelAccessObject {
	
	/**
	 * Gives chips to chap.
	 * 
	 * @param chips The amount of chips to add
	 */
	void remChips(int chips);
	
	/**
	 * Removes an interactable from the game
	 * 
	 * @param interact The interactable to remove
	 */
	void removeInteractable(Interactable interact);

}
