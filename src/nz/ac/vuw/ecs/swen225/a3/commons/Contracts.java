package nz.ac.vuw.ecs.swen225.a3.commons;

import java.io.File;

/**
 * A helper class for checking preconditions.
 * 
 * @author Claire
 */
public class Contracts {

	/**
	 * @param o The object we're checking
	 * @param message The message that shows when the contract is violated
	 */
	public static void notNull(Object o, String message)
	{
		if(o == null)
			throw new ContractViolationException(message);
	}
	
	/**
	 * @param file The file we're checking
	 * @param message The message that shows when the contract is violated
	 */
	public static void existsAndIsFile(File file, String message)
	{
		if(!(file.exists() && file.isFile()))
			throw new ContractViolationException(message);
	}
	
	/**
	 * @param bool The condition
	 * @param message The message that shows when the contract is violated
	 */
	public static void arbitrary(boolean bool, String message)
	{
		if(bool)
			throw new ContractViolationException(message);
	}
}
