package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.commons.Contracts;
import nz.ac.vuw.ecs.swen225.a3.maze.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.ActorPlayer;
import nz.ac.vuw.ecs.swen225.a3.maze.ActorPlayerFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.Interactable;
import nz.ac.vuw.ecs.swen225.a3.maze.InteractableChip;
import nz.ac.vuw.ecs.swen225.a3.maze.InteractableChipFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.InteractableExitLock;
import nz.ac.vuw.ecs.swen225.a3.maze.InteractableExitLockFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.InteractableItem;
import nz.ac.vuw.ecs.swen225.a3.maze.InteractableItemFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.Item;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
import nz.ac.vuw.ecs.swen225.a3.maze.TileExit;
import nz.ac.vuw.ecs.swen225.a3.maze.TileExitFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.TileFree;
import nz.ac.vuw.ecs.swen225.a3.maze.TileFreeFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.TileMessage;
import nz.ac.vuw.ecs.swen225.a3.maze.TileMessageFactory;

/**
 * @author Claire
 *
 * A factory for all items in the chap's maze.
 */
public class RootFactory {
	
	private static final RootFactory instance = new RootFactory();
	
	static {
		Contracts.notNull(instance, "Initialization ordering error");
		
		reinitialize();
	}
	
	/**
	 * Reinitialize the root factory to default state with no external code
	 */
	public static final void reinitialize()
	{
		instance.tileFactory.clearAll();
		instance.interactableFactory.clearAll();
		instance.actorFactory.clearAll();
		
		instance.tileFactory.addFactory(TileFree.class.getSimpleName(), new TileFreeFactory());
		instance.tileFactory.addFactory(TileExit.class.getSimpleName(), new TileExitFactory());
		instance.tileFactory.addFactory(TileMessage.class.getSimpleName(), new TileMessageFactory());
		
		instance.interactableFactory.addFactory(InteractableChip.class.getSimpleName(), new InteractableChipFactory());
		instance.interactableFactory.addFactory(InteractableItem.class.getSimpleName(), new InteractableItemFactory());
		instance.interactableFactory.addFactory(InteractableExitLock.class.getSimpleName(), new InteractableExitLockFactory());
		
		instance.actorFactory.addFactory(ActorPlayer.class.getSimpleName(), new ActorPlayerFactory());
	}

	/**
	 * @return The canonical instance of the root factory class
	 */
	public static RootFactory getInstance()
	{
		return instance;
	}
	
	/**
	 * The tile factory
	 */
	public final MultiFactory<Tile> tileFactory = new MultiFactory<Tile>();
	
	/**
	 * The interactable factory
	 */
	public final MultiFactory<Interactable> interactableFactory = new MultiFactory<Interactable>();
	
	/**
	 * The actor factory
	 */
	public final MultiFactory<Actor> actorFactory = new MultiFactory<Actor>();
	
	/**
	 * The item factory
	 */
	public final MultiFactory<Item> itemFactory = new MultiFactory<Item>();
	
}
