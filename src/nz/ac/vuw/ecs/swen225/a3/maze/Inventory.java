package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.JsonObject;

import nz.ac.vuw.ecs.swen225.a3.commons.Persistable;

/**
 * A class representing the player's inventory.
 * 
 * @author None yet
 */
public class Inventory implements Persistable {
	
	//TODO: Implement inventory class
	
	/*
	 * Ideally, this class should be able to facilitate:
	 *  - Checking whether an item of a certain type is in the inventory.
	 *  - Adding items
	 *  - Removing only one item of a certain type
	 */
	
	public JsonObject persist() 
	{
		//TODO: Implement
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	public String getName() 
	{
		return "inventory";
	}

}
