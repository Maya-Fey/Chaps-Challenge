package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.JsonObject;
import javax.swing.Icon;

/**
 * Class to represent Exit Lock
 * @author James
 *
 */
public class ExitLock implements Tile{
	private Position position;
	private String name;
	private Icon icon;

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
		if(actor instanceof Player) {
			Player p = (Player)actor;
			if (p.hasAllTreasure()) {
				return true;
			}
		}
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
		return new FreeTile(this.position, icon);
	}

}
