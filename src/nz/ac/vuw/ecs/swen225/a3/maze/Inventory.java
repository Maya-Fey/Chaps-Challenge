package nz.ac.vuw.ecs.swen225.a3.maze;

import java.awt.Color;
import java.util.ArrayList;

import javax.json.JsonObject;

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
	public Inventory() {
		super();
		this.inventory = new ArrayList<Item>();
	}
	
	
	/**
	 * Constructor to create players inventory
	 * with given list
	 * @param inventory
	 */
	public Inventory(ArrayList<Item> inventory) {
		super();
		this.inventory = inventory;
	}



	/**
	 * Returns a clone of the inventory
	 */
	public Inventory clone() {
		ArrayList<Item> inventoryClone = new ArrayList<>();
		for(Item i:inventory) {
			inventoryClone.add(i.clone());
		}
		return new Inventory(inventoryClone);
	}

	/**
	 * Adds item given as parameter to players inventory
	 * @param item
	 */
	public void addItem(Item item) {
		inventory.add(item);
	}

	/**
	 * Removes item given as parameter from players inventory
	 * @param item
	 */
	public void removeItem(Item item) {
		//TODO check the .contains works as .equals might need to be overridden
		if (inventory.contains(item)) {
			inventory.remove(item);
		}
	}

	/**
	 * Method to count how many treasure items are in a players inventory
	 * @return amount of treasure
	 */
	public int treasureCollected() {
		int count = 0;

		for (Item item : inventory) {
			if(item instanceof KeyItem) {
				count++;
			}
		}

		return count;
	}

	/**
	 * Method to check if this inventory has a key of the given colour
	 * @param color key colour to check
	 * @return if the inventory has a matching key
	 */
	public boolean hasKeyColour(Color color) {
		for (Item item : inventory) {
			if(item instanceof KeyItem) {
				KeyItem k = (KeyItem)item;
				if(k.getKeyColour().equals(color))
					return true;
			}
		}
		return false;
	}

	/**
	 * Method to check if this inventory contains an item
	 * @param item to check
	 * @return if contains item
	 */
	public boolean hasItem(Item item) {
		return inventory.contains(item);
	}

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
