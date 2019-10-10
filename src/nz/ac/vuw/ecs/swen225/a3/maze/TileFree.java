package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.swing.Icon;

import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;

/**
 * Class to represent an empty or free tile
 * @author James
 *
 */
public class TileFree implements Tile {
	
	private Position position;
	
	/**
	 * Blank constructor for newInstance
	 */
	public TileFree() {}
	
	/**
	 * @param pos
	 */
	public TileFree(Position pos)
	{
		this.position = pos;
	}
	
	public TileFree clone() 
	{
		TileFree tile = new TileFree();
		tile.setPosition(this.position.clone());
		return tile;
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
	public Icon getIcon() {
		return IconFactory.INSTANCE.loadIcon("freeTile.png");
	}

	@Override
	public boolean isFloor() 
	{
		return true;
	}

	@Override
	public boolean isSafe(Actor actor, ModelAccessObject obj) 
	{
		return true;
	}

	@Override
	public void onEnter(Interactable interactable, ModelAccessObject obj) {}

	@Override
	public void onEnter(Actor actor, ModelAccessObject obj) {}

	@Override
	public TileFree convertToFreeTile() {
		return null;
	}


}
