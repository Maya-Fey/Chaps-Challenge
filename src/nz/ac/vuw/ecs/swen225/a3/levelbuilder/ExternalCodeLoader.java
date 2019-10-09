package nz.ac.vuw.ecs.swen225.a3.levelbuilder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import nz.ac.vuw.ecs.swen225.a3.commons.ChapsFactory;
import nz.ac.vuw.ecs.swen225.a3.commons.Contracts;
import nz.ac.vuw.ecs.swen225.a3.maze.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.Interactable;
import nz.ac.vuw.ecs.swen225.a3.maze.Item;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;

/**
 * Parses a jar file and finds all worthwhile external code.
 * 
 * @author Claire
 */
public class ExternalCodeLoader {
	
	private final File file;
	
	private final URLClassLoader loader;
	
	/**
	 * Externally loaded item classes
	 */
	public final Set<ExternalChapsClass<Item>> itemClasses = new HashSet<>();
	
	/**
	 * Externally loaded item classes
	 */
	public final Set<ExternalChapsClass<Actor>> actorClasses = new HashSet<>();
	
	/**
	 * Externally loaded item classes
	 */
	public final Set<ExternalChapsClass<Interactable>> interactableClasses = new HashSet<>();
	
	/**
	 * Externally loaded item classes
	 */
	public final Set<ExternalChapsClass<Tile>> tileClasses = new HashSet<>();
	
	/**
	 * @param file
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public ExternalCodeLoader(File file) throws IOException
	{
		Contracts.existsAndIsFile(file, "File needs to both exist and be a file");
		
		this.file = file;
		
		loader = new URLClassLoader(new URL[] { file.toURI().toURL() }, LevelBuilder.class.getClassLoader());
		
		Set<String> classnames = this.getListedClasses();
		
		Map<Class<?>, String> concretes = new HashMap<>();
		Map<Class<?>, Class<?>> factories = new HashMap<>();
		
		for(String classname : classnames)
		{
			try {
				Class<?> newClass = Class.forName(classname, true, loader);
				
				//Ignore non-concrete classes
				if(Modifier.isAbstract(newClass.getModifiers()) || newClass.isInterface())
					continue;
				
				//If it's something in our game
				if(Item.class.isAssignableFrom(newClass)) {
					concretes.put(newClass, "Item");
				} else if(Actor.class.isAssignableFrom(newClass)) {
					concretes.put(newClass, "Actor");
				} else if(Interactable.class.isAssignableFrom(newClass)) {
					concretes.put(newClass, "Interactable");
				} else if(Tile.class.isAssignableFrom(newClass)) {
					concretes.put(newClass, "Tile");
				} if(ChapsFactory.class.isAssignableFrom(newClass)) {
					//Get the supertype that this factory implements, either ChapsFactory or a
					//class that implements ChapsFactory
					Type type = this.getChapsFactoryType(newClass);
					
					//If we can find it and if it's a parameterizedType, go forward
					if(type != null && ParameterizedType.class.isAssignableFrom(type.getClass()))
					{
						//TODO: Make sure it's the actual type argument we want?
						ParameterizedType ptype = (ParameterizedType) type;
						Type arg = ptype.getActualTypeArguments()[0];
						
						//If it's concrete
						if(arg.getClass().equals(Class.class)) {
							//Check for default constructor
							boolean hasDef = false;
							for(Constructor<?> constructor : newClass.getConstructors())
								if(constructor.getParameterCount() == 0)
									hasDef = true;
							
							if(hasDef)
								factories.put((Class<?>) arg, newClass); 
						}
					}
				}
				
			} catch (ClassNotFoundException e) {
				System.out.println("Class '" + classname + "' in jar '" + file.getAbsolutePath() + "' was not loadable");
			}
		}
		
		/*
		 * Add all the concrete classes that have factories
		 */
		
		for(Class<?> theClass : concretes.keySet())
		{
			Class<?> factory = factories.get(theClass);
			if(factory != null)
			{
				switch(concretes.get(theClass))
				{
					case "Item":
						itemClasses.add(new ExternalChapsClass<Item>((Class<? extends Item>) theClass, (Class<? extends ChapsFactory<Item>>) factory));
						break;
					case "Actor":
						actorClasses.add(new ExternalChapsClass<Actor>((Class<? extends Actor>) theClass, (Class<? extends ChapsFactory<Actor>>) factory));
						break;
					case "Interactable":
						interactableClasses.add(new ExternalChapsClass<Interactable>((Class<? extends Interactable>) theClass, (Class<? extends ChapsFactory<Interactable>>) factory));
						break;
					case "Tile":
						tileClasses.add(new ExternalChapsClass<Tile>((Class<? extends Tile>) theClass, (Class<? extends ChapsFactory<Tile>>) factory));
						break;
				}
			}
		}
	}
	
	/**
	 * Enumerates all the classes in a jar file.
	 * 
	 * @return A list of all the listed classes in the given jarfile.
	 * 
	 * @throws IOException in the event of an IO Error
	 */
	protected Set<String> getListedClasses() throws IOException
	{
		Set<String> set = new HashSet<>();
		JarFile jar = new JarFile(file);
		Enumeration<JarEntry> entries = jar.entries();
		while(entries.hasMoreElements()) {
			String name = entries.nextElement().getName();
			//If it's a class, format the name as a "package.subpackage.Class" name and add it to the set
			if(name.substring(name.length() - 6, name.length()).equals(".class"))
				set.add(name.replace('/', '.').substring(0, name.length() - 6)); 
		}
		jar.close();
		return set;
	}
	
	/**
	 * Returns the Type representing the implemented ChapsFactory for this class.
	 * Useful for getting the concrete type parameter, if one exists.
	 * 
	 * @return The type representing the implemented ChapsFactory interface, or null
	 * if it can't be found
	 */
	private Type getChapsFactoryType(Class<?> theClass)
	{
		Type[] type = theClass.getGenericInterfaces();
		for(Type t : type)
			if(t.getTypeName().contains("ChapsFactory"))
				return t;
		
		Type sup = theClass.getGenericSuperclass();
		
		if(sup == null)
			return null;
		else
			return sup;
	}

}
