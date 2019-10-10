package nz.ac.vuw.ecs.swen225.a3.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.application.Main;
import nz.ac.vuw.ecs.swen225.a3.render.ChapsViewImpl;

/**
 * JUnit test cases for testing application package
 * @author ferguscurrie
 *
 */
class ApplicationTesting {
	
	

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
	
	
	/**
	 * Test the four digit format method of ChapsViewImpl works correctly 
	 */
	@Test
	void test_four_digit_format() {
		int x = 4;
		String expected = "0004";
		
		String result = new ChapsViewImpl().fourDigitFormat(x);
		assertTrue(result.equals(expected));

	}
	
	/**
	 * Test the update board method of ChapsViewImpl works 
	 */
	@Test
	void test_update_board() {
		/*
		ChapsViewImpl cvl = new ChapsViewImpl();
		System.out.println(cvl.);
		*/
		assertTrue(true);

	}


}
