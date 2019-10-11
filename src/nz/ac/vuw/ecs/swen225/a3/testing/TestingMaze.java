package nz.ac.vuw.ecs.swen225.a3.testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.ActorPlayer;
import nz.ac.vuw.ecs.swen225.a3.maze.ChapsAction;
import nz.ac.vuw.ecs.swen225.a3.maze.ChapsEvent;
import nz.ac.vuw.ecs.swen225.a3.maze.InteractableChip;
import nz.ac.vuw.ecs.swen225.a3.maze.Inventory;
import nz.ac.vuw.ecs.swen225.a3.maze.Item;
import nz.ac.vuw.ecs.swen225.a3.maze.MazeObject;
import nz.ac.vuw.ecs.swen225.a3.maze.Position;
import nz.ac.vuw.ecs.swen225.a3.maze.Position.Direction;
import nz.ac.vuw.ecs.swen225.a3.maze.TileExit;
import nz.ac.vuw.ecs.swen225.a3.maze.TileFree;

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
				"HIDE_TUTORIAL_MESSAGE","GAME_LOST_PLAYER_DIED","GAME_LOST_TIME_OUT","PLAYER_WINS"));
		int count = 0;
		for(ChapsEvent c : ChapsEvent.values()) {
			if(!(c.name().equals(correct.get(count)))) {
				assert(false);
			}
			count++;
		}
		assert(true);
		
	}
	
	/**
	 * Tests InteractableChip class 
	 */
	@Test
	void test_interactable_chip() {
		InteractableChip ic = new InteractableChip();
		//Check gets treasure icon
		ImageIcon img = (ImageIcon) IconFactory.INSTANCE.loadIcon("treasure.png");
		assertTrue(img == ic.getIcon());
		//Position get/set
		Position p = new Position(0,0);
		ic.setPosition(p);
		assertTrue(ic.getPosition().x == p.x && ic.getPosition().y == p.y );
		assert(true);

		//Clone
		InteractableChip ic2 = ic.clone();
		assertTrue(ic2 != ic);
		
		//Could add on enter test...
		
		//Other
		assertTrue(ic.zIndex() == 1);
		assertTrue(ic.isPushable() == false);
		assertTrue(ic.isWalkable(null,null) == true);
		assertTrue(ic.isSafe(null,null) == true);
		
	}
	
	/**
	 * Tests ActorPlayer class 
	 */
	@Test
	void test_actorplayer() {
		ActorPlayer a = new ActorPlayer();
		//Setting and Getting position
		Position p = new Position(0,0);
		a.setPosition(p);
		assertTrue(a.getPosition().x == p.x && a.getPosition().y == p.y );
		//Clone
		ActorPlayer ic2 = a.clone();
		assertTrue(ic2 != a);
		//Check gets treasure icon
		ImageIcon img = (ImageIcon) IconFactory.INSTANCE.loadIcon("chap.png");
		assertTrue(img == a.getIcon());
		//Test is play
		assertTrue(a.isPlayer() == true);
	}
	
	/**
	 * Tests Inventory Class --- FINISH THIS TEST LATER ONCE CONCRETE ITEM CLASS ADDED 
	 */
	@Test
	void test_inventory() {
		//Both constructors
		//Item item = new Item();
		Inventory i = new Inventory(); 
		//Inventory ii = new Inventory((ArrayList<Item>)(i.getAll()));
		//Cloning 
		Inventory i2 = i.clone();
		assertTrue(i != i2);
		//Get all
		assertTrue(i.getAll().size() == 0);
		fail();
		
	}
	
	/**
	 * Tests TileExit class 
	 */
	@Test
	void test_tileexit() {
		TileExit a = new TileExit();
		//Setting and Getting position
		Position p = new Position(0,0);
		a.setPosition(p);
		assertTrue(a.getPosition().x == p.x && a.getPosition().y == p.y );
		//Clone
		TileExit ic2 = a.clone();
		assertTrue(ic2 != a);
		//Check gets treasure icon
		ImageIcon img = (ImageIcon) IconFactory.INSTANCE.loadIcon("exit.png");
		assertTrue(img == a.getIcon());
		//Other
		assertTrue(a.isFloor() == true);
		assertTrue(a.isSafe(null,null) == true);
	}
	
	/**
	 * Tests TileExit class 
	 */
	@Test
	void test_tilefree() {
		Position p = new Position(0,0);
		TileFree a = new TileFree(p);
		//Setting and Getting position
		a.setPosition(p);
		assertTrue(a.getPosition().x == p.x && a.getPosition().y == p.y );
		//Clone
		TileFree ic2 = a.clone();
		assertTrue(ic2 != a);
		//Check gets treasure icon
		ImageIcon img = (ImageIcon) IconFactory.INSTANCE.loadIcon("freeTile.png");
		assertTrue(img == a.getIcon());
		//Other
		assertTrue(a.isFloor() == true);
		assertTrue(a.isSafe(null,null) == true);
	}
	
	
	
}
