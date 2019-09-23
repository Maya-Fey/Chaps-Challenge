package nz.ac.vuw.ecs.swen225.a3.commons;

import javax.json.JsonObject;

/**
 * An interface representing any object that can be written to JSON.
 * 
 * @author Claire
 */
public interface Persistable {

	/**
	 * @return A JSON object that can be used to wholly recreate this object
	 */
	JsonObject persist();
	
	/**
	 * @return A name for this type of object. This name is not used to denote this 
	 * <em>specific instance</em> of this object, or this object's bottom-level type,
	 * but rather the class of object that this object belongs to. Used to identify 
	 * what kind of root factory to use for resurrection.
	 */
	String getName();
	
}
