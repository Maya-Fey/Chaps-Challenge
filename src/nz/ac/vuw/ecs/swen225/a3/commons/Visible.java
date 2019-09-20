package nz.ac.vuw.ecs.swen225.a3.commons;

import javax.swing.Icon;

/**
 * An interface representing any item that can be displayed in the GUI. This includes
 * items, tiles, interactables, and actors
 * 
 * @author Claire
 */
public interface Visible {
	
	/** 
	 * @return The icon that can be used to display this visible object
	 */
	Icon getIcon();
	
	/**
	 * Gets the z-index for this object. Higher numbers mean this object is lower (farther
	 * from the camera), and lower numbers means it's higher (closer to the camera)
	 * <br><br>
	 * Lower numbers are always rendered on top. Z=0 means it's an actor, Z=10 means it's a tile.
	 * 
	 * @return A number representing this object's z-index.
	 */
	int zIndex();

}
