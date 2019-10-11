package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * @author Claire
 */
public class InteractableExitLockFactory extends MazeObjectFactory<InteractableExitLock> {
	
	/**
	 * Constructor
	 */
	public InteractableExitLockFactory()
	{
		super(() -> { return new InteractableExitLock(); });
	}

}
