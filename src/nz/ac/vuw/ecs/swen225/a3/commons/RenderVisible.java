package nz.ac.vuw.ecs.swen225.a3.commons;

import javax.swing.Icon;

/**
 * A visible wrapper for Icon
 * 
 * @author Claire
 */
public class RenderVisible implements Visible
{
	private final Icon icon;
	
	/**
	 * Constructs a visible from an icon
	 * 
	 * @param icon
	 */
	public RenderVisible(Icon icon)
	{
		this.icon = icon;
	}
	
	@Override
	public Icon getIcon() 
	{
		return icon;
	}

	@Override
	public int zIndex() 
	{
		return 0;
	}
	
}