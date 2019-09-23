package nz.ac.vuw.ecs.swen225.a3.render;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.a3.commons.Visible;

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
	private JLabel timeLabel = new JLabel();
	private JLabel chipsLabel = new JLabel();
	private JLabel tutorialMessage = new JLabel();
	
	
	/**
	 * Constructor to initialize GUI
	 */
	public ChapsViewImpl() {
		
		super();
		this.setSize(new Dimension(600,600));
		this.setLayout(new GridLayout());
		this.add(chipsLabel);
		this.add(timeLabel);
		this.add(tutorialMessage);
		this.setVisible(true);
		
		
	}

	

	@Override
	public void updateBoard(Visible[][] board) {
		this.revalidate();
		this.repaint();
		
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
