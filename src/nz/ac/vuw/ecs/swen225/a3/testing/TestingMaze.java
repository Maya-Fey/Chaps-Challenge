package nz.ac.vuw.ecs.swen225.a3.testing;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.ImageIcon;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.Exit;
import nz.ac.vuw.ecs.swen225.a3.maze.Position;

/**
 * JUnit test cases for maze package, basic functionalities of game 
 * @author ferguscurrie
 *
 */
class TestingMaze {

	/**
	 * Tests the exit class
	 */
	@Test
	void test_exit() {
		//Constructing 
		Exit e = null;
		ImageIcon i = null;
		try {
			i = (ImageIcon) IconFactory.INSTANCE.loadIcon("dirt.png");
			Position p = new Position(0,0);
			e = new Exit(p,"",i);
		}catch(Error ee) {
			assert(false);
		}
		assert(true);
		//Clone
		Exit e2 = e.clone();
		assertTrue(e2 != e);
		assertTrue(e2.getName().equals(e.getName()));
		assertTrue(e2.getPosition().x == (e.getPosition().x) && e2.getPosition().y == (e.getPosition().y));
		//Set position
		Position pp = new Position(0,1);
		e.setPosition(pp);
		assertTrue(e.getPosition().y == 1);
		//Other
		assertTrue(e.isFloor() == false);
		assertTrue(e.getIcon() == i);
	}
	
	/**
	 * Tests if an the exit class 
	 */
	@Test
	void test_exit() {
		//Constructing 
		Exit e = null;
		ImageIcon i = null;
		try {
			i = (ImageIcon) IconFactory.INSTANCE.loadIcon("dirt.png");
			Position p = new Position(0,0);
			e = new Exit(p,"",i);
		}catch(Error ee) {
			assert(false);
		}
		assert(true);
		//Clone
		Exit e2 = e.clone();
		assertTrue(e2 != e);
		assertTrue(e2.getName().equals(e.getName()));
		assertTrue(e2.getPosition().x == (e.getPosition().x) && e2.getPosition().y == (e.getPosition().y));
		//Set position
		Position pp = new Position(0,1);
		e.setPosition(pp);
		assertTrue(e.getPosition().y == 1);
		//Other
		assertTrue(e.isFloor() == false);
		assertTrue(e.getIcon() == i);
	}
	
	

}
