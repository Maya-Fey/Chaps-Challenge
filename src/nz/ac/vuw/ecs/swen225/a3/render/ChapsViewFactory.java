package nz.ac.vuw.ecs.swen225.a3.render;

/**
 * A factory that produces a class that implements ChapsView
 * 
 * @author None yet
 */
public class ChapsViewFactory {

	/**
	 * Produces a new ChapsView by calling an appropriate constructor
	 * 
	 * @return A new ChapsView object
	 */
	public ChapsView produce()
	{
		return new ChapsViewImpl();
	} 
	
}
