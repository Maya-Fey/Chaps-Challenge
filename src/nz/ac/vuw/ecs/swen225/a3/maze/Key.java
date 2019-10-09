package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.JsonObject;
import javax.swing.Icon;

/**
 * Class to represent a key tile
 * players can move onto this tile and if so they collect the key
 * it is added to the inventory and converted to a free tile
 * @author James
 *
 */
public class Key implements Tile {
	private Position position;
	private String name;
	private Icon icon;
	private Item item;



	/**
	 * Constructor for a new key object
	 * @param position
	 * @param name
	 * @param icon
	 * @param item
	 */
	public Key(Position position, String name, Icon icon, Item item) {
		super();
		this.position = position;
		this.name = name;
		this.icon = icon;
		this.item = item;
	}

	public Key clone() {
		return new Key(position.clone(), name, icon, item.clone());
	}

	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public JsonObject persist() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Icon getIcon() {
		return icon;
	}

	@Override
	public boolean isFloor() {
		return true;
	}

	/**
	 * Returns the item stored inside of tile
	 * @return item
	 */
	public Item getItem() {
		return item;
	}

	@Override
	public boolean isSafe(Actor actor) {
		return true;
	}

	@Override
	public void onEnter(Interactable interactable, ModelAccessObject obj) {
		// TODO when stepped on add key to players inventory and convert to free tile

	}

	@Override
	public void onEnter(Actor actor, ModelAccessObject obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public FreeTile convertToFreeTile() {
		return new FreeTile(this.position, icon);
	}
}
