package nz.ac.vuw.ecs.swen225.a3.levelbuilder;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

/**
 * @author Claire
 *
 * A GUI class to build a level
 */
public class LevelBuilderFrame extends JFrame implements KeyListener {

	private static final long serialVersionUID = -6560472484901364076L;
	
	private final LevelBuilderDisplay disp = new LevelBuilderDisplay();
	
	/**
	 * Constructor
	 */
	public LevelBuilderFrame()
	{
		this.add(disp);
		
		this.setTitle("Chap's Challenge Level Builder");
		this.setSize(700, 500);
		this.setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) 
	{
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}

}
