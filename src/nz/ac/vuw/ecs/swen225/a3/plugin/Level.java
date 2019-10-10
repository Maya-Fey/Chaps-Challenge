package nz.ac.vuw.ecs.swen225.a3.plugin;

import java.util.Set;

import javax.json.JsonObject;

import nz.ac.vuw.ecs.swen225.a3.application.GameState;
import nz.ac.vuw.ecs.swen225.a3.application.GameStateFactory;
import nz.ac.vuw.ecs.swen225.a3.application.RootFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.Interactable;
import nz.ac.vuw.ecs.swen225.a3.maze.Item;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;

/**
 * A class representing a level.
 * 
 * @author Claire
 */
public class Level {
	
	private final GameStateFactory stateFactory = new GameStateFactory();
	
	private final JsonObject state;
	
	private final Set<ExternalCodeLoader> loaders;
	
	/**
	 * @param state
	 * @param loaders
	 */
	public Level(JsonObject state, Set<ExternalCodeLoader> loaders)
	{
		this.state = state;
		this.loaders = loaders;
		
		try {
			this.load();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError("Error: invalid level, could not ressurect.");
		}
	}
	
	/**
	 * Loads this level and returns the GameState to initialize the model
	 * 
	 * @return The GameState necessary to initialize the model
	 */
	public GameState load()
	{
		RootFactory.reinitialize();       //Remove all previously loaded code to prevent conflicts
		this.onLoad();                    //Load all external code
		return stateFactory.resurrect(this.state);
	}
	
	/**
	 * Load all required external code
	 */
	private void onLoad()
	{
		for(ExternalCodeLoader loader : loaders)
		{
			for(ExternalChapsClass<Tile> ext : loader.tileClasses) 
				RootFactory.getInstance().tileFactory.addFactory(ext.getTypename(), ext.getFactory());
			for(ExternalChapsClass<Actor> ext : loader.actorClasses) 
				RootFactory.getInstance().actorFactory.addFactory(ext.getTypename(), ext.getFactory());
			for(ExternalChapsClass<Interactable> ext : loader.interactableClasses) 
				RootFactory.getInstance().interactableFactory.addFactory(ext.getTypename(), ext.getFactory());
			for(ExternalChapsClass<Item> ext : loader.itemClasses) 
				RootFactory.getInstance().itemFactory.addFactory(ext.getTypename(), ext.getFactory());
		}
	}
	
}
