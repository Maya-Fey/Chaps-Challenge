package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * @author Claire
 */
public class InteractableChipFactory extends MazeObjectFactory<InteractableChip> {

	/**
	 * Constructor
	 */
	public InteractableChipFactory() 
	{
		super(() -> { return new InteractableChip(); });
	}

}
