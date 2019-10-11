package nz.ac.vuw.ecs.swen225.a3.testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.ChapsAction;
import nz.ac.vuw.ecs.swen225.a3.maze.ChapsEvent;
import nz.ac.vuw.ecs.swen225.a3.maze.Position;
import nz.ac.vuw.ecs.swen225.a3.maze.Position.Direction;

/**
 * JUnit test cases for maze package, basic functionalities of game
 * 
 * @author ferguscurrie
 *
 */
class TestingMaze {

	/**
	 * Tests the exit class
	 */
	@Test
	void test_exit() {
		// Constructing
		/*
		Exit e = null;
		ImageIcon i = null;
		try {
			i = (ImageIcon) IconFactory.INSTANCE.loadIcon("exit.png");
			Position p = new Position(0, 0);
			e = new Exit(p, "", i);
		} catch (Error ee) {
			assert (false);
		}
		assert (true);
		// Clone
		Exit e2 = e.clone();
		assertTrue(e2 != e);
		assertTrue(e2.getName().equals(e.getName()));
		assertTrue(e2.getPosition().x == (e.getPosition().x) && e2.getPosition().y == (e.getPosition().y));
		// Set position
		Position pp = new Position(0, 1);
		e.setPosition(pp);
		assertTrue(e.getPosition().y == 1);
		// Other
		assertTrue(e.isFloor() == false);
		assertTrue(e.getIcon() == i);
		*/
	}

	/**
	 * Tests the position class
	 */
	@Test
	void test_position() {
		// Constructing
		Position p = new Position(0, 0);
		// CLoning
		Position q = p.clone();
		assertTrue(p != q);
		assertTrue(q.x == p.x && q.y == p.y);
		// Translate - Direction
		ArrayList<Direction> direc = new ArrayList<Direction>(
				Arrays.asList(Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.WEST));
		ArrayList<Integer> mov = new ArrayList<Integer>(Arrays.asList(1, 0, 0, 1, 0, -1, -1, 0));
		int count = 0;
		for (Direction d : direc) {
			Position pn = p.translate(d);
			assertTrue((pn.x == p.x + mov.get(count)) && (pn.y == p.y + mov.get(count + 1)));
			count += 2;
		}
		// Translate - ChapsAction
		ArrayList<ChapsAction> ca = new ArrayList<ChapsAction>(
				Arrays.asList(ChapsAction.RIGHT, ChapsAction.UP, ChapsAction.DOWN, ChapsAction.LEFT));
		count = 0;
		for (ChapsAction d : ca) {
			Position pn = p.translate(d);
			assertTrue((pn.x == p.x + mov.get(count)) && (pn.y == p.y + mov.get(count + 1)));
			count += 2;
		}
	}
	
	//DISPLAY_UPDATE_REQUIRED,INV_UPDATE_REQUIRED,TIME_UPDATE_REQUIRED,CHIPS_UPDATE_REQUIRED,SHOW_TUTORIAL_MESSAGE,HIDE_TUTORIAL_MESSAGE,GAME_LOST_PLAYER_DIED,GAME_LOST_TIME_OUT

	/**
	 * Tests ChapsEvent has the correct enums 
	 */
	@Test
	void test_chaps_event() {
		ArrayList<String> correct = new ArrayList<String>(Arrays.asList("DISPLAY_UPDATE_REQUIRED",
				"INV_UPDATE_REQUIRED","TIME_UPDATE_REQUIRED","CHIPS_UPDATE_REQUIRED","SHOW_TUTORIAL_MESSAGE",
				"HIDE_TUTORIAL_MESSAGE","GAME_LOST_PLAYER_DIED","GAME_LOST_TIME_OUT"));
		int count = 0;
		for(ChapsEvent c : ChapsEvent.values()) {
			if(!(c.name().equals(correct.get(count)))) {
				assert(false);
			}
			count++;
		}
		assert(true);
		
	}
	
}
