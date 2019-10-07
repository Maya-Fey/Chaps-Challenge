package nz.ac.vuw.ecs.swen225.a3.levelbuilder;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

/**
 * @author Claire
 *
 * A GUI class to build a level
 */
public class LevelBuilderFrame extends JFrame implements KeyListener, LevelBuilderClickListener {

	private static final long serialVersionUID = -6560472484901364076L;
	
	private final LevelBuilderDisplay disp = new LevelBuilderDisplay(this);
	private final LevelBuilderModel model = new LevelBuilderModel();
	
	private int x, y;
	
	/**
	 * Constructor
	 */
	public LevelBuilderFrame()
	{
		x = y = 0;
		
		this.add(disp);
		
		this.setTitle("Chap's Challenge Level Builder");
		this.setSize(700, 500);
		this.setVisible(true);
		this.addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent arg0) 
	{
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) 
	{
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) 
	{
		char lc = Character.toLowerCase(arg0.getKeyChar());
		switch(lc)
		{
			case 'w':
				y++;
				break;
			case 'a':
				x--;
				break;
			case 's':
				y--;
				break;
			case 'd':
				x++;
				break;
		}
		this.redisplay();
		
	}

	@Override
	public void onTileClick(int x, int y) {
		// TODO Auto-generated method stub
		
	}
	
	private void redisplay()
	{
		disp.updatePosition(x, y);
		disp.updateBoard(model.render(x, y));
	}

}
