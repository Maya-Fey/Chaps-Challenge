package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 *
 * @author Claire 300436297
 */
public enum ChapsEvent {
	
	/**
	 * A display update is required
	 */
	DISPLAY_UPDATE_REQUIRED,
	
	/**
	 * An inventory update is required
	 */
	INV_UPDATE_REQUIRED,
	
	/**
	 * A time update is required
	 */
	TIME_UPDATE_REQUIRED,
	
	/**
	 * A chips level is required
	 */
	CHIPS_UPDATE_REQUIRED,
	
	/**
	 * A tutorial message is being shown
	 */
	SHOW_TUTORIAL_MESSAGE,
	
	/**
	 * The tutorial message is no longer shown
	 */
	HIDE_TUTORIAL_MESSAGE,
	
	/**
	 * The game was lost as the player died
	 */
	GAME_LOST_PLAYER_DIED,
	
	/**
	 * The game was lost as the time ran out
	 */
	GAME_LOST_TIME_OUT

}
