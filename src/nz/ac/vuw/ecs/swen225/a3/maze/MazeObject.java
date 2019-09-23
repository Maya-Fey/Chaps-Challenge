package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * An object that exists as on the board of the maze. Can be a 
 * tile, interactable, or actor.
 * 
 * @author Claire
 */
public interface MazeObject {

	/**
	 * @return The current position of this object.
	 */
	Position getPosition();
	
	/**
	 * Changes the location of this object.
	 * 
	 * @param position The desired new position of this object
	 */
	void setPosition(Position position);
}
