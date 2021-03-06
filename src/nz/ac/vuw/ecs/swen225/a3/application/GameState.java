package nz.ac.vuw.ecs.swen225.a3.application;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import nz.ac.vuw.ecs.swen225.a3.commons.Contracts;
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
	
	private int level;
	
	/**
	 * @param maze
	 * @param interactables
	 * @param actors
	 * @param inv
	 * @param timeRemaining
	 * @param chipsRemaining
	 * @param level
	 */
	public GameState(Tile[][] maze, List<Interactable> interactables, List<Actor> actors, Inventory inv, int timeRemaining, int chipsRemaining, int level)
	{
		Contracts.notNull(maze, "GameState must have no null fields");
		Contracts.notNull(maze[0], "GameState must have no null fields");
		Contracts.notNull(interactables, "GameState must have no null fields");
		Contracts.notNull(actors, "GameState must have no null fields");
		Contracts.notNull(inv, "GameState must have no null fields");
		
		this.maze = maze;
		this.interactables = interactables;
		this.actors = actors;
		this.inv = inv;
		this.timeRemaining = timeRemaining;
		this.chipsRemaining = chipsRemaining;
		this.level = level;
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
	
	/**
	 * @return The level that was specified by the creator of this object
	 */
	public int getLevel()
	{
		return level;
	}
	
	/**
	 * @param level
	 */
	public void setLevel(int level)
	{
		this.level = level;
	}
	
	public JsonObject persist() {		
		JsonArrayBuilder tiles = Json.createArrayBuilder();
		for(int x = 0; x < maze.length; x++) 
			for(int y = 0; y < maze[0].length; y++)
				if(maze[x][y] != null)
					tiles.add(maze[x][y].persist());
		
		JsonArrayBuilder interactables = Json.createArrayBuilder();
		for(Interactable interactable : this.interactables)
			interactables.add(interactable.persist());
		
		JsonArrayBuilder actors = Json.createArrayBuilder();
		for(Actor actor : this.actors)
			actors = actors.add(actor.persist());
		
		JsonObjectBuilder builder = Json.createObjectBuilder();
		
		builder = builder.add("timeRemaining", timeRemaining)
						 .add("chipsRemaining", chipsRemaining)
						 .add("level", level)
						 .add("tiles", tiles.build())
						 .add("inventory", inv.persist())
						 .add("interactables", interactables.build())
						 .add("actors", actors.build());
		
		return builder.build();
	}

}
