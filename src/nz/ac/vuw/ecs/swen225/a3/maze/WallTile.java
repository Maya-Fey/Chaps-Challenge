package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.JsonObject;
import javax.swing.Icon;

/**
 * Class to represent a wall tile
 * actors can not move onto this tile
 * @author James
 *
 */
public class WallTile implements Tile{
	private Position position;
	private String name;
	private Icon icon;
	
	
	/**
	 * Constructor for new WallTile
	 * @param position
	 * @param name
	 * @param icon
	 */
	public WallTile(Position position, String name, Icon icon) {
		super();
		this.position = position;
		this.name = name;
		this.icon = icon;
	}
	
	/**
	 * Method to clone WallTile
	 */
	public WallTile clone() {
		return new WallTile(position.clone(), name, icon);
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
		return false;
	}

	@Override
	public boolean isSafe(Actor actor) {
		return false;
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
		return null;
	}
}
