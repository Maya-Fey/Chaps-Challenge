package nz.ac.vuw.ecs.swen225.a3.levelbuilder.tmp;

import nz.ac.vuw.ecs.swen225.a3.maze.MazeObjectFactory;

/**
 * Factory for WallTile
 * @author Claire
 */
public class TileWallFactory extends MazeObjectFactory<TileWall> {

	/**
	 * Constructor
	 */
	public TileWallFactory() 
	{
		super(() -> { return new TileWall(); });
	}

}
