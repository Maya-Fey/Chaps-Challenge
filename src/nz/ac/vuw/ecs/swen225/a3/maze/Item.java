package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.commons.Persistable;
import nz.ac.vuw.ecs.swen225.a3.commons.Visible;

/**
 * An item existing in the player's inventory or on the ground 
 * 
 * @author None yet
 */
public interface Item extends Persistable, Visible {
	
	@Override
	default String getName()
	{
		return "item";
	}

}
