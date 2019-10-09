package nz.ac.vuw.ecs.swen225.a3.levelbuilder;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;
import nz.ac.vuw.ecs.swen225.a3.commons.Visible;

/**
 * @author Claire
 *
 * A GUI class to build a level
 */
public class LevelBuilderFrame extends JFrame implements KeyListener, LevelBuilderClickListener {

	private static final long serialVersionUID = -6560472484901364076L;
	
	private final LevelBuilderDisplay disp = new LevelBuilderDisplay(this);
	private final LevelBuilderModel model = new LevelBuilderModel();
	
	private final JMenuBar menu = new JMenuBar();
	private final JMenu save = new JMenu("Save");
	private final JMenu load = new JMenu("Load");
	private final JMenu edit = new JMenu("Edit");
	
	private int x, y;
	
	private int sX, sY;
	
	/**
	 * Constructor
	 */
	public LevelBuilderFrame()
	{
		x = y = 0;
		sX = sY = -1;
		
		this.add(disp);
		
		this.setJMenuBar(menu);
		menu.add(save);
		menu.add(load);
		menu.add(edit);
		
		this.setTitle("Chap's Challenge Level Builder");
		this.setSize(7 * 125, 5 * 125);
		this.setVisible(true);
		this.addKeyListener(this);
		
		this.redisplay();
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
		sX = sY = -1;
		this.redisplay();
		
	}

	@Override
	public void onTileClick(int x, int y) 
	{
		sX = x;
		sY = y;
		this.redisplay();
	}
	
	private void redisplay()
	{
		disp.updatePosition(x, y);
		Visible[][] vis = model.render(x, y);
		
		if(sX != -1) {
			Icon icon = IconFactory.INSTANCE.composite(Arrays.asList(vis[sX][sY], new Visible() {
				public Icon getIcon() {
					return IconFactory.INSTANCE.loadIcon("selected.png");
				}
					
				public int zIndex() 
				{
					return -500;
				}
				
			}));
			
			vis[sX][sY] = new Visible() {
				public Icon getIcon() {
					return icon;
				}

				public int zIndex() {
					return 0;
				}
			
			};
		}
		disp.updateBoard(vis);
	}

}
