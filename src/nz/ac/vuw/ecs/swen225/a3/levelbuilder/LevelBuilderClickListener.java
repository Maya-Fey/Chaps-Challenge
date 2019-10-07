package nz.ac.vuw.ecs.swen225.a3.levelbuilder;

/**
 * A listener for click events on the maze shown in the level editor
 * 
 * @author Claire
 */
public interface LevelBuilderClickListener {
	
	/**
	 * Called when a display tile is clicked. Coordinates in view coordinates,
	 * not world coordinates.
	 * 
	 * @param x
	 * @param y
	 */
	void onTileClick(int x, int y);

}
