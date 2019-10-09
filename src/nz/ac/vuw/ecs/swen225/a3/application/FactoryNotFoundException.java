package nz.ac.vuw.ecs.swen225.a3.application;

/**
 * @author Claire
 *
 * An exception indicating a factory for a certain type couldn't be found
 */
public class FactoryNotFoundException extends Error {

	private static final long serialVersionUID = 4305950335070692634L;
	
	/**
	 * @param s The message carried by this exception
	 */
	public FactoryNotFoundException(String s)
	{
		super(s);
	}

}
