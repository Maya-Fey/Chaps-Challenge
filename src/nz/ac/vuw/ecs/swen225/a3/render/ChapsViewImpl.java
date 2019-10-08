package nz.ac.vuw.ecs.swen225.a3.render;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.InterfaceAddress;
import java.time.Year;
import java.util.List;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.a3.commons.*;

/**
 * An implementation for the ChapsView interface which represents the view for
 * Chap's Challenge.
 *
 * @author Jakob 300444995
 *
 */
@SuppressWarnings("serial")
public class ChapsViewImpl extends JPanel implements ChapsView {

	/**
	 * Fields
	 */
	private JPanel mazePanel = new JPanel();
	private JPanel invPanel = new JPanel();
	private JLabel levelLabel = new JLabel();
	private JLabel timeLabel = new JLabel();
	private JLabel chipsLabel = new JLabel();
	private JLabel tutorialMessage = new JLabel();

	private Visible[][] visible;
	private final JLabel[][] grid;

	private Image border;
	private String filename = "resources/digital-7.ttf";
	private static int WIDTH = 9;
	private static int HEIGHT = 9;

	/**
	 * Constructor to initialize GUI
	 *
	 */
	public ChapsViewImpl() {
		super();

		grid = new JLabel[GameConstants.VISIBILE_SIZE][GameConstants.VISIBILE_SIZE];
		mazePanel.setPreferredSize(new Dimension(700,700));
		mazePanel.setLayout(new GridLayout(GameConstants.VISIBILE_SIZE, GameConstants.VISIBILE_SIZE));

		for(int x = 0; x < GameConstants.VISIBILE_SIZE; x++) {
			for(int y = 0; y < GameConstants.VISIBILE_SIZE; y++) {
				grid[x][y] = new JLabel();
				grid[x][y].setIcon(null);
				mazePanel.add(grid[x][y]);
			}
		}
		this.setPreferredSize(new Dimension(600,600));
		this.setLayout(new BorderLayout());
		this.add(mazePanel);
		this.add(createInfoPanel(), BorderLayout.EAST);
		this.setVisible(true);
	}

	/**
	 * Sets up the digital font used for the info panel
	 * @return font
	 */
	public Font setupFont() {

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
		font = font.deriveFont(Font.BOLD,28);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);

		return font;

	}



	@Override
	public void updateBoard(Visible[][] board) {
		for(int x = 0; x < GameConstants.VISIBILE_SIZE; x++) {
			for(int y = 0; y < GameConstants.VISIBILE_SIZE; y++) {
				grid[x][y].setIcon(board[x][y].getIcon());
			}
		}

		this.revalidate();
		this.repaint();

	}



	/**
	 * Create the panel containing the remaining time,
	 * remaining chips and current inventory.
	 * @return infoPanel
	 */
	public JPanel createInfoPanel() {
		JPanel infoPanel = new JPanel();
		infoPanel.setMinimumSize(new Dimension(300, 600));
		infoPanel.setBackground(Color.GRAY);
		infoPanel.setLayout(new GridLayout(10, 50));
		JLabel text1 = new JLabel("LEVEL");
		text1.setForeground(Color.RED);
		text1.setFont(setupFont());
		infoPanel.add(text1);

		JLabel text2 = new JLabel("TIME:");
		text2.setForeground(Color.RED);
		text2.setFont(setupFont());
		infoPanel.add(text2);

		infoPanel.add(timeLabel);

		JLabel text3 = new JLabel("CHIPS:");
		text3.setForeground(Color.RED);
		text3.setFont(setupFont());
		infoPanel.add(text3);

		infoPanel.add(chipsLabel);

		infoPanel.add(invPanel);
		infoPanel.setVisible(true);
		return infoPanel;

	}

	@Override
	public void updateCurrentLevel(int lvl) {
		levelLabel.setText(""+lvl);
		levelLabel.setFont(setupFont());
	}

	@Override
	public void updateRemainingChips(int rem) {
		chipsLabel.setText(""+rem);
		chipsLabel.setFont(setupFont());
	}

	@Override
	public void updateRemainingTime(int rem) {

		timeLabel.setText("" + rem);
		timeLabel.setFont(setupFont());
		timeLabel.setForeground(Color.GREEN);

	}

	@Override
	public void updateInventory(List<Visible> v) {
		invPanel.setLayout(new GridLayout(4, 2));
		invPanel.setBackground(Color.BLACK);


		invPanel.setVisible(true);

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
	public JPanel getRootPanel() {
		return this;
	}








}
