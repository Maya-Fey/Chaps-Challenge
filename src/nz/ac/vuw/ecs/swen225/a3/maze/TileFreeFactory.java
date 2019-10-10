package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * A factory for the free tile
 * 
 * @author Claire
 */
public class TileFreeFactory extends MazeObjectFactory<TileFree> {

	/**
	 * Constructor
	 */
	public TileFreeFactory() 
	{
		super(() -> { return new TileFree(); });
	}

	
	
}
