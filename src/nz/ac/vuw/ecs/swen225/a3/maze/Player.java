package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.JsonObject;
import javax.swing.Icon;

/**
 * Class for the players character
 * @author James
 *
 */
public class Player implements Actor{


	private Position currentPosition;
	private String name;
	private Icon icon;
	private boolean isDead;
	private Inventory inventory;
	private int treasureNeeded;

	
	/**
	 * Main constructor for a new player
	 * @param currentPosition
	 * @param name
	 * @param icon
	 */
	public Player(Position currentPosition, String name, Icon icon) {
		super();
		this.currentPosition = currentPosition;
		this.name = name;
		this.icon = icon;
		this.isDead = false;
		this.inventory = new Inventory();
	}
	
	
	/**
	 * Constructor with all fields used for cloning
	 * @param currentPosition
	 * @param name
	 * @param icon
	 * @param isDead
	 * @param inventory
	 * @param treasureNeeded
	 */
	public Player(Position currentPosition, String name, Icon icon, boolean isDead, Inventory inventory,
			int treasureNeeded) {
		super();
		this.currentPosition = currentPosition;
		this.name = name;
		this.icon = icon;
		this.isDead = isDead;
		this.inventory = inventory;
		this.treasureNeeded = treasureNeeded;
	}


	/**
	 * Method to Clone player class
	 */
	public Player clone() {
		return new Player(currentPosition.clone(), name, icon, isDead, inventory.clone(), treasureNeeded);
	}
	
	
	/**
	 * Adds item given as parameter to players inventory
	 * @param item
	 */
	public void AddInventoryItem(Item item) {
		inventory.addItem(item);
	}
	
	/**
	 * Removes item given as parameter from players inventory
	 * @param item
	 */
	public void removeInventoryItem(Item item) {
		//TODO check the .contains works as .equals might need to be overridden
		inventory.removeItem(item);
	}
	
	/**
	 * returns the inventory for this player
	 * @return inventory
	 */
	public Inventory getInventory() {
		return inventory;		
	}

	@Override
	public Position getPosition() {
		return currentPosition;
	}

	@Override
	public void setPosition(Position position) {
		this.currentPosition = position;
		
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
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDead() {
		return isDead;
	}

	@Override
	public void kill() {
		isDead = true;
	}

	@Override
	public boolean isPlayer() {
		//always returns true as this class is for the player
		return true;
	}

	@Override
	public void onCollide(Actor actor, ModelAccessObject obj) {
		//TODO not sure what do do here
	}

}
