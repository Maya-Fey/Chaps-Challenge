package nz.ac.vuw.ecs.swen225.a3.commons;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * A factory to manufacture and save all the game icons.
 * 
 * @author Claire 300436297
 */
public class IconFactory {

	/**
	 * The instance of this factory
	 */
	public static final IconFactory INSTANCE = new IconFactory();
	
	/**
	 * A map of all the currently loaded icons by their filenames
	 */
	private final Map<String, Icon> icons = new HashMap<>();
	
	private IconFactory() {}
	
	/**
	 * Loads an icon by the specified name, and, if it hasn't been loaded already, 
	 * loads it from file.
	 * 
	 * @param name The name of the icon
	 * @return The icon
	 */
	public Icon loadIcon(String name)
	{
		if(!icons.containsKey(name))
			loadIcon(name, new File(GameConstants.DEFAULT_ICON_DIRECTORY + name));
		return icons.get(name);
	}
	
	/**
	 * Loads an icon at a specified location, but saves it as only <code>name</code>.
	 * <br><br>
	 * Should only be used by the plugin loader.
	 * 
	 * @param name the name of the file that will be referenced later
	 * @param file The file where the image is stored
	 */
	public void loadIcon(String name, File file)
	{
		try {
			Image img = ImageIO.read(file);
			ImageIcon icon = new ImageIcon(img);
			icons.put(name, icon);
		} catch (IOException e) {
			throw new UncheckedIOException("IO Exception occured while loading icon from file " + file.getAbsolutePath(), e);
		}
		
	}
	
}
