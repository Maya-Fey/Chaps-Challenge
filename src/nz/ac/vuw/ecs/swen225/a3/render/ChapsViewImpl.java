package nz.ac.vuw.ecs.swen225.a3.render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import nz.ac.vuw.ecs.swen225.a3.commons.GameConstants;
import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;
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
	public ChapsViewImpl() {

		super(JSplitPane.HORIZONTAL_SPLIT, new JPanel(), new JPanel());


//Code for faking a list of visible objects to test updateInventory
//		Visible v = new Visible() {
//
//			@Override
//			public Icon getIcon() {
//				return IconFactory.INSTANCE.loadIcon("wallTile.png");
//			}
//
//			@Override
//			public int zIndex() {
//				// TODO Auto-generated method stub
//				return 0;
//			}
//
//		};
//		vList.add(v);
//		vList.add(v);
//		vList.add(v);
//		vList.add(v);
//		vList.add(v);
//		vList.add(v);
//		vList.add(v);
//		vList.add(v);

		this.left = (JPanel) this.leftComponent;
		this.right = (JPanel) this.rightComponent;
		left.setLayout(new GridBagLayout());
		right.setLayout(new GridBagLayout());

		//Create a grid of labels to act as holders for the icons in the board
		grid = new JLabel[GameConstants.VISIBILE_SIZE][GameConstants.VISIBILE_SIZE];

		for(int x = 0; x < GameConstants.VISIBILE_SIZE; x++) {
			for(int y = 0; y < GameConstants.VISIBILE_SIZE; y++) {
				GridBagConstraints gbc = new GridBagConstraints();
				//Increment through gridbagconstraints to know the location of each label
				gbc.gridx = x;
				gbc.gridy = y;
				gbc.weightx = 1;
				gbc.weighty = 1;
				//Initialise every label in the grid as an empty JLabel
				//and add them to the left display
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
	public void updateBoard(Visible[][] board) {
		//Iterate through every cell in the board and update its icon
		//which is retrieved by the an array of visible objects
		for(int x = 0; x < GameConstants.VISIBILE_SIZE; x++) {
			for(int y = 0; y < GameConstants.VISIBILE_SIZE; y++) {
				Icon i = board[x][y].getIcon();
				grid[x][y].setIcon(i);
			}
		}
	}




	@Override
	public void updateCurrentLevel(int lvl) {
		levelLabel.setOpaque(true);
		//convert from single digit to 0000 format
		String val = fourDigitFormat(lvl);
		//Set the text, fontsize, background & foreground colors, and border
		levelLabel.setText(val);
		levelLabel.setBackground(Color.BLACK);
		levelLabel.setForeground(Color.GREEN);
		levelLabel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.GRAY));
		//levelLabel.setBorder(border);
		levelLabel.setFont(levelLabel.getFont().deriveFont(Font.BOLD, 32));

	}

	@Override
	public void updateRemainingChips(int rem) {
		chipsLabel.setOpaque(true);
		//convert from single digit to 0000 format
		String val = fourDigitFormat(rem);
		//Set the text, fontsize, background & foreground colors, and border
		chipsLabel.setText(val);
		chipsLabel.setBackground(Color.BLACK);
		chipsLabel.setForeground(Color.GREEN);
		chipsLabel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.GRAY));
		chipsLabel.setFont(chipsLabel.getFont().deriveFont(Font.BOLD, 32));
	}

	@Override
	public void updateRemainingTime(int rem) {
		timeLabel.setOpaque(true);
		//convert from single digit to 0000 format
		String val = fourDigitFormat(rem);
		//Set the text, fontsize, background & foreground colors, and border
		timeLabel.setText(val);
		timeLabel.setBackground(Color.BLACK);
		timeLabel.setForeground(Color.GREEN);
		timeLabel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.GRAY));
		timeLabel.setFont(timeLabel.getFont().deriveFont(Font.BOLD, 32));

	}

	@Override
	public void updateInventory(Collection<Visible> v) {

		invPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		//Creates the inventory display as a grid and sets
		//the icon based on the index in the list of Visible objects.
		int count = 0;
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
				count++;
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








}
