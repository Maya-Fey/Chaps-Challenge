package nz.ac.vuw.ecs.swen225.a3.testing;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.application.Main;

/**
 * JUnit test cases for testing application package
 * @author ferguscurrie
 *
 */
class Testing {
	
	

	/**
	 * Try run main, make sure no error occurs 
	 */
	@Test
	void test_main_run() {
		try {
			String[] args = new String[] {""};
			Main.main(args);
		}catch(Error e) {
			fail("");
		}
		assertTrue(true);

	}
	
	

	
	
	


}
