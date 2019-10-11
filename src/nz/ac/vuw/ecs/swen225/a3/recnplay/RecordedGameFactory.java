package nz.ac.vuw.ecs.swen225.a3.recnplay;

import javax.json.JsonArray;
import javax.json.JsonObject;

import nz.ac.vuw.ecs.swen225.a3.application.GameState;
import nz.ac.vuw.ecs.swen225.a3.application.GameStateFactory;
import nz.ac.vuw.ecs.swen225.a3.commons.ChapsFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.ChapsAction;

/**
 * @author Claire
 */
public class RecordedGameFactory implements ChapsFactory<RecordedGame> {

	@Override
	public RecordedGame resurrect(JsonObject obj) 
	{
		GameStateFactory factory = new GameStateFactory();
		GameState state = factory.resurrect(obj.getJsonObject("startingState"));
		RecordedGame game = new RecordedGame(state);
		JsonArray arr = obj.getJsonArray("actions");
		for(int i = 0; i < arr.size(); i++)
			game.addAction(ChapsAction.valueOf(arr.getString(i)));
		return game;
	}
	
	/**
	 * @param object The recorded game, in JSON
	 * @return The level of this recorded game
	 */
	public int levelOf(JsonObject object)
	{
		return object.getInt("level");
	}

	@Override
	public RecordedGame newInstance() 
	{
		throw new UnsupportedOperationException();
	}

}
