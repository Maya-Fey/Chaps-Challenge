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
	
	/**
	 * @return The inventory for the model
	 */
	Inventory getInventory();
	
	/**
	 * @return If the player has all the chips or not
	 */
	boolean hasAllChips();
	
	/**
	 * @param message The message to display
	 */
	void setTutorialMessage(String message);
	
	/**
	 * Sets the tile at the specified position
	 * 
	 * @param tile The tile to place
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 */
	void setTile(Tile tile, int x, int y);
	
	/**
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 * @return The tile at those coords
	 */
	Tile getTile(int x, int y);
	
	/**
	 * Kills the player
	 */
	void killPlayer();
	
	/**
	 * Can an object move to this position?
	 * <br><br>
	 * <i>Note, this is not used in onAction as the decision tree needs more complexity to function</i>
	 * 
	 * @param pos The position we're testing
	 * @return Whether it's possible
	 */
	boolean canMoveTo(Position pos);

}
