package nz.ac.vuw.ecs.swen225.a3.testing;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.application.GameState;
import nz.ac.vuw.ecs.swen225.a3.maze.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.ActorPlayer;
import nz.ac.vuw.ecs.swen225.a3.maze.ChapsModelFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.ChapsModelImpl;
import nz.ac.vuw.ecs.swen225.a3.maze.Interactable;
import nz.ac.vuw.ecs.swen225.a3.maze.InteractableChip;
import nz.ac.vuw.ecs.swen225.a3.maze.Inventory;
import nz.ac.vuw.ecs.swen225.a3.maze.Position;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
import nz.ac.vuw.ecs.swen225.a3.persistence.JsonFileInterface;

/**
 * 
 * JUnit test file for testing the persistance package
 * 
 * @author ferguscurrie
 *
 */
class TestingPersistance {

	/**
	 * @throws IOException
	 */
	@Test
	/**
	 * Tests the ability for a game to be saved and loaded 
	 */
	void test_save_load() throws IOException {
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
		GameState gs = new GameState(maze, interactables, actors, inv, 10, 10);
		// Set the state
		cmi.setState(gs);
		//Record JSon
		String jsn = gs.persist().toString();
		//Save
		File f = File.createTempFile("test",".json");
		JsonFileInterface.saveToFile(gs.persist(),f);
		//Load 
		assertTrue(JsonFileInterface.loadFromFile(f).toString().equals(jsn));
		f.deleteOnExit();
	}
	

}
