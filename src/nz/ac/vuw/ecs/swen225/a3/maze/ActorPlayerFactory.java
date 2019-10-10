package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * @author Claire
 */
public class ActorPlayerFactory extends MazeObjectFactory<ActorPlayer> {

	/**
	 * 
	 */
	public ActorPlayerFactory() 
	{
		super(() -> { return new ActorPlayer(); });
	}

}
