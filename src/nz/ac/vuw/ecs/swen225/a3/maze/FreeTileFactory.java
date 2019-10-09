package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * A factory for the free tile
 * 
 * @author Claire
 */
public class FreeTileFactory extends MazeObjectFactory<FreeTile> {

	/**
	 * Constructor
	 */
	public FreeTileFactory() 
	{
		super(() -> { return new FreeTile(); });
	}

	
	
}
