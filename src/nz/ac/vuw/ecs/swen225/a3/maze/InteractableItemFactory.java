package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.JsonObject;
import javax.json.JsonValue.ValueType;

import nz.ac.vuw.ecs.swen225.a3.application.RootFactory;
import nz.ac.vuw.ecs.swen225.a3.commons.ChapsFactory;

/**
 * @author Claire
 */
public class InteractableItemFactory implements ChapsFactory<InteractableItem> {

	@Override
	public InteractableItem resurrect(JsonObject obj) 
	{
		Position pos = Position.resurrect(obj);
		Item item = null;
		if(obj.getValue("item").getValueType() != ValueType.NULL)
			item = RootFactory.getInstance().itemFactory.newInstance(obj.getString("item"));
		InteractableItem res = new InteractableItem(item);
		res.setPosition(pos);
		return res;
	}

	@Override
	public InteractableItem newInstance() 
	{
		return new InteractableItem();
	}

}
