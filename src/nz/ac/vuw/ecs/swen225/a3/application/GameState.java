package nz.ac.vuw.ecs.swen225.a3.application;

import java.util.List;

import javax.json.JsonObject;

import nz.ac.vuw.ecs.swen225.a3.commons.Persistable;
import nz.ac.vuw.ecs.swen225.a3.maze.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.Interactable;
import nz.ac.vuw.ecs.swen225.a3.maze.Inventory;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;

/**
 * A class representing the whole state of a level (either untouched or mid-
 * play), including all tiles, interactables, and actors.
 * 
 * @author Claire
 */
public class GameState implements Persistable {
	
	private Tile[][] maze;
	
	private List<Interactable> interactables;
	private List<Actor> actors;
	
	private Inventory inv;
	
	private int timeRemaining, chipsRemaining;
	
	/**
	 * @param maze
	 * @param interactables
	 * @param actors
	 * @param inv
	 * @param timeRemaining
	 * @param chipsRemaining
	 */
	public GameState(Tile[][] maze, List<Interactable> interactables, List<Actor> actors, Inventory inv, int timeRemaining, int chipsRemaining)
	{
		this.maze = maze;
		this.interactables = interactables;
		this.actors = actors;
		this.inv = inv;
		this.timeRemaining = timeRemaining;
		this.chipsRemaining = chipsRemaining;
	}
	
	/**
	 * @return The inventory in this game state.
	 */
	public Inventory getInventory()
	{
		return inv;
	}
	
	/**
	 * @return The time remaining for this game state
	 */
	public int getTimeRemaining()
	{
		return this.timeRemaining;
	}
	
	/**
	 * @return The chips remaining for this game state
	 */
	public int getChipsRemaining()
	{
		return this.chipsRemaining;
	}
	
	/**
	 * @return The maze represented by a 2D array of tiles.
	 */
	public Tile[][] getMaze()
	{
		return maze;
	}
	
	/**
	 * @return All the interactables currently inside the maze
	 */
	public List<Interactable> getInteractables()
	{
		return this.interactables;
	}
	
	/**
	 * @return All the actor currently inside the maze
	 */
	public List<Actor> getActors()
	{
		return this.actors;
	}
	
	public JsonObject persist() {
		//TODO: Implement this
		throw new UnsupportedOperationException("This feature hasn't been implemented yet.");
	}

	public String getName() 
	{
		return "state";
	}

}
