package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.maze.ChapsModelFactory;
import nz.ac.vuw.ecs.swen225.a3.render.ChapsViewFactory;

/**
 * A factory that produces a class that implements ChapsView
 * 
 * @author None yet
 */
public class ChapsControllerFactory {

	/**
	 * Produces a new ChapsView by calling an appropriate constructor
	 * 
	 * @param modelFactory A factory that the controller can use to produce the model
	 * @param viewFactory A factor that the controller can ues to produce the view
	 * 
	 * @return A new ChapsView object
	 */
	public ChapsController produce(ChapsModelFactory modelFactory, ChapsViewFactory viewFactory)
	{
		//FIXME: Implement this
		throw new UnsupportedOperationException("This feature hasn't been implemented yet.");
	}
	
}
