package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import nz.ac.vuw.ecs.swen225.a3.commons.Persistable;

/**
 * An object that exists as on the board of the maze. Can be a 
 * tile, interactable, or actor.
 * 
 * @author Claire
 */
public interface MazeObject extends Persistable {

	/**
	 * @return The current position of this object.
	 */
	Position getPosition();
	
	/**
	 * Changes the location of this object.
	 * 
	 * @param position The desired new position of this object
	 */
	void setPosition(Position position);
	
	default JsonObjectBuilder getBuilder()
	{
		return Json.createObjectBuilder().add("name", this.getClass().getSimpleName())
										 .add("x", this.getPosition().x)
										 .add("y", this.getPosition().y);
	}
	
	default JsonObject persist()
	{
		return this.getBuilder().build();
	}
}
