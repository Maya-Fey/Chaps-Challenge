package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.JsonObject;
import javax.swing.Icon;

/**
 * Class to represent an info field
 * Text is displayed when the player steps on the tile
 * @author James
 *
 */
public class InfoField implements Tile{
	private Position position;
	private String name;
	private Icon icon;
	private String infoText;



	/**
	 * Default constructor for Info Field tiles
	 * @param position position of tile
	 * @param name given name of tile
	 * @param icon to represent tile
	 * @param infoText text that displays when standing on tile
	 */
	public InfoField(Position position, String name, Icon icon, String infoText) {
		super();
		this.position = position;
		this.name = name;
		this.icon = icon;
		this.infoText = infoText;
	}
	
	/**
	 * Method to clone infoField tile
	 */
	public InfoField clone() {
		return new InfoField(position.clone(), name, icon, infoText);
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
