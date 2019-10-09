package nz.ac.vuw.ecs.swen225.a3.levelbuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import nz.ac.vuw.ecs.swen225.a3.commons.ChapsFactory;
import nz.ac.vuw.ecs.swen225.a3.commons.Persistable;

/**
 * An object representing an externally loaded chaps class.
 * 
 * @author Claire
 * 
 * @param <Type> The base type of this external class (Eg. Tile)
 */
public class ExternalChapsClass<Type extends Persistable> {
	
	private final Class<? extends Type> concrete;
	
	@SuppressWarnings("unused")
	private final Class<? extends ChapsFactory<Type>> factory;
	
	private ChapsFactory<Type> theFactory;

	/**
	 * Creates an instance of this class
	 * 
	 * @param concrete
	 * @param factory
	 */
	@SuppressWarnings("unchecked")
	public ExternalChapsClass(Class<? extends Type> concrete, Class<? extends ChapsFactory<Type>> factory)
	{
		this.concrete = concrete;
		this.factory = factory;
		
		try {
			Constructor<ChapsFactory<Type>> constructor = (Constructor<ChapsFactory<Type>>) factory.getConstructor(new Class<?>[0]);
			theFactory = constructor.newInstance();
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new Error(e);
		}
	}
	
	/**
	 * @return The name of this external class's type
	 */
	public String getTypename()
	{
		return concrete.getSimpleName();
	}
	
	/**
	 * @return A factory for this class
	 */
	public ChapsFactory<Type> getFactory()
	{
		return theFactory;
	}
	
}
