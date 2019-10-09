package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.JsonObject;
import javax.swing.Icon;

/**
 * Class to represent the exit tile
 * the level is finished when the player steps on it
 * @author James
 *
 */
public class Exit implements Tile{
	private Position position;
	private String name;
	private Icon icon;
	
	

	/**
	 * Constructor for new Exit
	 * @param position
	 * @param name
	 * @param icon
	 */
	public Exit(Position position, String name, Icon icon) {
		super();
		this.position = position;
		this.name = name;
		this.icon = icon;
	}
	
	/**
	 * Clone Exit tile
	 */
	public Exit clone() {
		return new Exit(position.clone(), name, icon);
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
		return true;
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
