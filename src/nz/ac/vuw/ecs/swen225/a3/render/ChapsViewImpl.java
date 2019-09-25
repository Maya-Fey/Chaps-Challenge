package nz.ac.vuw.ecs.swen225.a3.render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.InterfaceAddress;
import java.time.Year;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
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
	private JLabel timeLabel = new JLabel();
	private JLabel chipsLabel = new JLabel();
	private JLabel tutorialMessage = new JLabel();
	
	private Visible visible;
	
	private Image border;
	
	private static int WIDTH = 9;
	private static int HEIGHT = 9;
	
	/**
	 * Constructor to initialize GUI
	 * 
	 */
	public ChapsViewImpl() {
		super();

		
		this.setSize(new Dimension(600,600));
		this.setLayout(new GridLayout());
		this.add(chipsLabel);
		this.add(timeLabel);
		this.add(tutorialMessage);
		this.add(mazePanel);
		this.setVisible(true);
		
	}
	
	

	@Override
	public void updateBoard(Visible[][] board) {
		mazePanel = new JPanel();
		mazePanel.setPreferredSize(new Dimension(700, 700));
		mazePanel.setLayout(new GridLayout(WIDTH + 1, HEIGHT, 1, 1));
		mazePanel.setBackground(Color.BLACK);
		for(int x = 0; x < HEIGHT; x++) {
			for(int y = 0; y < WIDTH; y++) {
				mazePanel.add(IconToLabel(board[x][y].getIcon())); 
			}
		}
		
		this.revalidate();
		this.repaint();
		
	}
	
	/**
	 * @param icon
	 * @return iconLabel
	 */
	public JLabel IconToLabel(Icon icon) {
		JLabel iconLabel = new JLabel();
		iconLabel.setIcon(icon);
		return iconLabel;
		
	}

	@Override
	public void updateRemainingChips(int rem) {
		chipsLabel.setText("Chips remaining: " + rem);
		
	}

	@Override
	public void updateRemainingTime(int rem) {
		timeLabel.setText("Time remaining: " + rem);
		
		
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
