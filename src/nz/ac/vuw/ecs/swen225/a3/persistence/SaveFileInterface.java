package nz.ac.vuw.ecs.swen225.a3.persistence;

import java.io.File;
import java.io.IOException;

import javax.json.JsonObject;

import nz.ac.vuw.ecs.swen225.a3.application.GameState;
import nz.ac.vuw.ecs.swen225.a3.application.GameStateFactory;
import nz.ac.vuw.ecs.swen225.a3.commons.Contracts;

/**
 * The interface between the application and the primary save file.
 * Takes in gamestates and persists them so they last beyond application close.
 * 
 * @author Claire 300436297
 */
public class SaveFileInterface {

	private static final File SAVE_LOCATION = new File("chaps.save");
	
	private static final GameStateFactory factory = new GameStateFactory();
	
	/**
	 * Saves the game state at a specified location for later recall.
	 * 
	 * @param state The game state that needs to be saved
	 * 
	 * @throws IOException If there's an error writing to the file
	 */
	public static void save(GameState state) throws IOException
	{
		Contracts.notNull(state, "State must not be null");
		
		JsonObject saveObj = state.persist();
		
		JsonFileInterface.saveToFile(saveObj, SAVE_LOCATION);
	}
	
	/**
	 * @return The state that was saved to the file, or <code>null</code> if the file
	 * doesn't exist or is invalid.
	 */
	public static GameState load() 
	{
		if(SAVE_LOCATION.exists() && SAVE_LOCATION.isFile())
			return null;
		
		try {
			
			JsonObject saveObj = JsonFileInterface.loadFromFile(SAVE_LOCATION);
		
			return factory.resurrect(saveObj);
			
		} catch (IOException e) {
			return null;
		}
	}

}
