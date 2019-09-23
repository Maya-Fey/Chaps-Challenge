package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.Collection;
import java.util.EnumSet;

import nz.ac.vuw.ecs.swen225.a3.application.GameState;
import nz.ac.vuw.ecs.swen225.a3.commons.Visible;

/**
 * A model representing the game's maze. Responds to the passage of time and movements
 * by the player. Capable of loading arbitrary levels and, generally running the game
 * logic.
 * 
 * @author Claire
 */
public interface ChapsModel {

	/**
	 * Processes an action and updates the internal game state. Once done, returns
	 * a series of events representing what happened, such as updates needed by the
	 * GUI or a game over instance.
	 * 
	 * @param action The action that recently occurred. Either a tick or a movement
	 * from the player.
	 * 
	 * @return A set of all the events this action caused
	 */
	EnumSet<ChapsEvent> onAction(ChapsAction action);
	
	/**
	 * Returns the current state of the game without any object aliasing, ie, this object
	 * is unaffected by future game progress.
	 * 
	 * @return The current state of the game
	 */
	GameState getState();
	
	/**
	 * Sets the state of the game from a save.
	 * 
	 * @param state a saved game state
	 */
	void setState(GameState state);
	
	/**
	 * @return A 2D array of size Constants.VISIBILE_SIZE that represents everything chap can see.
	 */
	Visible[][] getVisibleArea();
	
	/**
	 * @return A collection of all the items in the inventory, as a list of renderable visibles.
	 */
	Collection<Visible> getInventoryIcons();
	
	/**
	 * @return The amount of time remaining, in seconds
	 */
	int getTimeRemaining();
	
	/**
	 * @return The amount of chips remaining
	 */
	int getChipsRemaining();
	
}
