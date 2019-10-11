package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.maze.ChapsModelFactory;
import nz.ac.vuw.ecs.swen225.a3.render.ChapsViewFactory;

/**
 * The main class. Used for starting the game.
 * 
 * @author Claire 300436297
 */
public class Main {

	/**
	 * main.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ChapsControllerFactory factory = new ChapsControllerFactory();
		ChapsModelFactory modelFactory = new ChapsModelFactory();
		ChapsViewFactory viewFactory = new ChapsViewFactory();
		factory.produce(modelFactory, viewFactory);
	}

}
