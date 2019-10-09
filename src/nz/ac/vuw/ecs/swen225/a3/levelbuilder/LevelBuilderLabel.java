package nz.ac.vuw.ecs.swen225.a3.levelbuilder;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

/**
 * A label for a square on the level builder. Used for callbacks
 * 
 * @author Claire
 */
public class LevelBuilderLabel extends JLabel implements MouseListener {
	
	private static final long serialVersionUID = 6801347398948769718L;
	
	private final int x, y;
	
	private final LevelBuilderClickListener listener;
	
	/**
	 * @param listener
	 * @param x
	 * @param y
	 */
	public LevelBuilderLabel(LevelBuilderClickListener listener, int x, int y)
	{
		this.listener = listener;
		this.x = x;
		this.y = y;
		this.addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		listener.onTileClick(x, y);
	}
	
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

}
