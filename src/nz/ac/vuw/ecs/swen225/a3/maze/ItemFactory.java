package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.function.Supplier;

import javax.json.JsonObject;

import nz.ac.vuw.ecs.swen225.a3.commons.ChapsFactory;

/**
 * A class to resurrect and create simple MazeObjects.
 * 
 * @author Claire
 *
 * @param <Type>
 */
public class ItemFactory<Type extends Item> implements ChapsFactory<Type> {

	private final Supplier<Type> constructor;
	
	/**
	 * Creates a new basic factory for the given type.
	 * 
	 * @param constructor
	 */
	public ItemFactory(Supplier<Type> constructor)
	{
		this.constructor = constructor;
	}
	
	public Type resurrect(JsonObject obj) 
	{
		return this.newInstance();
	}

	public Type newInstance() 
	{
		return constructor.get();
	}

}
