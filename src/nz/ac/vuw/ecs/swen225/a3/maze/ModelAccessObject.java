package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.List;

/**
 * An access object for the model. Used by maze objects to create side effects.
 * method is given all variables storing the board state
 * they are then modified within other classes and returned after being processed within class
 * and update the ChapsModelImpl to reflect these changes
 *
 * @author None yet
 */
public class ModelAccessObject {
	//all variables storing state of board
	private Tile[][] maze;
	private List<Actor> actors;
	private List<Interactable> interactables;
	private Inventory inv;

	private int timeRemaining, chipsRemaining;

	private Position newTileLocation;
	private Position oldTileLocation;


	/**
	 * Constructor for new model access object
	 * takes all fields from ChapsModelImpl so they can be modified and returned
	 * @param maze
	 * @param actors
	 * @param interactables
	 * @param inv
	 * @param timeRemaining
	 * @param chipsRemaining
	 * @param newTileLocation
	 * @param oldTileLocation
	 */
	public ModelAccessObject(Tile[][] maze, List<Actor> actors, List<Interactable> interactables, Inventory inv,
			int timeRemaining, int chipsRemaining, Position newTileLocation, Position oldTileLocation) {
		super();
		this.maze = maze;
		this.actors = actors;
		this.interactables = interactables;
		this.inv = inv;
		this.timeRemaining = timeRemaining;
		this.chipsRemaining = chipsRemaining;
		this.newTileLocation = newTileLocation;
		this.oldTileLocation = oldTileLocation;
	}

	/*
	 * The idea behind this class is it's passed to certain other classes during events,
	 * such as when something walks on a tile, or when you push something on a tile, or
	 * when two actors collide, etc.
	 *
	 * Inside those methods, the object can then change the game state, such as alter the player's
	 * inventory or change something about the maze.
	 */






	//move object
		//need direction?



	//walk to a tile

		//onto a locked door


		//onto treasure
			//update tile to free tile


		//onto key
			//update tile to free tile


		//info field


		//exit


		//free tile


		//






	//

}
