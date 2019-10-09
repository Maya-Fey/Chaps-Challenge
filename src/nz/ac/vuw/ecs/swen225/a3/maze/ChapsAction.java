package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.Json;
import javax.json.JsonObject;

import nz.ac.vuw.ecs.swen225.a3.commons.Persistable;
import nz.ac.vuw.ecs.swen225.a3.maze.Position.Direction;

/**
 * An enum representing all the events that can be sent to ChapModel
 * 
 * @author Claire 300436297
 */
public enum ChapsAction implements Persistable {
	
	/**
	 * Move the game forward by one tick (a faction of a second)
	 */
	TICK,
	
	/**
	 * Chap moves up
	 */
	UP,
	
	/**
	 * Chap moves down
	 */
	DOWN,
	
	/**
	 * Chap moves left
	 */
	LEFT,
	
	/**
	 * Chap moves up
	 */
	RIGHT;

	public JsonObject persist() 
	{
		return Json.createObjectBuilder().add("type", this.name()).build();
	}

	public String getName() 
	{
		return "action";
	}
	

}
