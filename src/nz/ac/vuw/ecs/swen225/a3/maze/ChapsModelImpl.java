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


	@Override
	public EnumSet<ChapsEvent> onAction(ChapsAction action) {
		List<ChapsEvent> events = new ArrayList<ChapsEvent>();
		if(action.equals(ChapsAction.TICK)) {
			events.add(ChapsEvent.TIME_UPDATE_REQUIRED);
			timeRemaining--;
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

	private List<ChapsEvent> convertToFreeTile(List<ChapsEvent> events, Position newPos){
		FreeTile newTile = maze[newPos.x][newPos.y].convertToFreeTile();
		Tile mazeTile = maze[newPos.x][newPos.y];
		if(newTile!=null) {
			if(mazeTile instanceof Key || mazeTile instanceof Treasure)
				events.add(ChapsEvent.INV_UPDATE_REQUIRED);

			if(mazeTile instanceof InfoField)
				events.add(ChapsEvent.SHOW_TUTORIAL_MESSAGE);
			else
				events.add(ChapsEvent.HIDE_TUTORIAL_MESSAGE);

			maze[newPos.x][newPos.y] = newTile;
		}

		return events;
	}

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
