package nz.ac.vuw.ecs.swen225.a3.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import nz.ac.vuw.ecs.swen225.a3.commons.GameConstants;
import nz.ac.vuw.ecs.swen225.a3.commons.Visible;

/**
 * An implementation for the ChapsView interface which represents the view for
 * Chap's Challenge.
 *
 * @author Jakob 300444995
 *
 */
@SuppressWarnings("serial")
public class ChapsViewImpl extends JSplitPane implements ChapsView {

	/**
	 * Fields
	 */
	private final JPanel invPanel = new JPanel();
	
	private final JLabel levelLabel = new JLabel("0000");
	private final JLabel timeLabel = new JLabel("0000");
	private final JLabel chipsLabel = new JLabel("0000");
	
	private final JLabel tutorialMessage = new JLabel();
	private final JPanel tutorialPanel = new JPanel();
	
	private final JLabel text_1 = new JLabel("LEVEL:");
	private final JLabel text_2 = new JLabel("TIME:");
	private final JLabel text_3 = new JLabel("CHIPS:");
	
	private final JLabel[][] grid;
	
	private final JPanel left, right;
	
	/**
	 * Constructor to initialize GUI
	 *
	 */
	public ChapsViewImpl() 
	{
		super(JSplitPane.HORIZONTAL_SPLIT, new JPanel(), new JPanel());

		this.left = (JPanel) this.leftComponent;
		this.right = (JPanel) this.rightComponent;
		left.setLayout(new GridBagLayout());
		right.setLayout(new GridBagLayout());

		//Create a grid of labels to act as holders for the icons in the board
		grid = new JLabel[GameConstants.VISIBILE_SIZE][GameConstants.VISIBILE_SIZE];

		for(int x = 0; x < GameConstants.VISIBILE_SIZE; x++) {
			for(int y = 0; y < GameConstants.VISIBILE_SIZE; y++) {
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = x;
				gbc.gridy = y;
				grid[x][y] = new JLabel();
				left.add(grid[x][y], gbc);
			}
		}
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gbc.gridy = 0;
		gbc.weightx = gbc.weighty = 1;
		
		//Edit the color and size of the Level, time, and chips texts
		text_1.setForeground(Color.RED);
		text_1.setFont(text_1.getFont().deriveFont(Font.BOLD, 32));
		text_2.setForeground(Color.RED);
		text_2.setFont(text_2.getFont().deriveFont(Font.BOLD, 32));
		text_3.setForeground(Color.RED);
		text_3.setFont(text_3.getFont().deriveFont(Font.BOLD, 32));
		
		//Add all the text and info labels to the right panel
		//while also incrementing the y position down
		right.add(text_1, gbc);
		gbc.gridy++;
		right.add(levelLabel, gbc);
		gbc.gridy++;
		right.add(text_2, gbc);
		gbc.gridy++;
		right.add(timeLabel, gbc);
		gbc.gridy++;
		right.add(text_3, gbc);
		gbc.gridy++;
		right.add(chipsLabel, gbc);
		gbc.gridy++;
		right.add(invPanel, gbc);
		right.add(tutorialPanel, gbc);
		
		//Set the background to gray with a bevel border around
		right.setBackground(Color.lightGray);
		right.setBorder(BorderFactory.createRaisedBevelBorder());
		
		stylizeNumLabel(levelLabel);
		stylizeNumLabel(timeLabel);
		stylizeNumLabel(chipsLabel);
	}
	
	/**
	 * Function to convert a number string into
	 * 4 digits i.e. 0004
	 * @param n
	 * @return str a four digit string
	 */
	private String fourDigitFormat(int n) {
		String str = String.format("%04d", n);
		return str;
	}
	
	@Override
	public void updateBoard(Visible[][] board) 
	{
		//Iterate through every cell in the board and update its icon
		//which is retrieved by the an array of visible objects
		for(int x = 0; x < GameConstants.VISIBILE_SIZE; x++) 
			for(int y = 0; y < GameConstants.VISIBILE_SIZE; y++) 
				grid[x][y].setIcon(board[x][y].getIcon());
	}

	@Override
	public void updateCurrentLevel(int lvl) 
	{
		//convert from single digit to 0000 format
		String val = fourDigitFormat(lvl);
		levelLabel.setText(val);

	}

	@Override
	public void updateRemainingChips(int rem) 
	{
		//convert from single digit to 0000 format
		String val = fourDigitFormat(rem);
		chipsLabel.setText(val);
	}

	@Override
	public void updateRemainingTime(int rem) 
	{
		//convert from single digit to 0000 format
		String val = fourDigitFormat(rem / GameConstants.TICKS_TO_SECONDS_RATIO);
		timeLabel.setText(val);
	}

	@Override
	public void updateInventory(Collection<Visible> v) {

		invPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		//Creates the inventory display as a grid and sets
		//the icon based on the index in the list of Visible objects.
		for(int x = 0; x < 4; x++) {
			for(int y = 0; y < 2; y++) {
				for(Visible i : v) {
				gbc.gridx = x;
				gbc.gridy = y;
				gbc.weightx = 1;
				gbc.weighty = 1;
				JLabel label = new JLabel();
				label.setOpaque(true);

				label.setIcon(i.getIcon());
				label.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));

				invPanel.add(label, gbc);
				}
			}
		}
	}
	
	@Override
	public void setDisplayTutorialMessage(String message) {
		//Set the size to the inventory panel
		tutorialPanel.setPreferredSize(invPanel.getSize());
		tutorialMessage.setText(message);
		//makes the tutorial panel visible and the inventory panel invisible
		//so the tutorial text covers the inventory section
		tutorialPanel.add(tutorialMessage);
		invPanel.setVisible(false);
		tutorialPanel.setVisible(true);
	}

	@Override
	public void clearDisplayTutorialMessage() {
		//Removes the text of the message and hides
		//the tutorial panel and reveals the inventory panel
		tutorialMessage.setText(null);
		invPanel.setVisible(true);
		tutorialPanel.setVisible(false);

	}

	@Override
	public JSplitPane getRootPanel() {
		return this;
	}
	
	/**
	 * Stylize the number labels
	 * 
	 * @param label
	 */
	private void stylizeNumLabel(JLabel label)
	{
		//Set the text, fontsize, background & foreground colors, and border
		label.setOpaque(true);
		label.setBackground(Color.BLACK);
		label.setForeground(Color.GREEN);
		label.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.GRAY));
		label.setFont(timeLabel.getFont().deriveFont(Font.BOLD, 32));
	}
	
}
