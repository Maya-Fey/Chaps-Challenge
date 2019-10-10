package nz.ac.vuw.ecs.swen225.a3.testing;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;


import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.application.ChapsController;
import nz.ac.vuw.ecs.swen225.a3.application.ChapsControllerFactory;
import nz.ac.vuw.ecs.swen225.a3.application.ChapsControllerImpl;
import nz.ac.vuw.ecs.swen225.a3.application.Main;
import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.ChapsAction;
import nz.ac.vuw.ecs.swen225.a3.maze.ChapsModelFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.Exit;
import nz.ac.vuw.ecs.swen225.a3.render.ChapsViewFactory;
import nz.ac.vuw.ecs.swen225.a3.render.ChapsViewImpl;
import nz.ac.vuw.ecs.swen225.a3.maze.Position;

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
	
	
	


}
