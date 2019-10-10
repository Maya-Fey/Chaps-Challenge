package nz.ac.vuw.ecs.swen225.a3.levelbuilder;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Arrays;

import javax.json.JsonObject;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import nz.ac.vuw.ecs.swen225.a3.application.MultiFactory;
import nz.ac.vuw.ecs.swen225.a3.application.RootFactory;
import nz.ac.vuw.ecs.swen225.a3.commons.GameConstants;
import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;
import nz.ac.vuw.ecs.swen225.a3.commons.Persistable;
import nz.ac.vuw.ecs.swen225.a3.commons.Visible;
import nz.ac.vuw.ecs.swen225.a3.maze.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.Interactable;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;

/**
 * @author Claire
 *
 * A GUI class to build a level
 */
public class LevelBuilderFrame extends JFrame implements KeyListener, ActionListener, LevelBuilderClickListener {

	private static final long serialVersionUID = -6560472484901364076L;
	
	private final LevelBuilderDisplay disp = new LevelBuilderDisplay(this);
	private final LevelBuilderModel model = new LevelBuilderModel();
	
	private final JMenuBar menu = new JMenuBar();
	private final JMenu save = new JMenu("Save");
	private final JMenuItem loadJar = new JMenuItem("Load External Code");
	private final JMenu load = new JMenu("Load");
	private final JMenu edit = new JMenu("Edit");
	
	private boolean add = true;
	
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
		load.add(loadJar); loadJar.addActionListener(this);
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
		if(arg0.isShiftDown())
		{
			char lc = Character.toLowerCase(arg0.getKeyChar());
			String use;
			switch(lc)
			{
				case 't':
					use = "tile";
					break;
				case 'a':
					use = "actor";
					break;
				case 'i':
					use = "interactable";
					break;
				default:
					return;
			}
			
			if(add)
				setMazeObject(use);
			else;
			
			return;
		}
		
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
		disp.updateRemainingChips(model.getChips());
		disp.updateBoard(vis);
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if(arg0.getSource() == loadJar)
			loadJar();
	}
	
	@SuppressWarnings("unchecked")
	private void setMazeObject(String typ)
	{
		if(sX == -1 || sY == -1)
			return;
		
		MultiFactory<?> factory;
		switch(typ)
		{
			case "actor":
				factory = RootFactory.getInstance().actorFactory;
				break;
			case "interactable":
				factory = RootFactory.getInstance().interactableFactory;
				break;
			case "tile":
				factory = RootFactory.getInstance().tileFactory;
				break;
			default:
				return;	
		}
		
		AddObjectDialog dialog = new AddObjectDialog(this, factory, typ);
		dialog.setVisible(true);
		
		if(dialog.hasChoice() && dialog.getChoice() != null && !dialog.getChoice().equals(""))
		{
			//TODO: Possibly edit beforehand?
			int x = translateX();
			int y = translateY();
			
			switch(typ)
			{
				case "actor":
					MultiFactory<Actor> af = (MultiFactory<Actor>) factory;
					Actor actor = af.newInstance(dialog.getChoice());
					if(dialog.wantsToEdit())
						actor = doEdit(actor, af);
					model.addActor(actor, x, y);
					break;
				case "interactable":
					MultiFactory<Interactable> inf = (MultiFactory<Interactable>) factory;
					Interactable interactable = inf.newInstance(dialog.getChoice());
					if(dialog.wantsToEdit())
						interactable = doEdit(interactable, inf);
					model.addInteractable(interactable, x, y);
					break;
				case "tile":
					MultiFactory<Tile> tf = (MultiFactory<Tile>) factory;
					Tile tile = tf.newInstance(dialog.getChoice());
					if(dialog.wantsToEdit())
						tile = doEdit(tile, tf);
					model.addTile(tile, x, y);
					break;
				default:
					return;	
			}
			
			this.redisplay();
		}
	}
	
	/**
	 * Performs an edit on an object using the edit dialog
	 * 
	 * @param <T> The interface type we're editing
	 * @param obj The object
	 * @param factory The factory for the interface
	 * @return
	 */
	private <T extends Persistable> T doEdit(T obj, MultiFactory<T> factory)
	{
		JsonObject jObj = obj.persist();
		
		if(EditJsonDialog.canEdit(jObj))
			return obj;
		
		EditJsonDialog dialog = new EditJsonDialog(this, jObj, true);
		dialog.setVisible(true);
		
		if(dialog.hasEdit()) {
			return factory.resurrect(dialog.edited());
		} else {
			return obj;
		}
	}
	
	private void loadJar()
	{
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Java Jar Files", "jar");
	    chooser.setFileFilter(filter);
	    chooser.setApproveButtonText("Open");
	    chooser.setDialogTitle("Open External Code");
	    chooser.showOpenDialog(this);
	    
	    File selected = chooser.getSelectedFile();
	    if(selected != null)
	    {
	    	try {
				ExternalCodeLoader loader = new ExternalCodeLoader(selected);
				
				StringBuilder all = new StringBuilder("Added:\n");
				
				for(ExternalChapsClass<Tile> ext : loader.tileClasses) {
					RootFactory.getInstance().tileFactory.addFactory(ext.getTypename(), ext.getFactory());
					all.append(ext.getTypename()); all.append('\n');
				}
				for(ExternalChapsClass<Actor> ext : loader.actorClasses) {
					RootFactory.getInstance().actorFactory.addFactory(ext.getTypename(), ext.getFactory());
					all.append(ext.getTypename()); all.append('\n');
				}
				for(ExternalChapsClass<Interactable> ext : loader.interactableClasses) {
					RootFactory.getInstance().interactableFactory.addFactory(ext.getTypename(), ext.getFactory());
					all.append(ext.getTypename()); all.append('\n');
				}
				
				all.deleteCharAt(all.length() - 1);
				
				JOptionPane.showMessageDialog(this, all.toString());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "There was an error parsing the given jar-file: " + e.getClass().getSimpleName(), "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
	    }
	}
	
	/**
	 * @return The true x coordinate of the selected position
	 */
	private int translateX()
	{
		return sX + x - GameConstants.VISIBILE_SIZE / 2;
	}
	
	/**
	 * @return The true y coordinate of the selected position
	 */
	private int translateY()
	{
		return (8 - sY) + y - GameConstants.VISIBILE_SIZE / 2;
	}
	
	/**
	 * Centers a window on the screen
	 * 
	 * @param window The window to center
	 */
	public static void center(final Window window)
	{
		final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		final int X = (int) ((screen.getWidth() - window.getWidth()) / 2);
		final int Y = (int) ((screen.getHeight() - window.getHeight()) / 2);
		window.setLocation(X, Y);
	}	

}
