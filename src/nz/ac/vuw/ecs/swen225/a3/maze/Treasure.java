package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.JsonObject;
import javax.swing.Icon;

/**
 * Class to represent a treasure tile
 * when stood on the tile turns back to a free tile
 * the treasure item is added to the players inventory
 * @author James
 *
 */
public class Treasure implements Tile, Cloneable{
	private Position position;
	private String name;
	private Icon icon;
	private Item item;

	public Treasure clone() {
		Treasure tClone = new Treasure(position.clone(), name, icon, item.clone());
		return null;

	}



	/**
	 * Default constructor for making a new treasure
	 * @param position
	 * @param name
	 * @param icon
	 * @param item
	 */
	public Treasure(Position position, String name, Icon icon, Item item) {
		super();
		this.position = position;
		this.name = name;
		this.icon = icon;
		this.item = item;
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

	@Override
	public boolean isSafe(Actor actor) {
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
	public void onEnter(Interactable interactable, ModelAccessObject obj) {
		// TODO Auto-generated method stub

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
