package nz.ac.vuw.ecs.swen225.a3.application;

import java.awt.KeyEventDispatcher;
import java.awt.event.WindowListener;

/**
 * An interface that represents the controller for Chaps's challenge. This class
 * shoudld respond to user events, direct the low-level file operations to load levels 
 * and plugins, and tie the renderer, the maze, and the file operations together.
 * <br><br>
 * It should also create the root window.
 * 
 * @author Claire 300436297
 */
public interface ChapsController extends KeyEventDispatcher, WindowListener {
	
	/**
	 * Displays the main window of the game
	 */
	void start();

}
