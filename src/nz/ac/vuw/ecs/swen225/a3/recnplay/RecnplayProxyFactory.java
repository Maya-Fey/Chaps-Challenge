package nz.ac.vuw.ecs.swen225.a3.recnplay;

import nz.ac.vuw.ecs.swen225.a3.maze.ChapsModel;

/**
 * A factory to create a Recnplay Proxy for a model.
 * 
 * @author Claire
 */
public class RecnplayProxyFactory {
	
	/**
	 * @param model This game's model
	 * @return A proxy that passes on all events to the real model but records them
	 * for potentially saving to a file
	 */
	RecnplayProxy createProxy(ChapsModel model)
	{
		//FIXME: Implement this
		throw new UnsupportedOperationException("This feature hasn't been implemented yet.");
	}

}
