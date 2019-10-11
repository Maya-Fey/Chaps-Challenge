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
	 * @return A basic JsonObjectBuilder that includes this object's type information.
	 */
	default JsonObjectBuilder getBuilder()
	{
		return Json.createObjectBuilder().add("name", this.getClass().getSimpleName());
	}
	
}
