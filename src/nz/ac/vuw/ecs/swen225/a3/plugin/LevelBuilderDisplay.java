package nz.ac.vuw.ecs.swen225.a3.plugin;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Collection;

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
public class LevelBuilderDisplay extends JSplitPane implements ChapsView {
	
	private static final long serialVersionUID = -1231560855846207109L;
	
	private final JPanel left, right;
	
	private final JLabel[][] grid;
	
	private final JLabel time = new JLabel("Time: 0");
	
	private final JLabel chips = new JLabel("Chips: 0");
	
	private final JLabel position = new JLabel("Looking at: (0, 0)");

	/**
	 * Constructor
	 * 
	 * @param listener 
	 */
	public LevelBuilderDisplay(LevelBuilderClickListener listener)
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
				left.add(grid[i][j] = new LevelBuilderLabel(listener, i, j), gbc);
			}
		}
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gbc.gridy = 0;
		gbc.weightx = gbc.weighty = 1;
		right.add(time, gbc);
		gbc.gridy++;
		right.add(chips, gbc);
		gbc.gridy++;
		right.add(position, gbc);
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
		chips.setText("Chips: " + rem);
	}

	@Override
	public void updateRemainingTime(int rem) 
	{
		time.setText("Time: " + rem / GameConstants.TICKS_TO_SECONDS_RATIO);
	}
	
	/**
	 * Updates the position on the display
	 * 
	 * @param x
	 * @param y
	 */
	public void updatePosition(int x, int y)
	{
		position.setText(String.format("(Looking at: %d, %d)", x, y));
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
	public JSplitPane getRootPanel() 
	{
		return this;
	}

	@Override
	public void updateCurrentLevel(int lvl) 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateInventory(Collection<Visible> v) 
	{
		throw new UnsupportedOperationException();	
	}

}
