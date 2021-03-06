package nz.ac.vuw.ecs.swen225.a3.plugin;

import java.util.ArrayList;
import java.util.List;

import nz.ac.vuw.ecs.swen225.a3.application.GameState;
import nz.ac.vuw.ecs.swen225.a3.commons.Contracts;
import nz.ac.vuw.ecs.swen225.a3.commons.GameConstants;
import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;
import nz.ac.vuw.ecs.swen225.a3.commons.List2D;
import nz.ac.vuw.ecs.swen225.a3.commons.RenderVisible;
import nz.ac.vuw.ecs.swen225.a3.commons.Visible;
import nz.ac.vuw.ecs.swen225.a3.maze.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.Interactable;
import nz.ac.vuw.ecs.swen225.a3.maze.InteractableChip;
import nz.ac.vuw.ecs.swen225.a3.maze.Inventory;
import nz.ac.vuw.ecs.swen225.a3.maze.ActorPlayer;
import nz.ac.vuw.ecs.swen225.a3.maze.Position;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
import nz.ac.vuw.ecs.swen225.a3.maze.TileFree;

/**
 * A model representing the level builder. Used for building the level's game state for
 * later exporting.
 * 
 * @author Claire
 */
public class LevelBuilderModel {
	
	private final List<Actor> actors;
	private final List<Interactable> interactables;

	private final List2D<Tile> tiles;
	
	@SuppressWarnings("unchecked")
	private final List<Visible>[][] buffer = (List<Visible>[][]) new List<?>[GameConstants.VISIBILE_SIZE][GameConstants.VISIBILE_SIZE];
	
	private int chips;
	private int time;
	
	{
		for(int i = 0; i < GameConstants.VISIBILE_SIZE; i++)
			for(int j = 0; j < GameConstants.VISIBILE_SIZE; j++)
				buffer[i][j] = new ArrayList<>();
	}
	
	/**
	 * Constructs a basic model with chap at the center position
	 */
	public LevelBuilderModel()
	{
		interactables = new ArrayList<>();
		actors = new ArrayList<>();
		tiles = new List2D<>(new TileFree());
		
		actors.add(new ActorPlayer());
		actors.get(0).setPosition(new Position(0, 0));
	}
	
	/**
	 * Constructs a model from existing state
	 * 
	 * @param state The state to use
	 */
	public LevelBuilderModel(GameState state)
	{
		Contracts.notNull(state, "Tried to initialize model with null state");
		
		Tile[][] raw = state.getMaze();
		
		this.tiles = new List2D<>(new TileFree());
		for(int i = 0; i < raw.length; i++)
			for(int j = 0; j < raw[i].length; j++)
				if(raw[i][j] != null)
					this.tiles.set(raw[i][j], raw[i][j].getPosition().x, raw[i][j].getPosition().y);
		
		this.actors = state.getActors();
		this.interactables = state.getInteractables();
		this.time = state.getTimeRemaining();
		this.chips = state.getChipsRemaining();
		
		int p = 0;
		for(Actor actor : actors)
			if(actor instanceof ActorPlayer)
				p++;
		
		Contracts.arbitrary(p == 1, "The number of players should be exactly one");
	}
	
	/**
	 * @return The state of this model represented by a GameState
	 */
	public GameState export()
	{
		return new GameState(tiles.export(Tile[].class, Tile.class), interactables, actors, new Inventory(), time, chips, 0);
	}
	
	/**
	 * Produces an array of visibles representing a visible section of the level, marked
	 * by an x and y center coordinate.
	 * 
	 * @param xc
	 * @param yc
	 * @return The array of visibles
	 */
	public Visible[][] render(int xc, int yc)
	{
		Visible[][] arr = new Visible[GameConstants.VISIBILE_SIZE][GameConstants.VISIBILE_SIZE];
		
		int minx = xc - GameConstants.VISIBILE_SIZE / 2;
		int miny = yc - GameConstants.VISIBILE_SIZE / 2;
		int maxx = xc + GameConstants.VISIBILE_SIZE / 2;
		int maxy = yc + GameConstants.VISIBILE_SIZE / 2;
		
		for(int i = 0; i < GameConstants.VISIBILE_SIZE; i++)
			for(int j = 0; j < GameConstants.VISIBILE_SIZE; j++)
				buffer[i][j].clear();
		
		for(Actor actor : actors)
		{
			int x = actor.getPosition().x;
			int y = actor.getPosition().y;
			if(minx <= x && x <= maxx)
				if(miny <= y && y <= maxy)
					buffer[x - minx][y - miny].add(actor);
		}
		
		for(Interactable interactable : interactables)
		{
			int x = interactable.getPosition().x;
			int y = interactable.getPosition().y;
			if(minx <= x && x <= maxx)
				if(miny <= y && y <= maxy)
					buffer[x - minx][y - miny].add(interactable);
		}
		
		for(int x = minx; x <= maxx; x++)
			for(int y = miny; y <= maxy; y++)
				buffer[x - minx][y - miny].add(tiles.get(x, y));
		
		for(int i = 0; i < GameConstants.VISIBILE_SIZE; i++)
			for(int j = 0; j < GameConstants.VISIBILE_SIZE; j++)
				arr[i][GameConstants.VISIBILE_SIZE - j - 1] = new RenderVisible(IconFactory.INSTANCE.composite(buffer[i][j]));
		//             ^ Invert the y-axis
		
		return arr;
	}
	
	/**
	 * @param time The desired amount of time allocated to complete the level
	 */
	public void setTime(int time)
	{
		this.time = time * GameConstants.TICKS_TO_SECONDS_RATIO;
	}
	
	/**
	 * @return The amount of time allocated to this level
	 */
	public int getTime()
	{
		return time;
	}
	
	/**
	 * @return The number of chips on the board
	 */
	public int getChips()
	{
		return chips;
	}
	
	/**
	 * Sets the tile at the specified position, overwriting any previous one
	 * 
	 * @param tile The tile to set/add
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 */
	public void addTile(Tile tile, int x, int y)
	{
		tile.setPosition(new Position(x, y));
		tiles.set(tile, x, y);
	}
	
	/**
	 * Sets the actor at the specified position, overwriting any previous one
	 * 
	 * @param actor The actor to set/add
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 */
	public void addActor(Actor actor, int x, int y)
	{
		if(actor instanceof ActorPlayer)
			return;
		
		Actor toRemove = null;
		for(Actor other : actors)
			if(other.getPosition().x == x && other.getPosition().y == y)
				toRemove = other;
		if(toRemove != null)
			actors.remove(toRemove);
		actor.setPosition(new Position(x, y));
		actors.add(actor);
	}
	
	/**
	 * Sets the tile at the specified position, overwriting any previous one
	 * 
	 * @param interactable The interactable to set/add
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 */
	public void addInteractable(Interactable interactable, int x, int y)
	{
		if(interactable instanceof InteractableChip)
			chips++;
		
		Interactable toRemove = null;
		for(Interactable other : interactables)
			if(other.getPosition().x == x && other.getPosition().y == y && other.isPushable() == interactable.isPushable())
				toRemove = other;
		
		if(toRemove != null)
			interactables.remove(toRemove);
		
		interactable.setPosition(new Position(x, y));
		interactables.add(interactable);
	}
	
}
