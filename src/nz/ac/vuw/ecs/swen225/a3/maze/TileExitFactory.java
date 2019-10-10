package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * @author Claire
 */
public class TileExitFactory extends MazeObjectFactory<TileExit> {

	/**
	 * Constructor
	 */
	public TileExitFactory() 
	{	
		super(() -> { return new TileExit(); });
	}

}
