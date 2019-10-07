package nz.ac.vuw.ecs.swen225.a3.levelbuilder;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import nz.ac.vuw.ecs.swen225.a3.commons.GameConstants;
import nz.ac.vuw.ecs.swen225.a3.commons.Visible;
import nz.ac.vuw.ecs.swen225.a3.render.ChapsView;

/**
 * A pane showing all the necessary display for the level builder
 * 
 * @author Claire
 */
public class LevelBuilderDisplay extends JSplitPane implements ChapsView, MouseListener {
	
	private static final long serialVersionUID = -1231560855846207109L;
	
	private final JPanel left, right;
	
	private final JLabel[][] grid;

	/**
	 * Constructor
	 */
	public LevelBuilderDisplay()
	{
		super(JSplitPane.HORIZONTAL_SPLIT, new JPanel(), new JPanel());
		this.left = (JPanel) this.leftComponent;
		this.right = (JPanel) this.rightComponent;
		left.setLayout(new GridBagLayout());
		right.setLayout(new GridBagLayout());
		
		grid = new JLabel[GameConstants.VISIBILE_SIZE][GameConstants.VISIBILE_SIZE];
		for(int i = 0; i < GameConstants.VISIBILE_SIZE; i++)
		{
			for(int j = 0; j < GameConstants.VISIBILE_SIZE; j++)
			{
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = i;
				gbc.gridy = j;
				gbc.weightx = 1;
				gbc.weighty = 1;
				left.add(grid[i][j] = new JLabel(), gbc);
			}
		}
		
		
	}
	
	/**
	 * Stop stupid auto-resizing of the divider.
	 */
	@SuppressWarnings("deprecation")
	public void reshape(int x, int y, int w, int h)
	{
		super.reshape(x, y, w, h);
		this.setDividerLocation(5.0 / 7.0);
	}

	@Override
	public void updateBoard(Visible[][] board) 
	{
		for(int x = 0; x < GameConstants.VISIBILE_SIZE; x++)
			for(int y = 0; y < GameConstants.VISIBILE_SIZE; y++)
				grid[x][y].setIcon(board[x][y].getIcon());
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
		return null;
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
