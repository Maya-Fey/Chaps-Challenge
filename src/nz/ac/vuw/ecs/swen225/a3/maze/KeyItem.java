package nz.ac.vuw.ecs.swen225.a3.maze;

import java.awt.Color;

import javax.json.JsonObject;
import javax.swing.Icon;

/**
 * Class to represent a key item
 * @author James
 *
 */
public class KeyItem implements Item, Cloneable{

	private Icon icon;
	private Color keyColour;





	/**
	 * Constructor for new key item
	 * @param icon represents key
	 * @param keyColour colour used for unlocking
	 */
	public KeyItem(Icon icon, Color keyColour) {
		super();
		this.icon = icon;
		this.keyColour = keyColour;
	}

	public Item clone() {
		return new KeyItem(icon, keyColour);

	}

	/**
	 * gets key colour for this key
	 * @return key colour
	 */
	public Color getKeyColour() {
		return keyColour;
	}

	@Override
	public JsonObject persist() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int zIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

}
