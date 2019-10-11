package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.swing.Icon;

/**
 * @author Claire
 */
public class InteractableItem implements Interactable {
	
	private Item item;
	
	private Position position;
	
	/**
	 * Blank constructor for level creator
	 */
	public InteractableItem() {}
	
	/**
	 * Not so blank constructor for factory
	 * 
	 * @param item The item this interactable holds
	 */
	public InteractableItem(Item item)
	{
		this.item = item;
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
	public Icon getIcon() 
	{
		return item == null ? null : item.getIcon();
	}
	
	@Override
	public JsonObject persist()
	{
		JsonObjectBuilder builder = this.getBuilder();
		builder.add("item", item == null ? "-1" : item.getClass().getSimpleName());
		return builder.build();
	}

	@Override
	public int zIndex() 
	{
		return 9;
	}

	@Override
	public boolean isPushable() 
	{
		return false;
	}

	@Override
	public boolean isWalkable(Actor actor, ModelAccessObject obj) 
	{
		return true;
	}

	@Override
	public boolean isSafe(Actor actor, ModelAccessObject obj) 
	{
		return true;
	}

	@Override
	public void onEnter(Actor actor, ModelAccessObject obj) 
	{
		if(this.item != null)
			obj.getInventory().addItem(this.item);
		obj.removeInteractable(this);
	}
	
	@Override
	public InteractableItem clone()
	{
		InteractableItem item = new InteractableItem(this.item);
		item.setPosition(this.getPosition());
		return item;
	}

}
