package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.swing.Icon;

import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;

/**
 * Class for the players character
 * @author James
 *
 */
public class ActorPlayer implements Actor {
	
	private Position position;
	
	/**
	 * Main constructor for a new player
	 */
	public ActorPlayer() {}
	
	/**
	 * Method to Clone player class
	 */
	public ActorPlayer clone() 
	{
		ActorPlayer player = new ActorPlayer();
		player.setPosition(this.position.clone());
		return player;
	}

	@Override
	public Position getPosition() 
	{
		return position;
	}

	@Override
	public void setPosition(Position position) 
	{
		this.position = position;
		
	}

	@Override
	public Icon getIcon() 
	{
		return IconFactory.INSTANCE.loadIcon("chap.png");
	}

	@Override
	public void tick(ModelAccessObject obj) {}

	@Override
	public boolean isPlayer() 
	{
		return true;
	}

	@Override
	public void onCollide(Actor actor, ModelAccessObject obj) 
	{
		//TODO not sure what do do here
	}

}
