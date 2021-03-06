package nz.ac.vuw.ecs.swen225.a3.testing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.application.GameState;
import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.ActorPlayer;
import nz.ac.vuw.ecs.swen225.a3.maze.ChapsAction;
import nz.ac.vuw.ecs.swen225.a3.maze.ChapsEvent;
import nz.ac.vuw.ecs.swen225.a3.maze.ChapsModelFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.ChapsModelImpl;
import nz.ac.vuw.ecs.swen225.a3.maze.Interactable;
import nz.ac.vuw.ecs.swen225.a3.maze.InteractableChip;
import nz.ac.vuw.ecs.swen225.a3.maze.Inventory;
import nz.ac.vuw.ecs.swen225.a3.maze.Position;
import nz.ac.vuw.ecs.swen225.a3.maze.Position.Direction;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
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

	/**
	 * Tests ChapsEvent has the correct enums
	 */
	@Test
	void test_chaps_event() {
		ArrayList<String> correct = new ArrayList<String>(Arrays.asList("DISPLAY_UPDATE_REQUIRED",
				"INV_UPDATE_REQUIRED", "TIME_UPDATE_REQUIRED", "CHIPS_UPDATE_REQUIRED", "SHOW_TUTORIAL_MESSAGE",
				"HIDE_TUTORIAL_MESSAGE", "GAME_LOST_PLAYER_DIED", "GAME_LOST_TIME_OUT", "PLAYER_WINS"));
		int count = 0;
		for (ChapsEvent c : ChapsEvent.values()) {
			if (!(c.name().equals(correct.get(count)))) {
				assert (false);
			}
			count++;
		}
		assert (true);

	}

	/**
	 * Tests InteractableChip class
	 */
	@Test
	void test_interactable_chip() {
		InteractableChip ic = new InteractableChip();
		// Check gets treasure icon
		ImageIcon img = (ImageIcon) IconFactory.INSTANCE.loadIcon("treasure.png");
		assertTrue(img == ic.getIcon());
		// Position get/set
		Position p = new Position(0, 0);
		ic.setPosition(p);
		assertTrue(ic.getPosition().x == p.x && ic.getPosition().y == p.y);
		assert (true);

		// Clone
		InteractableChip ic2 = ic.clone();
		assertTrue(ic2 != ic);

		// Could add on enter test...

		// Other
		assertTrue(ic.zIndex() == 1);
		assertTrue(ic.isPushable() == false);
		assertTrue(ic.isWalkable(null, null) == true);
		assertTrue(ic.isSafe(null, null) == true);

	}

	/**
	 * Tests ActorPlayer class
	 */
	@Test
	void test_actorplayer() {
		ActorPlayer a = new ActorPlayer();
		// Setting and Getting position
		Position p = new Position(0, 0);
		a.setPosition(p);
		assertTrue(a.getPosition().x == p.x && a.getPosition().y == p.y);
		// Clone
		ActorPlayer ic2 = a.clone();
		assertTrue(ic2 != a);
		// Check gets treasure icon
		ImageIcon img = (ImageIcon) IconFactory.INSTANCE.loadIcon("front.png");
		assertTrue(img == a.getIcon());
		// Test is play
		assertTrue(a.isPlayer() == true);
	}

	/**
	 * Tests TileExit class
	 */
	@Test
	void test_tileexit() {
		TileExit a = new TileExit();
		// Setting and Getting position
		Position p = new Position(0, 0);
		a.setPosition(p);
		assertTrue(a.getPosition().x == p.x && a.getPosition().y == p.y);
		// Clone
		TileExit ic2 = a.clone();
		assertTrue(ic2 != a);
		// Check gets treasure icon
		ImageIcon img = (ImageIcon) IconFactory.INSTANCE.loadIcon("exit.png");
		assertTrue(img == a.getIcon());
		// Other
		assertTrue(a.isFloor() == true);
		assertTrue(a.isSafe(null, null) == true);
	}

	/**
	 * Tests TileExit class
	 */
	@Test
	void test_tilefree() {
		Position p = new Position(0, 0);
		TileFree a = new TileFree(p);
		// Setting and Getting position
		a.setPosition(p);
		assertTrue(a.getPosition().x == p.x && a.getPosition().y == p.y);
		// Clone
		TileFree ic2 = a.clone();
		assertTrue(ic2 != a);
		// Check gets treasure icon
		ImageIcon img = (ImageIcon) IconFactory.INSTANCE.loadIcon("freeTile.png");
		assertTrue(img == a.getIcon());
		// Other
		assertTrue(a.isFloor() == true);
		assertTrue(a.isSafe(null, null) == true);
	}

	/**
	 * Tests the factorys for TileExit and TileFree --- HOW TO TEST THIS?
	 */
	@Test
	void test_tilefactory() {
		// new TileExitFactory();
		// new TileFreeFactory();

	}

	/**
	 * Tests the main methods in gamestate
	 */
	@Test
	void test_gamestate() {
		Tile[][] maze = new Tile[2][2];
		List<Interactable> interactables = new ArrayList<Interactable>();
		interactables.add(new InteractableChip());
		List<Actor> actors = new ArrayList<Actor>();
		actors.add(new ActorPlayer());
		Inventory inv = new Inventory();
		GameState gs = new GameState(maze, interactables, actors, inv, 10, 10, 0);
		// Getters
		assertTrue(inv == gs.getInventory());
		assertTrue(10 == gs.getTimeRemaining());
		assertTrue(10 == gs.getChipsRemaining());
		assertTrue(maze == gs.getMaze());
		assertTrue(interactables == gs.getInteractables());
		assertTrue(actors == gs.getActors());
		// Persist
		String target = "{\"timeRemaining\":10,\"chipsRemaining\":10,\"level\":0,\"tiles\":[],\"inventory\":{\"name\":\"Inventory\",\"items\":[]},\"interactables\":[{\"name\":\"InteractableChip\",\"x\":0,\"y\":0}],\"actors\":[{\"name\":\"ActorPlayer\",\"x\":0,\"y\":0}]}";
		assertTrue(target.equals(gs.persist().toString()));

	}

	/**
	 * Tests the onAction method of ChapsModelImpl
	 * 
	 */
	@Test
	void test_ChapsModelImpl_onActions() {
		ChapsModelImpl cmi = (ChapsModelImpl) new ChapsModelFactory().produce();
		Tile[][] maze = new Tile[2][2];
		List<Interactable> interactables = new ArrayList<Interactable>();
		InteractableChip iii = new InteractableChip();
		iii.setPosition(new Position(0, 0));
		interactables.add(iii);
		List<Actor> actors = new ArrayList<Actor>();
		Actor a = new ActorPlayer();
		a.setPosition(new Position(0, 0));
		actors.add(a);
		Inventory inv = new Inventory();
		GameState gs = new GameState(maze, interactables, actors, inv, 10, 10, 0);
		// Set the state
		cmi.setState(gs);

		// Test move
		cmi.onAction(ChapsAction.DOWN);

	}

	/**
	 * Tests the main methods of ChampsModelImpl except for onAction which has its own test case
	 */
	@Test
	void test_ChapsModelImpl_othermethods() {
		// Setup
		ChapsModelImpl cmi = (ChapsModelImpl) new ChapsModelFactory().produce();
		Tile[][] maze = new Tile[2][2];
		List<Interactable> interactables = new ArrayList<Interactable>();
		InteractableChip iii = new InteractableChip();
		iii.setPosition(new Position(0, 0));
		interactables.add(iii);
		List<Actor> actors = new ArrayList<Actor>();
		Actor a = new ActorPlayer();
		a.setPosition(new Position(0, 0));
		actors.add(a);
		Inventory inv = new Inventory();
		GameState gs = new GameState(maze, interactables, actors, inv, 10, 10, 0);
		// Set the state
		cmi.setState(gs);
		GameState copy = cmi.getState();
		assertTrue(copy != gs); // Should be deep clone
		// Get visable
		cmi.getVisibleArea();
		// Chips Numbers
		int num = cmi.getChipsRemaining();
		cmi.remChips(1);
		assertTrue(num == cmi.getChipsRemaining() + 1);
		cmi.remChips(9);
		assertTrue(cmi.hasAllChips());
		cmi.remChips(-2);
		assertTrue(cmi.getInventory() == inv);
		cmi.removeInteractable(iii);
		// Check tick works
		int time = cmi.getTimeRemaining();
		cmi.onAction(ChapsAction.TICK);
		int time2 = cmi.getTimeRemaining();
		assertTrue(time == time2 + 1);
		// Test game over event works
		ChapsModelImpl cmi2 = (ChapsModelImpl) new ChapsModelFactory().produce();
		GameState gs2 = new GameState(maze, interactables, actors, inv, 1, 10, 0);
		cmi2.setState(gs2);
		String target = "[TIME_UPDATE_REQUIRED, GAME_LOST_TIME_OUT]";
		assertTrue(cmi2.onAction(ChapsAction.TICK).toString().equals(target));

	}

}
