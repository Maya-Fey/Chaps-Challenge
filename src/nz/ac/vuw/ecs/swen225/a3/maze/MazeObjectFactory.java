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
public class MazeObjectFactory<Type extends MazeObject> implements ChapsFactory<Type> {

	private final Supplier<Type> constructor;
	
	/**
	 * Creates a new basic factory for the given type.
	 * 
	 * @param constructor
	 */
	public MazeObjectFactory(Supplier<Type> constructor)
	{
		this.constructor = constructor;
	}
	
	public Type resurrect(JsonObject obj) 
	{
		Type nObj = this.newInstance();
		nObj.setPosition(Position.resurrect(obj));
		return nObj;
	}

	public Type newInstance() 
	{
		return constructor.get();
	}

}
