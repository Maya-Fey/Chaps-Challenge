package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import nz.ac.vuw.ecs.swen225.a3.application.GameState;
import nz.ac.vuw.ecs.swen225.a3.commons.List2D;
import nz.ac.vuw.ecs.swen225.a3.commons.Visible;

/**
 * Class to implement the main package controlling the game
 * @author James
 *
 */
public class ChapsModelImpl implements ChapsModel, ModelAccessObject {
	
	private List2D<Tile> maze = new List2D<>(new FreeTile());
	
	private Tile[][] maze2;
	
	private List<Actor> actors;
	private List<Interactable> interactables;
	
	private Inventory inv;

	private int timeRemaining, chipsRemaining;
	
	/**
	 * Constructor for empty ChapsModel
	 */
	public ChapsModelImpl() {}
	
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
		for(Interactable i:interactables) {
			clone.add(i.clone());
		}
		return clone;
	}

	@Override
	public EnumSet<ChapsEvent> onAction(ChapsAction action) {
		List<ChapsEvent> events = new ArrayList<ChapsEvent>();
		//update time if tick action requiring time update
		if(action.equals(ChapsAction.TICK)) {
			events.add(ChapsEvent.TIME_UPDATE_REQUIRED);
			timeRemaining--;
			//return game over time time <= to 0
			if(timeRemaining<=0)
				events.add(ChapsEvent.GAME_LOST_TIME_OUT);
		}


		if(action.equals(ChapsAction.UP)||action.equals(ChapsAction.DOWN)||action.equals(ChapsAction.LEFT)||action.equals(ChapsAction.RIGHT)) {
			//find player actor throwing error if none found

			Player player = findPlayer();
			
			Position newPos = player.getPosition().translate(action);
		}
		
		EnumSet<ChapsEvent> enumEvents = EnumSet.copyOf(events);
		return enumEvents;
	}

	/**
	 * Goes through the list of actors returning the playable character
	 * @return
	 */
	private Player findPlayer()
	{
		for(Actor a : actors) {
			if (a instanceof Player) {
				return (Player)a;
			}
		}

		return null;
	}

	@Override
	public GameState getState() 
	{
		Inventory cInventory = inv.clone();
		List<Actor> actors = cloneActors();
		List<Interactable> interactables = cloneInteractables();
		Tile[][] maze = this.maze.export();
		return new GameState(maze, interactables, actors, cInventory, timeRemaining, chipsRemaining);
	}

	@Override
	public void setState(GameState state) 
	{
		Tile[][] raw = state.getMaze();
		
		this.maze = new List2D<>(new FreeTile());
		for(int i = 0; i < raw.length; i++)
			for(int j = 0; j < raw[i].length; j++)
				if(raw[i][j] != null)
					this.maze.set(raw[i][j], i, j);
		
		this.actors = state.getActors();
		this.interactables = state.getInteractables();
		this.inv = state.getInventory();
		this.timeRemaining = state.getTimeRemaining();
		this.chipsRemaining = state.getChipsRemaining();
	}

	@Override
	public Visible[][] getVisibleArea() {
		//temporary until method has been further developed
		return null;
	}

	@Override
	public Collection<Visible> getInventoryIcons() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTimeRemaining() {
		return timeRemaining;
	}

	@Override
	public int getChipsRemaining() {
		return chipsRemaining;
	}

}
