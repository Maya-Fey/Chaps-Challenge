package nz.ac.vuw.ecs.swen225.a3.levelbuilder.tmp;

import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.Player;
import nz.ac.vuw.ecs.swen225.a3.maze.Position;

/**
 * A temporary extension of player with a default constructor
 * 
 * @author Claire
 */
public class ActorPlayer extends Player {

	/**
	 * Construct a player at 0,0;
	 */
	public ActorPlayer()
	{
		super(new Position(0,0), "", IconFactory.INSTANCE.loadIcon("chap.png"));
	}
	
}
