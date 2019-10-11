package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nz.ac.vuw.ecs.swen225.a3.application.GameState;
import nz.ac.vuw.ecs.swen225.a3.commons.Contracts;
import nz.ac.vuw.ecs.swen225.a3.commons.GameConstants;
import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;
import nz.ac.vuw.ecs.swen225.a3.commons.List2D;
import nz.ac.vuw.ecs.swen225.a3.commons.RenderVisible;
import nz.ac.vuw.ecs.swen225.a3.commons.Visible;

/**
 * Class to implement the main package controlling the game
 * @author James
 *
 */
public class ChapsModelImpl implements ChapsModel, ModelAccessObject {
	
	private Set<ChapsEvent> events = new HashSet<>();
	
	private List2D<Tile> tiles = new List2D<>(new TileFree());
	
	@SuppressWarnings("unchecked")
	private List<Visible>[][] buffer = new List[GameConstants.VISIBILE_SIZE][GameConstants.VISIBILE_SIZE];
	
	private List<Actor> actors;
	private List<Interactable> interactables;
	
	private Inventory inv;
	
	private ActorPlayer player;

	private int timeRemaining, chipsRemaining;
	
	private int level;
	
	private String message;
	
	/**
	 * Constructor for empty ChapsModel
	 */
	public ChapsModelImpl() 
	{
		for(int i = 0; i < GameConstants.VISIBILE_SIZE; i++)
			for(int j = 0; j < GameConstants.VISIBILE_SIZE; j++)
				buffer[i][j] = new ArrayList<>();
	}
	
	/**
	 * Returns a clone of the actors
	 * @return
	 */
	private List<Actor> cloneActors()
	{
		List<Actor> clone = new ArrayList<>();

		for(Actor a:actors) {
			clone.add(a.clone());
		}

		return clone;
	}

	/**
	 * returns a list of cloned interactables
	 * @return
	 */
	private List<Interactable> cloneInteractables()
	{
		List<Interactable> clone = new ArrayList<>();
		for(Interactable i : interactables) {
			clone.add((Interactable) i.clone());
		}
		return clone;
	}
	
	/**
	 * Goes through the list of actors returning the playable character
	 * @return The player in this game
	 */
	private ActorPlayer findPlayer()
	{
		for(Actor a : actors) 
			if(a instanceof ActorPlayer) 
				return (ActorPlayer) a;
		return null;
	}
	
	/**
	 * Returns all the interactables within a certain space
	 * 
	 * @return A list of interactables at the given position
	 */
	private List<Interactable> getInteractablesAt(Position pos)
	{
		List<Interactable> interactables = new ArrayList<>();
		for(Interactable interactable : this.interactables)
			if(interactable.getPosition().x == pos.x && interactable.getPosition().y == pos.y)
				interactables.add(interactable);
		return interactables;
	}
	
	@Override
	public boolean canMoveTo(Position pos)
	{
		Tile tile = tiles.get(pos.x, pos.y);
		List<Interactable> interactables = getInteractablesAt(pos);
		boolean hasPushable = false;
		for(Interactable interactable : interactables)
			if(interactable.isPushable())
				hasPushable = true;
		return !hasPushable && tile.isFloor();
	}

	@Override
	public EnumSet<ChapsEvent> onAction(ChapsAction action) {
		events.clear();
		
		if(action.equals(ChapsAction.TICK)) {
			
			events.add(ChapsEvent.TIME_UPDATE_REQUIRED);
			
			timeRemaining--;
			
			if(timeRemaining <= 0)
				events.add(ChapsEvent.GAME_LOST_TIME_OUT);			
			
			for(Actor actor : actors)
				if(actor.tick(this))
					events.add(ChapsEvent.DISPLAY_UPDATE_REQUIRED);
		}
		
		for(int i = 0; i < actors.size(); i++)
			for(int j = i + 1; j < actors.size(); j++)
			{
				Actor a1 = actors.get(i);
				Actor a2 = actors.get(j);
				if(a1.getPosition().x == a2.getPosition().x && a1.getPosition().y == a2.getPosition().y) {
					a1.onCollide(a2, this);
					a2.onCollide(a1, this);
				}
			}
		
		root:
		if(action.equals(ChapsAction.UP) || action.equals(ChapsAction.DOWN) || action.equals(ChapsAction.LEFT) || action.equals(ChapsAction.RIGHT)) {
			
			if(message != null) {
				message = null;
				events.add(ChapsEvent.HIDE_TUTORIAL_MESSAGE);
			}
			
			Position potentialNewPos = player.getPosition().translate(action);
			
			Tile tile = tiles.get(potentialNewPos.x, potentialNewPos.y);
			
			if(tile instanceof TileExit)
			{
				player.setPosition(potentialNewPos);
				events.add(ChapsEvent.DISPLAY_UPDATE_REQUIRED);
				events.add(ChapsEvent.PLAYER_WINS);
				break root;
			}
			
			if(tile.isFloor()) {
				
				if(!tile.isSafe(player, this)) {
					events.add(ChapsEvent.GAME_LOST_PLAYER_DIED);
					break root;
				}
				
				List<Interactable> interactables = this.getInteractablesAt(potentialNewPos);
				for(Interactable interactable : interactables)
				{
					if(interactable.isPushable())
					{
						if(this.canMoveTo(potentialNewPos.translate(action)))
						{
							Position thePos = potentialNewPos.translate(action);
							interactable.setPosition(thePos);
							tiles.get(thePos.x, thePos.y).onEnter(interactable, this);
						} else {
							break root;
						}
					}
				}
				
				for(Interactable interactable : interactables)
				{
					if(!interactable.isPushable())
					{
						if(!interactable.isWalkable(player, this)) {
							break root;
						} else if(!interactable.isSafe(player, this)) {
							events.add(ChapsEvent.GAME_LOST_PLAYER_DIED);
							break root;
						} else {
							interactable.onEnter(player, this);
						}
					}
				}
				
				tile.onEnter(player, this);
				
				player.setPosition(potentialNewPos);
				events.add(ChapsEvent.DISPLAY_UPDATE_REQUIRED);
				
			}
		}
		
		EnumSet<ChapsEvent> enumEvents;
		if(events.size() != 0)
			enumEvents = EnumSet.copyOf(events);
		else 
			enumEvents = EnumSet.noneOf(ChapsEvent.class);
		
		return enumEvents;
	}

	@Override
	public GameState getState() 
	{
		Inventory cInventory = inv.clone();
		List<Actor> actors = cloneActors();
		List<Interactable> interactables = cloneInteractables();
		Tile[][] maze = this.tiles.export(Tile[].class, Tile.class);
		return new GameState(maze, interactables, actors, cInventory, timeRemaining, chipsRemaining, level);
	}

	@Override
	public void setState(GameState state) 
	{
		Tile[][] raw = state.getMaze();
		
		this.tiles = new List2D<>(new TileFree());
		for(int i = 0; i < raw.length; i++)
			for(int j = 0; j < raw[i].length; j++)
				if(raw[i][j] != null)
					this.tiles.set(raw[i][j], raw[i][j].getPosition().x, raw[i][j].getPosition().y);
		
		this.actors = state.getActors();
		this.interactables = state.getInteractables();
		this.inv = state.getInventory();
		this.timeRemaining = state.getTimeRemaining();
		this.chipsRemaining = state.getChipsRemaining();
		
		this.level = state.getLevel();
		
		this.player = findPlayer();
		
		Contracts.notNull(player, "A valid GameState should contain a player");
	}

	@Override
	public Visible[][] getVisibleArea() 
	{
		Visible[][] arr = new Visible[GameConstants.VISIBILE_SIZE][GameConstants.VISIBILE_SIZE];
		
		int xc = player.getPosition().x;
		int yc = player.getPosition().y;
		
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection<Visible> getInventoryIcons() 
	{
		return (Collection<Visible>) ((Collection) inv.getAll());
	}

	@Override
	public int getTimeRemaining() 
	{
		return timeRemaining;
	}

	@Override
	public int getChipsRemaining() 
	{
		return chipsRemaining;
	}
	
	@Override
	public String getTutorialMessage()
	{
		return message;
	}

	@Override
	public void remChips(int chips) 
	{
		events.add(ChapsEvent.CHIPS_UPDATE_REQUIRED);
		this.chipsRemaining -= chips;
	}

	@Override
	public void removeInteractable(Interactable interact) 
	{
		events.add(ChapsEvent.DISPLAY_UPDATE_REQUIRED);
		interactables.remove(interact);
	}

	@Override
	public Inventory getInventory() 
	{
		events.add(ChapsEvent.INV_UPDATE_REQUIRED);
		return this.inv;
	}

	@Override
	public boolean hasAllChips() 
	{
		return this.chipsRemaining == 0;
	}

	@Override
	public void setTutorialMessage(String message) 
	{
		events.add(ChapsEvent.SHOW_TUTORIAL_MESSAGE);
		this.message = message;
	}

	@Override
	public void setTile(Tile tile, int x, int y) 
	{
		events.add(ChapsEvent.DISPLAY_UPDATE_REQUIRED);
		tile.setPosition(new Position(x, y));
		tiles.set(tile, x, y);
	}

	@Override
	public void killPlayer() 
	{
		events.add(ChapsEvent.GAME_LOST_PLAYER_DIED);
	}

	@Override
	public Tile getTile(int x, int y) {
		return tiles.get(x, y);
	}

}
