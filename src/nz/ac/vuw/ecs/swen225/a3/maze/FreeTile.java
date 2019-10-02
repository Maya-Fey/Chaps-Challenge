package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.JsonObject;
import javax.swing.Icon;

/**
 * Class to represent an empty or free tile
 * @author James
 *
 */
public class FreeTile implements Tile{
	private Position position;
	private Icon icon;

	/**
	 * Constructor to make a new free tile
	 * @param position
	 * @param icon
	 */
	public FreeTile(Position position, Icon icon) {
		super();
		this.position = position;
		this.icon = icon;
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
		return "FreeTile";
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

	@Override
	public void onEnter(Interactable interactable, ModelAccessObject obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnter(Actor actor, ModelAccessObject obj) {
		// TODO Auto-generated method stub
		//TODO

	}

	@Override
	public FreeTile convertToFreeTile() {
		return null;
	}

//	/**
//	 * Method is run when a player is on this tile to convert this tile to a free tile
//	 * @return new free tile
//	 */
//	public FreeTile convertToFreeTile() {
//		return new FreeTile(this.position, icon);
//	}


}
