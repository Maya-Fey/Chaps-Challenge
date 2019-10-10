package nz.ac.vuw.ecs.swen225.a3.plugin;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import nz.ac.vuw.ecs.swen225.a3.application.FactoryNotFoundException;
import nz.ac.vuw.ecs.swen225.a3.application.GameState;
import nz.ac.vuw.ecs.swen225.a3.application.GameStateFactory;
import nz.ac.vuw.ecs.swen225.a3.application.MultiFactory;
import nz.ac.vuw.ecs.swen225.a3.application.RootFactory;
import nz.ac.vuw.ecs.swen225.a3.commons.GameConstants;
import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;
import nz.ac.vuw.ecs.swen225.a3.commons.Persistable;
import nz.ac.vuw.ecs.swen225.a3.commons.Visible;
import nz.ac.vuw.ecs.swen225.a3.maze.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.Interactable;
import nz.ac.vuw.ecs.swen225.a3.maze.Item;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
import nz.ac.vuw.ecs.swen225.a3.persistence.JsonFileInterface;

/**
 * @author Claire
 *
 * A GUI class to build a level
 */
public class LevelBuilderFrame extends JFrame implements KeyListener, ActionListener, LevelBuilderClickListener {

	private static final long serialVersionUID = -6560472484901364076L;
	
	private final LevelBuilderDisplay disp = new LevelBuilderDisplay(this);
	private LevelBuilderModel model = new LevelBuilderModel();
	
	private Set<File> referencedJars = new HashSet<>();
	
	private final JMenuBar menu = new JMenuBar();
	private final JMenu save = new JMenu("Save");
	private final JMenuItem saveState = new JMenuItem("Save");
	private final JMenuItem export = new JMenuItem("Export");
	private final JMenu load = new JMenu("Load");
	private final JMenuItem loadState = new JMenuItem("Load");
	private final JMenuItem loadJar = new JMenuItem("Load External Code");
	private final JMenu edit = new JMenu("Edit");
	private final JMenuItem clear = new JMenuItem("Clear");
	private final JMenuItem setTime = new JMenuItem("Set Time Available");
	
	private boolean add = true;
	
	private int x, y;
	
	private int sX, sY;
	
	/**
	 * Constructor
	 */
	public LevelBuilderFrame()
	{		
		this.add(disp);
		
		this.setJMenuBar(menu);
		menu.add(save);
		save.add(saveState); saveState.addActionListener(this);
		save.add(export); export.addActionListener(this);
		menu.add(load);
		load.add(loadState); loadState.addActionListener(this);
		load.add(loadJar); loadJar.addActionListener(this);
		menu.add(edit);
		edit.add(clear); clear.addActionListener(this);
		edit.add(setTime); setTime.addActionListener(this);
		
		this.setTitle("Chap's Challenge Level Builder");
		this.setSize(7 * 125, 5 * 125);
		this.setVisible(true);
		this.addKeyListener(this);
		
		reinitialize(null);
		
		center(this);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent arg0) {}

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
		else if(arg0.getSource() == setTime) {
			String answer = JOptionPane.showInputDialog(this, "Set time in seconds");
			try {
				int i = Integer.parseInt(answer);
				if(i <= 0)
					throw new NumberFormatException();
				model.setTime(i);
				disp.updateRemainingTime(model.getTime());
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "You must enter a positive number of some kind.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if(arg0.getSource() == clear) {
			reinitialize(null);
		} else if(arg0.getSource() == export) {
			export();
		} else if(arg0.getSource() == saveState) {
			save();
		} else if(arg0.getSource() == loadState) {
			load();
		}
	}
	
	/**
	 * Reinitialize the builder to either a brand new level or a different level
	 * 
	 * @param state The state of the existing level, if there is one
	 */
	private void reinitialize(GameState state)
	{
		if(state == null)
			RootFactory.reinitialize();
		model = state == null ? new LevelBuilderModel() : new LevelBuilderModel(state);
		sX = sY = -1;
		x = y = 0;
		this.redisplay();
		disp.updateRemainingTime(model.getTime());
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
		
		if(!EditJsonDialog.canEdit(jObj))
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
				
				boolean added = false;
				
				for(ExternalChapsClass<Tile> ext : loader.tileClasses) {
					RootFactory.getInstance().tileFactory.addFactory(ext.getTypename(), ext.getFactory());
					all.append(ext.getTypename()); all.append('\n'); 
					added = true;
				}
				for(ExternalChapsClass<Actor> ext : loader.actorClasses) {
					RootFactory.getInstance().actorFactory.addFactory(ext.getTypename(), ext.getFactory());
					all.append(ext.getTypename()); all.append('\n');
					added = true;
				}
				for(ExternalChapsClass<Interactable> ext : loader.interactableClasses) {
					RootFactory.getInstance().interactableFactory.addFactory(ext.getTypename(), ext.getFactory());
					all.append(ext.getTypename()); all.append('\n');
					added = true;
				}
				for(ExternalChapsClass<Item> ext : loader.itemClasses) {
					RootFactory.getInstance().itemFactory.addFactory(ext.getTypename(), ext.getFactory());
					all.append(ext.getTypename()); all.append('\n');
					added = true;
				}
				
				if(added)
					referencedJars.add(selected);
				
				all.deleteCharAt(all.length() - 1);
				
				JOptionPane.showMessageDialog(this, all.toString());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "There was an error parsing the given jar-file: " + e.getClass().getSimpleName(), "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
	    }
	}
	
	/**
	 * Collates all required files for the designed level into a zip
	 */
	private void export()
	{
		JFileChooser chooser = new JFileChooser(new File("."));
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Chaps Plugin Files", "zip");
	    chooser.setFileFilter(filter);
	    chooser.setApproveButtonText("Save");
	    chooser.setDialogTitle("Save Level");
	    chooser.showOpenDialog(this);
	    
	    File selected = chooser.getSelectedFile();
	    if(selected != null)
	    {
	    	if(selected.exists() && selected.isDirectory()) 
	    		JOptionPane.showMessageDialog(this, "That's a Directory", "Error", JOptionPane.ERROR_MESSAGE);
	    	
	    	if(selected.exists() && selected.isFile() && !(JOptionPane.showConfirmDialog(this, "This file already exists. Are you sure you want to overwrite it?") == JOptionPane.OK_OPTION))
	    		return;
	    	
	    	if(selected.exists() && !selected.delete())
	    		JOptionPane.showMessageDialog(this, "File couldn't be overwritten", "Error", JOptionPane.ERROR_MESSAGE);
	    	
	    	try {
				selected.createNewFile();
				
				try(FileOutputStream fos = new FileOutputStream(selected)) {
					ZipOutputStream zos = new ZipOutputStream(fos);
					ZipEntry entry;
					
					GameState state = model.export();
					JsonObject obj = state.persist();
					
					entry = new ZipEntry("state.json");
					zos.putNextEntry(entry);
					
					JsonWriter writer = Json.createWriter(zos);
					writer.write(obj);
					
					zos.closeEntry();
					
					byte[] buf = new byte[1024];
					
					for(File jar : referencedJars)
					{
						entry = new ZipEntry(jar.getName());
						zos.putNextEntry(entry);
						
						FileInputStream stream = new FileInputStream(jar);
						
						int len;
						while((len = stream.read(buf, 0, 1024)) > 0)
							zos.write(buf, 0, len);
						
						stream.close();
						zos.closeEntry();
					}
					
					zos.close();
				}
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "There was an error in saving: " + e.getClass().getSimpleName(), "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} 
	    }
	}
	
	/**
	 * Saves the state for later loading
	 */
	private void save()
	{
		JFileChooser chooser = new JFileChooser(new File("."));
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Chaps Save Files", "json");
	    chooser.setFileFilter(filter);
	    chooser.setApproveButtonText("Save");
	    chooser.setDialogTitle("Save State for Later Editing");
	    chooser.showOpenDialog(this);
	    
	    File selected = chooser.getSelectedFile();
	    if(selected != null)
	    {
	    	if(selected.exists() && selected.isDirectory()) 
	    		JOptionPane.showMessageDialog(this, "That's a Directory", "Error", JOptionPane.ERROR_MESSAGE);
	    	
	    	if(selected.exists() && selected.isFile() && !(JOptionPane.showConfirmDialog(this, "This file already exists. Are you sure you want to overwrite it?") == JOptionPane.OK_OPTION))
	    		return;
	    	
	    	if(selected.exists() && !selected.delete())
	    		JOptionPane.showMessageDialog(this, "File couldn't be overwritten", "Error", JOptionPane.ERROR_MESSAGE);
	    	
	    	try {
				selected.createNewFile();
				
				JsonFileInterface.saveToFile(model.export().persist(), selected);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "There was an error in saving: " + e.getClass().getSimpleName(), "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} 
	    }
	}
	
	/**
	 * Loads state from file
	 */
	private void load()
	{
		if(!(JOptionPane.showConfirmDialog(this, "Loading a state will overwrite whatever you currently have. Are you sure?") == JOptionPane.OK_OPTION))
    		return;
		
		JFileChooser chooser = new JFileChooser(new File("."));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Chaps Save Files", "json");
	    chooser.setFileFilter(filter);
	    chooser.setApproveButtonText("Load");
	    chooser.setDialogTitle("Load Level for Further Editing");
	    chooser.showOpenDialog(this);
	    
	    File selected = chooser.getSelectedFile();
	    if(selected != null)
	    {
	    	try {
	    		JsonObject obj = JsonFileInterface.loadFromFile(selected);
	    		GameStateFactory factory = new GameStateFactory();
	    		GameState state = factory.resurrect(obj);
	    		
	    		this.reinitialize(state);
	    		
	    	} catch(FactoryNotFoundException e) {
	    		JOptionPane.showMessageDialog(this, "There was an error in loading your file. It's likely that you haven't imported all the external code that this level requires.", "Error", JOptionPane.ERROR_MESSAGE);
	    	} catch(Exception e) {
	    		JOptionPane.showMessageDialog(this, "There was an error in loading: " + e.getClass().getSimpleName(), "Error", JOptionPane.ERROR_MESSAGE);
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
