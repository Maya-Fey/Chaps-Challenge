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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

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
	private final JLabel text_1 = new JLabel("LEVEL:");
	private final JLabel text_2 = new JLabel("TIME:");
	private final JLabel text_3 = new JLabel("CHIPS:");
	private final Border border = BorderFactory.createEmptyBorder(10, 10, 20, 10);


	private final JLabel[][] grid;


	private final JPanel left, right;


	private String filename = "resources/digital-7.ttf";
	private Font customFont;;

//	/**
//	 * Anonymous method to give a value for v
//	 */
//	Visible v = new Visible() {
//
//		@Override
//		public Icon getIcon() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public int zIndex() {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//	};



	/**
	 * Constructor to initialize GUI
	 *
	 */
	public ChapsViewImpl() {

		super(JSplitPane.HORIZONTAL_SPLIT, new JPanel(), new JPanel());
		setupFont();
		//used for testing rendering
//		updateRemainingChips(5);
//		updateCurrentLevel(2);
//		updateRemainingTime(8);
//		updateInventory();

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
		text_1.setForeground(Color.RED);
		text_1.setFont(text_1.getFont().deriveFont(Font.BOLD, 32));
		text_2.setForeground(Color.RED);
		text_2.setFont(text_2.getFont().deriveFont(Font.BOLD, 32));
		text_3.setForeground(Color.RED);
		text_3.setFont(text_3.getFont().deriveFont(Font.BOLD, 32));
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
	}






	/**
	 * Sets up the digital font used for the info panel
	 //TODO currently not used as it messes wirth the formtting
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
		font = font.deriveFont(Font.BOLD, 42);

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);

		this.customFont = font;

	}

	/**
	 * Function to convert a number string into
	 * 4 digits i.e. 0004
	 * @param n
	 * @return str a four digit string
	 */
	public String fourDigitFormat(int n) {
		String str = String.format("%04d", n);
		return str;
	}



	@Override
	public void updateBoard(Visible[][] board) {
		for(int x = 0; x < GameConstants.VISIBILE_SIZE; x++) {
			for(int y = 0; y < GameConstants.VISIBILE_SIZE; y++) {
				grid[x][y].setIcon(board[x][y].getIcon());
			}
		}

	}




	@Override
	public void updateCurrentLevel(int lvl) {
		levelLabel.setOpaque(true);
		String val = fourDigitFormat(lvl);
		levelLabel.setText(val);
		levelLabel.setBackground(Color.BLACK);
		levelLabel.setForeground(Color.GREEN);
		levelLabel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.GRAY));
		//levelLabel.setBorder(border);
		levelLabel.setFont(chipsLabel.getFont().deriveFont(Font.BOLD, 32));

	}

	@Override
	public void updateRemainingChips(int rem) {
		chipsLabel.setOpaque(true);
		String val = fourDigitFormat(rem);
		chipsLabel.setText(val);
		chipsLabel.setBackground(Color.BLACK);
		chipsLabel.setForeground(Color.GREEN);
		chipsLabel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.GRAY));
		chipsLabel.setFont(chipsLabel.getFont().deriveFont(Font.BOLD, 32));
	}

	@Override
	public void updateRemainingTime(int rem) {
		timeLabel.setOpaque(true);
		String val = fourDigitFormat(rem);
		timeLabel.setText(val);
		timeLabel.setBackground(Color.BLACK);
		timeLabel.setForeground(Color.GREEN);
		timeLabel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.GRAY));
		timeLabel.setFont(chipsLabel.getFont().deriveFont(Font.BOLD, 32));

	}

	@Override
	public void updateInventory(List<Visible> v) {
		invPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		for(int x = 0; x < 4; x++) {
			for(int y = 0; y < 2; y++) {
				gbc.gridx = x;
				gbc.gridy = y;
				gbc.weightx = 1;
				gbc.weighty = 1;
				JLabel label = new JLabel();
				label.setOpaque(true);
				Icon i = new ImageIcon("resources/wallTile.png");
				label.setIcon(i);
				label.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));

				invPanel.add(label, gbc);
			}
		}


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
