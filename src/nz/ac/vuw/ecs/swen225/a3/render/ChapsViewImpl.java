package nz.ac.vuw.ecs.swen225.a3.render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.BoxLayout;
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
	private final JLabel levelLabel = new JLabel();
	private final JPanel levelPanel = new JPanel();
	private final JLabel timeLabel = new JLabel();
	private final JPanel timePanel = new JPanel();
	private final JLabel chipsLabel = new JLabel();
	private final JPanel chipsPanel = new JPanel();
	private final JLabel tutorialMessage = new JLabel();

	private final JLabel[][] grid;


	private final JPanel left, right;

	private Image border;
	private String filename = "resources/digital-7.ttf";
	private Font customFont;;


	/**
	 * Constructor to initialize GUI
	 *
	 */
	public ChapsViewImpl() {
		super(JSplitPane.HORIZONTAL_SPLIT, new JPanel(), new JPanel());
		setupFont();
		updateRemainingChips(5);
		updateCurrentLevel(2);

		this.left = (JPanel) this.leftComponent;
		this.right = (JPanel) this.rightComponent;
		left.setLayout(new GridBagLayout());
		right.setLayout(new GridBagLayout());

		grid = new JLabel[GameConstants.VISIBILE_SIZE][GameConstants.VISIBILE_SIZE];

		for(int x = 0; x < GameConstants.VISIBILE_SIZE; x++) {
			for(int y = 0; y < GameConstants.VISIBILE_SIZE; y++) {
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = x;
				gbc.gridy = y;
				gbc.weightx = 1;
				gbc.weighty = 1;
				grid[x][y] = new JLabel();
				left.add(grid[x][y], gbc);
			}
		}
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gbc.gridy = 0;
		gbc.weightx = gbc.weighty = 1;
		right.add(createLevelPanel(), gbc);
		gbc.gridy++;
		right.add(createTimePanel(), gbc);
		gbc.gridy++;
		right.add(createChipsPanel(), gbc);

	}



	/**
	 * Sets up the digital font used for the info panel
	 *
	 */
	public void setupFont() {

		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File(filename));
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		font = font.deriveFont(Font.BOLD, 28);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);

		this.customFont = font;

	}



	@Override
	public void updateBoard(Visible[][] board) {
		for(int x = 0; x < GameConstants.VISIBILE_SIZE; x++) {
			for(int y = 0; y < GameConstants.VISIBILE_SIZE; y++) {
				grid[x][y].setIcon(board[x][y].getIcon());
			}
		}

	}


	/**
	 * Makes a simple panel of the Time display to
	 * go on the info panel
	 * @return timePanel
	 */
	public JPanel createTimePanel() {
		timePanel.setLayout(new GridLayout(2, 1));
		timePanel.setPreferredSize(new Dimension(100, 50));
		JLabel text = new JLabel();
		text.setForeground(Color.RED);
		text.setText("TIME:");
		timePanel.add(text);
		timePanel.add(timeLabel);
		timePanel.setBackground(Color.BLACK);

		return timePanel;
	}

	/**
	 * Makes a simple panel of the Chips display to
	 * go on the info panel
	 * @return chipsPanel
	 */
	public JPanel createChipsPanel() {
		chipsPanel.setLayout(new GridLayout(2,1));
		chipsPanel.setPreferredSize(new Dimension(50, 50));
		JLabel text = new JLabel();
		text.setForeground(Color.RED);
		text.setText("CHIPS:");
		chipsPanel.add(text);
		chipsPanel.add(chipsLabel);
		chipsPanel.setBackground(Color.BLACK);

		return chipsPanel;
	}

	/**
	 * Makes a simple panel of the current level
	 * to go on the info panel
	 * @return levelPanel
	 *
	 */
	public JPanel createLevelPanel() {
		levelPanel.setLayout(new GridLayout(2,1));
		levelPanel.setPreferredSize(new Dimension(100, 50));
		JLabel text = new JLabel("LEVEL:");
		text.setForeground(Color.RED);
		text.setBackground(Color.GRAY);
		levelPanel.add(text);
		levelPanel.add(levelLabel);
		levelPanel.setBackground(Color.BLACK);

		return levelPanel;
	}

	@Override
	public void updateCurrentLevel(int lvl) {
		levelLabel.setText("2"+lvl);
		levelLabel.setForeground(Color.GREEN);
		levelLabel.setFont(customFont);
	}

	@Override
	public void updateRemainingChips(int rem) {
		chipsLabel.setText("7"+rem);
		chipsLabel.setBackground(Color.GREEN);
		chipsLabel.setFont(customFont);
	}

	@Override
	public void updateRemainingTime(int rem) {
		timeLabel.setText("" + rem);
		timeLabel.setBackground(Color.gray);
		timeLabel.setForeground(Color.GREEN);
		timeLabel.setFont(customFont);

	}

	@Override
	public void updateInventory(List<Visible> v) {
		invPanel.setLayout(new GridLayout(4, 2));
		for(int x = 0; x < 2; x++) {
			for(int y = 0; y < 4; y++) {

			}
		}
		invPanel.setBackground(Color.BLACK);

	}



	@Override
	public void setDisplayTutorialMessage(String message) {
		tutorialMessage.setText(message);

	}

	@Override
	public void clearDisplayTutorialMessage() {
		tutorialMessage.setText(null);

	}

	@Override
	public JSplitPane getRootPanel() {
		return this;
	}








}
