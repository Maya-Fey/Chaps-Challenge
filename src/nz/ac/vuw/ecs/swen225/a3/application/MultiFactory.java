package nz.ac.vuw.ecs.swen225.a3.application;

import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

import nz.ac.vuw.ecs.swen225.a3.commons.ChapsFactory;
import nz.ac.vuw.ecs.swen225.a3.commons.Persistable;

/**
 * @author Claire
 * 
 * A factory that can resurrect many subclasses of one class.
 *
 * @param <Type> The base type this factory can resurrect
 */
class MultiFactory<Type extends Persistable> implements ChapsFactory<Type>
{
	private final Map<String, ChapsFactory<? extends Type>> factories = new HashMap<>();
	
	@Override
	/**
	 * @param obj The object in need of resurrection.
	 * 
	 * @return A concrete object represented by the JsonObject.
	 * 
	 * @throws FactoryNotFoundException if the type represented by the JsonObject isn't found.
	 */
	public Type resurrect(JsonObject obj) 
	{
		ChapsFactory<? extends Type> factory = factories.get(obj.getString("typename"));
		
		if(factory == null)
			throw new FactoryNotFoundException("Factory of type " + obj.getString("typename") + " not found");
		
		return factory.resurrect(obj);
	}
	
	/**
	 * Adds a factory to this multi-factory, so it can be used to resurrect objects of this type.
	 * 
	 * @param name 
	 * @param factory The factory to add.
	 */
	public void addFactory(String name, ChapsFactory<? extends Type> factory)
	{
		factories.put(name, factory);
	}
	
}