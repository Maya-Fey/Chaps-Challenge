package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.Collection;
import java.util.EnumSet;

import nz.ac.vuw.ecs.swen225.a3.application.GameState;
import nz.ac.vuw.ecs.swen225.a3.commons.Visible;

/**
 * A factory that produces a class that implements ChapsModel
 * 
 * @author None yet
 */
public class ChapsModelFactory {

	/**
	 * Produces a new ChapsModel by calling an appropriate constructor
	 * 
	 * @return A new ChapsModel object
	 */
	public ChapsModel produce()
	{
		return new ChapsModelImpl();
//		throw new UnsupportedOperationException("This feature hasn't been implemented yet.");
	}
	
}
