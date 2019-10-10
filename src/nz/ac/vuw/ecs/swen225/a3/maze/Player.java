package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.swing.Icon;

import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;

/**
 * Class for the players character
 * @author James
 *
 */
public class Player implements Actor {
	
	private Position position;
	
	/**
	 * Main constructor for a new player
	 */
	public Player() {}
	
	/**
	 * Method to Clone player class
	 */
	public Player clone() 
	{
		Player player = new Player();
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
	public String getName() {
		return null;
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
