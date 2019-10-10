package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.ArrayList;
import java.util.Collection;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import nz.ac.vuw.ecs.swen225.a3.commons.Persistable;

/**
 * A class representing an actors inventory.
 *
 * @author James
 */
public class Inventory implements Persistable, Cloneable {

	private ArrayList<Item> inventory;

	/**
	 * Constructor to create the players inventory
	 * starting as empty
	 */
	public Inventory() 
	{
		super();
		this.inventory = new ArrayList<Item>();
	}
	
	/**
	 * Constructor to create players inventory with given list
	 * 
	 * @param inventory The list
	 */
	public Inventory(ArrayList<Item> inventory) 
	{
		super();
		this.inventory = inventory;
	}

	/**
	 * @return All the items in this inventory
	 */
	public Collection<Item> getAll()
	{
		return inventory;
	}

	/**
	 * Returns a clone of the inventory
	 */
	public Inventory clone() 
	{
		ArrayList<Item> inventoryClone = new ArrayList<>();
		for(Item i : inventory) 
			inventoryClone.add(i.clone());
		return new Inventory(inventoryClone);
	}

	/**
	 * Adds item given as parameter to players inventory
	 * @param item
	 */
	public void addItem(Item item) 
	{
		inventory.add(item);
	}

	/**
	 * Removes item given as parameter from players inventory
	 * 
	 * @param type The type of item we're removing
	 */
	public void removeItemOfType(String type) 
	{
		for(int i = 0; i < inventory.size(); i++)
			if(inventory.get(i).getClass().getSimpleName().equals(type))
			{
				inventory.remove(i);
				return;
			}
	}

	/**
	 * Method to check if this inventory contains an item
	 * @param type The type of item we're removing
	 * @return if contains item
	 */
	public boolean hasItemOfType(String type) 
	{
		for(int i = 0; i < inventory.size(); i++)
			if(inventory.get(i).getClass().getSimpleName().equals(type))
				return true;
		return false;
	}
	
	public JsonObject persist()
	{
		JsonObjectBuilder builder = this.getBuilder();
		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
		
		for(Item item : inventory)
			arrBuilder.add(item.getClass().getName());
		
		builder.add("items", arrBuilder.build());
		
		return builder.build();
	}

}
