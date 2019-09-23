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
public class ChapsViewImpl extends JPanel implements ChapsView {
	
	/**
	 * Fields
	 */
	
	
	/**
	 * Constructor to initialize GUI
	 */
	public ChapsViewImpl() {
		
		super();
		this.setSize(new Dimension(600,600));
		this.setLayout(new GridLayout());
		this.setVisible(true);
		JLabel timeLabel = new JLabel("Time: 0");
		JLabel chipsLabel = new JLabel("Chips: 0");
		this.add(timeLabel);
		this.add(chipsLabel);
		
		
	}

	@Override
	public void updateBoard(Visible[][] board) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRemainingChips(int rem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRemainingTime(int rem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDisplayTutorialMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearDisplayTutorialMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JPanel getRootPanel() {
		return this;
	}
	


	
	
	
	
	
}
