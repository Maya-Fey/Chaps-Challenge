package nz.ac.vuw.ecs.swen225.a3.commons;

/**
 * An exception denoting a contract violation. Unchecked.
 * 
 * @author Claire 300436297
 */
public class ContractViolationException extends RuntimeException {

	private static final long serialVersionUID = -330456999186858856L;

	/**
	 * @param arg0 
	 */
	public ContractViolationException(String arg0) {
		super(arg0);
	}
	
}
