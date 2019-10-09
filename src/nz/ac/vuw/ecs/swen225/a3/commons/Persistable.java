package nz.ac.vuw.ecs.swen225.a3.commons;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

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
	 * @deprecated Use reflection instead.
	 * 
	 * @return A name for this type of object. This name is not used to denote this 
	 * <em>specific instance</em> of this object, or this object's bottom-level type,
	 * but rather the class of object that this object belongs to. Used to identify 
	 * what kind of root factory to use for resurrection.
	 */
	String getName();
	
	/**
	 * @return A basic JsonObjectBuilder that includes this object's type information.
	 */
	default JsonObjectBuilder getBuilder()
	{
		return Json.createObjectBuilder().add("name", this.getClass().getSimpleName());
	}
	
}
