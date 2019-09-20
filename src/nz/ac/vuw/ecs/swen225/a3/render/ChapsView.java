package nz.ac.vuw.ecs.swen225.a3.render;

import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.a3.commons.Visible;

/**
 * An interface that represents the view for Chap's Challenge. Receives updates
 * from the application package and uses those updates to render the board as 
 * well as display important information such as the time, level, inventory, etc.
 * 
 * @author Claire 300436297
 */
public interface ChapsView {
	
	/**
	 * Given the visible section of the board, updates the GUI.
	 * 
	 * @param board A 2D array of renderables representing the visible area of the board.
	 * The width and height of the array must be exactly Constants.VISIBLE_SIZE
	 */
	void updateBoard(Visible[][] board);
	
	/**
	 * Updates the display with the number of chips remaining
	 * 
	 * @param rem The number of remaining chips
	 */
	void updateRemainingChips(int rem);
	
	/**
	 * Updates the display with the number of chips remaining
	 * 
	 * @param rem The number of remaining time
	 */
	void updateRemainingTime(int rem);
	
	/**
	 * Sets a tutorial message to be displayed, occluding the inventory area.
	 * 
	 * @param message
	 */
	void setDisplayTutorialMessage(String message);
	
	/**
	 * Clears any tutorial message, if any is present
	 */
	void clearDisplayTutorialMessage();
	
	/**
	 * Return's a reference to the JPanel that contains all the necessary views 
	 * for the game. This JPanel isn't new but rather a reference to the one
	 * updated by this view.
	 * 
	 * @return This view's root JPanel
	 */
	JPanel getRootPanel();
	

}
