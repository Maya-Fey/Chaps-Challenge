package nz.ac.vuw.ecs.swen225.a3.commons;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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
	 * A comparator that orders visible objects by Z-Index
	 */
	public static final Comparator<Visible> orderByZIndex = new Comparator<Visible>()
	{

		@Override
		public int compare(Visible o1, Visible o2) {
			return o2.zIndex() - o1.zIndex();
		}
		
	};
	
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
	 * @param file The file where the image is stored. The image should be EXACTLY GameConstants.ICON_SIZE in both directions
	 */
	public void loadIcon(String name, File file)
	{
		try {
			Image img = ImageIO.read(file);
			
			Contracts.arbitrary((img.getHeight(null) == img.getWidth(null)) && (img.getWidth(null) == GameConstants.ICON_SIZE), "Icons should be exactly " + GameConstants.ICON_SIZE + "x" + GameConstants.ICON_SIZE);
			
			ImageIcon icon = new ImageIcon(img);
			icons.put(name, icon);
		} catch (IOException e) {
			throw new UncheckedIOException("IO Exception occured while loading icon from file " + file.getAbsolutePath(), e);
		}
	}
	
	/**
	 * 
	 * @param visibles A list of visibles, to be rendered in order of z-index.
	 * 
	 * @return An icon that represents 
	 */
	public Icon composite(List<Visible> visibles)
	{
		visibles.sort(orderByZIndex);
		BufferedImage img = new BufferedImage(GameConstants.ICON_SIZE, GameConstants.ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = img.createGraphics();
		for(Visible visible : visibles)
			visible.getIcon().paintIcon(null, graphics, 0, 0);
		return new ImageIcon(img);
	}
	
}
