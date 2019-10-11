package nz.ac.vuw.ecs.swen225.a3.application;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import nz.ac.vuw.ecs.swen225.a3.commons.ChapsFactory;
import nz.ac.vuw.ecs.swen225.a3.commons.Contracts;
import nz.ac.vuw.ecs.swen225.a3.commons.List2D;
import nz.ac.vuw.ecs.swen225.a3.maze.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.Interactable;
import nz.ac.vuw.ecs.swen225.a3.maze.Inventory;
import nz.ac.vuw.ecs.swen225.a3.maze.InventoryFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
import nz.ac.vuw.ecs.swen225.a3.maze.TileFree;

/**
 * A factory for GameState
 * 
 * @author Claire 300436297
 */
public class GameStateFactory implements ChapsFactory<GameState> 
{	
	private final ChapsFactory<Inventory> invFactory = new InventoryFactory();
	private final ChapsFactory<Tile> tileFactory = RootFactory.getInstance().tileFactory;
	private final ChapsFactory<Interactable> interactableFactory = RootFactory.getInstance().interactableFactory;
	private final ChapsFactory<Actor> actorFactory = RootFactory.getInstance().actorFactory;
	
	/**
	 * Creates a GameStateFactory, ensuring that all the necessary sub-factories exist
	 */
	public GameStateFactory()
	{
		Contracts.notNull(RootFactory.getInstance().tileFactory, "Race Condition/Initialization problem: Tile factory not initialized by the time GameStateFactory was constructed.");
		Contracts.notNull(RootFactory.getInstance().interactableFactory, "Race Condition/Initialization problem: Interactable factory not initialized by the time GameStateFactory was constructed.");
		Contracts.notNull(RootFactory.getInstance().actorFactory, "Race Condition/Initialization problem: Actor factory not initialized by the time GameStateFactory was constructed.");
	}
	
	public GameState resurrect(JsonObject obj) 
	{
		int timeRem = obj.getInt("timeRemaining");
		int chipRem = obj.getInt("chipsRemaining");
		int level = obj.getInt("level");
		
		Inventory inv = invFactory.resurrect(obj.getJsonObject("inventory"));
		
		List2D<Tile> tiles = new List2D<>(new TileFree());
		JsonArray jTiles = obj.getJsonArray("tiles");
		jTiles.forEach((JsonValue v) -> {
			JsonObject tObj = (JsonObject) v;
			Tile tile = tileFactory.resurrect(tObj);
			tiles.set(tile, tile.getPosition().x, tile.getPosition().y);
		});
		
		List<Interactable> interactables = new ArrayList<Interactable>();
		JsonArray iArray = obj.getJsonArray("interactables");
		for(int i = 0; i < iArray.size(); i++)
			interactables.add(interactableFactory.resurrect(iArray.getJsonObject(i)));
		
		List<Actor> actors = new ArrayList<Actor>();
		JsonArray aArray = obj.getJsonArray("actors");
		for(int i = 0; i < aArray.size(); i++)
			actors.add(actorFactory.resurrect(aArray.getJsonObject(i)));
		
		return new GameState(tiles.export(Tile[].class, Tile.class), interactables, actors, inv, timeRem, chipRem, level);
	}

	@Override
	public GameState newInstance() 
	{
		throw new UnsupportedOperationException();
	}

}
