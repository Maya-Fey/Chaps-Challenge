package nz.ac.vuw.ecs.swen225.a3.commons;

import javax.json.JsonObject;

/**
 * A factory capable of creating objects from JsonObjects. Used
 * for loading save files and new levels.
 * 
 * @author Claire
 * @param <Type> The type of object this factory can resurrect.
 */
public abstract class ChapsFactory<Type extends Persistable> {

	/**
	 * Resurrects an object from JSON
	 * 
	 * @param obj JSON representing an object of this factory's type.
	 * @return The object represented by the passed JSON
	 */
	public abstract Type resurrect(JsonObject obj);
	
}
