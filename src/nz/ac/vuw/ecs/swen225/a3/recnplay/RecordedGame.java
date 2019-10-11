package nz.ac.vuw.ecs.swen225.a3.recnplay;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import nz.ac.vuw.ecs.swen225.a3.application.GameState;
import nz.ac.vuw.ecs.swen225.a3.commons.Persistable;
import nz.ac.vuw.ecs.swen225.a3.maze.ChapsAction;

/**
 * A class that is used to record a game of chap's challenge.
 * 
 * @author Claire
 */
public class RecordedGame implements Persistable {

	private final GameState startingState;
	
	private final List<ChapsAction> actions = new ArrayList<>();
	
	/**
	 * Creates a recorded game from a starting state. Actions are added on
	 * afterwards with addAction()
	 * 
	 * @param state The initial state
	 */
	public RecordedGame(GameState state)
	{
		this.startingState = state;
	}
	
	/**
	 * Adds an action to this recording
	 * 
	 * @param action
	 */
	public void addAction(ChapsAction action)
	{
		this.actions.add(action);
	}
	
	@Override
	public JsonObject persist() 
	{
		JsonArrayBuilder aBuilder = Json.createArrayBuilder();
		for(ChapsAction action : actions)
			aBuilder = aBuilder.add(action.toString());
		
		JsonObjectBuilder builder = Json.createObjectBuilder()
									    .add("level", startingState.getLevel())
									    .add("startingState", startingState.persist())
									    .add("actions", aBuilder.build());
		
		return builder.build();
	}

}
