package nz.ac.vuw.ecs.swen225.a3.maze;


import java.awt.Event;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import nz.ac.vuw.ecs.swen225.a3.application.GameState;
import nz.ac.vuw.ecs.swen225.a3.commons.Visible;

/**
 * Class to implement the main package controlling the game
 * @author James
 *
 */
public class ChapsModelImpl implements ChapsModel{
	private Tile[][] maze;
	private List<Actor> actors;
	private List<Interactable> interactables;
	private Inventory inv;

	private int timeRemaining, chipsRemaining;



	/**
	 * Constructor for new ChapsModelImpl object
	 * @param maze
	 * @param actors
	 * @param interactables
	 * @param inv
	 * @param timeRemaining
	 * @param chipsRemaining
	 */
	public ChapsModelImpl(Tile[][] maze, List<Actor> actors, List<Interactable> interactables, Inventory inv,
			int timeRemaining, int chipsRemaining) {
		super();
		this.maze = maze;
		this.actors = actors;
		this.interactables = interactables;
		this.inv = inv;
		this.timeRemaining = timeRemaining;
		this.chipsRemaining = chipsRemaining;
	}
	
	

	/**
	 * Constructor for empty ChapsModel
	 */
	public ChapsModelImpl() {
		super();
	}



	public ChapsModelImpl clone() {
		Tile[][] cloneMaze = cloneMaze();
		List<Actor> cloneActors = cloneActors();
		List<Interactable> cloneInteractable = cloneInteractable();

		return new ChapsModelImpl(cloneMaze, cloneActors, cloneInteractable, inv.clone(), timeRemaining, chipsRemaining);

	}

	/**
	 * returns a clone of the maze
	 * @return
	 */
	private Tile[][] cloneMaze() {
		Tile[][] newMaze = new Tile[maze.length][maze[0].length];
		for(int x=0;x<maze.length;x++) {
			for(int y=0;y<maze[x].length;y++) {
				newMaze[x][y]= maze[x][y].clone();
			}
		}
		return maze;

	}

	/**
	 * Returns a clone of the actors
	 * @return
	 */
	private List<Actor> cloneActors(){
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
	private List<Interactable> cloneInteractable(){
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

			if(maze[newPos.x][newPos.y].isSafe(player)) {
				//call on entry
				maze[newPos.x][newPos.y].onEnter(player, null);

				//update players position
				player.setPosition(newPos);

				//if needed convert to a free tile
				events.addAll(convertToFreeTile(events, newPos));
				//add chap events
				events.add(ChapsEvent.DISPLAY_UPDATE_REQUIRED);
			}
		}



		EnumSet<ChapsEvent> enumEvents = EnumSet.copyOf(events);
		return enumEvents;
	}


	/**
	 * Method which takes a position and converts that tile into a free tile
	 * will return null if that tile isn't designed to be converted into a free tile
	 * doesn't return the new tile as it is updated in maze[][]
	 * @param events
	 * @param newPos
	 * @return any updates are needed
	 */
	private List<ChapsEvent> convertToFreeTile(List<ChapsEvent> events, Position newPos){
		FreeTile newTile = maze[newPos.x][newPos.y].convertToFreeTile();
		Tile mazeTile = maze[newPos.x][newPos.y];
		if(newTile!=null) {
			//if key or treasure item must be picked up and added to inventory requiring inv update
			if(mazeTile instanceof Key) {
				events.add(ChapsEvent.INV_UPDATE_REQUIRED);
				//add item to players inventory
				Key tile = (Key) mazeTile;
				inv.addItem(tile.getItem());
			}

			if(mazeTile instanceof Treasure) {
				events.add(ChapsEvent.INV_UPDATE_REQUIRED);
				//add item to players inventory
				Treasure tile = (Treasure) mazeTile;
				inv.addItem(tile.getItem());
			}


			//if player is on an info field display contents of it else don't display any
			if(mazeTile instanceof InfoField)
				events.add(ChapsEvent.SHOW_TUTORIAL_MESSAGE);
			else
				events.add(ChapsEvent.HIDE_TUTORIAL_MESSAGE);

			//after doing processing with old tile convert to new freeTile
			maze[newPos.x][newPos.y] = newTile;
		}

		return events;
	}

	/**
	 * Goes through the list of actors returning the playable character
	 * @return
	 */
	private Player findPlayer(){
		for(Actor a : actors) {
			if (a instanceof Player) {
				return (Player)a;
			}
		}

		return null;
	}

	@Override
	public GameState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setState(GameState state) {
		this.maze = state.getMaze();
		this.actors = state.getActors();
		this.interactables = state.getInteractables();
		this.inv = state.getInventory();
		this.timeRemaining = state.getTimeRemaining();
		this.chipsRemaining = state.getChipsRemaining();
	}

	@Override
	public Visible[][] getVisibleArea() {
		//temporary until method has been further developed
		return maze;
	}

	@Override
	public Collection<Visible> getInventoryIcons() {
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
