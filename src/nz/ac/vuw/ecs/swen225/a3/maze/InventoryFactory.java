package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

import nz.ac.vuw.ecs.swen225.a3.application.RootFactory;
import nz.ac.vuw.ecs.swen225.a3.commons.ChapsFactory;

/**
 * Inventory factory
 * 
 * @author Claire
 */
public class InventoryFactory implements ChapsFactory<Inventory> {

	@Override
	public Inventory resurrect(JsonObject obj)
	{
		Inventory inv = new Inventory();
		JsonArray arr = obj.getJsonArray("items");
		arr.forEach((JsonValue value) -> {
			String s = ((JsonString) value).getString();
			inv.addItem(RootFactory.getInstance().itemFactory.newInstance(s));
		});
		return inv;
	}

	@Override
	public Inventory newInstance() 
	{
		return new Inventory();
	}

}
