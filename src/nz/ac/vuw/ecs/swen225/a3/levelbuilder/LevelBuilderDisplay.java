package nz.ac.vuw.ecs.swen225.a3.levelbuilder;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.a3.commons.Visible;
import nz.ac.vuw.ecs.swen225.a3.render.ChapsView;

/**
 * A pane showing all the necessary display for the level builder
 * 
 * @author Claire
 */
public class LevelBuilderDisplay extends JPanel implements ChapsView, MouseListener {
	
	private static final long serialVersionUID = -1231560855846207109L;

	/**
	 * Constructor
	 */
	public LevelBuilderDisplay()
	{
		
	}

	@Override
	public void updateBoard(Visible[][] board) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRemainingChips(int rem) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRemainingTime(int rem) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDisplayTutorialMessage(String message) 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearDisplayTutorialMessage() 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public JPanel getRootPanel() 
	{
		return this;
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
	
	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent arg0) {}

}
