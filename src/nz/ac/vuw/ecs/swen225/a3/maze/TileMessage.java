package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.swing.Icon;

import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;

/**
 * Class to represent an empty or free tile
 * @author James
 *
 */
public class TileMessage implements Tile {
	
	private String message;
	
	private Position position;
	
	/**
	 * Paramaterized constructor for res
	 * 
	 * @param message The message of this tile
	 */
	public TileMessage(String message)
	{
		this.message = message;
	}
	
	/**
	 * Blank constructor for newInstance
	 */
	public TileMessage() 
	{
		this("This message is blank");
	}
	
	/**
	 * @param pos
	 */
	public TileMessage(Position pos)
	{
		this.position = pos;
	}
	
	@Override
	public JsonObject persist()
	{
		JsonObjectBuilder builder = this.getBuilder()
										.add("message", message);
		
		return builder.build();
	}
	
	public TileMessage clone() 
	{
		TileMessage tile = new TileMessage();
		tile.setPosition(this.position.clone());
		return tile;
	}

	@Override
	public Position getPosition() 
	{
		return position;
	}

	@Override
	public void setPosition(Position position) 
	{
		this.position = position;
	}
	
	@Override
	public Icon getIcon() {
		return IconFactory.INSTANCE.loadIcon("infoField.png");
	}

	@Override
	public boolean isFloor() 
	{
		return true;
	}

	@Override
	public boolean isSafe(Actor actor, ModelAccessObject obj) 
	{
		return true;
	}

	@Override
	public void onEnter(Interactable interactable, ModelAccessObject obj) {}

	@Override
	public void onEnter(Actor actor, ModelAccessObject obj) 
	{
		if(actor.isPlayer())
			obj.setTutorialMessage(message);
	}

}
