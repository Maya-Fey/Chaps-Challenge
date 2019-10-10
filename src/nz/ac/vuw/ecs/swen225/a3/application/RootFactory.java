package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.commons.Contracts;
import nz.ac.vuw.ecs.swen225.a3.maze.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.FreeTile;
import nz.ac.vuw.ecs.swen225.a3.maze.FreeTileFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.Interactable;
import nz.ac.vuw.ecs.swen225.a3.maze.InteractableChip;
import nz.ac.vuw.ecs.swen225.a3.maze.InteractableChipFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;

/**
 * @author Claire
 *
 * A factory for all items in the chap's maze.
 */
public class RootFactory {
	
	private static final RootFactory instance = new RootFactory();
	
	static {
		Contracts.notNull(instance, "Initialization ordering error");
		
		instance.tileFactory.addFactory(FreeTile.class.getSimpleName(), new FreeTileFactory());
		
		instance.interactableFactory.addFactory(InteractableChip.class.getSimpleName(), new InteractableChipFactory());
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
	
}
