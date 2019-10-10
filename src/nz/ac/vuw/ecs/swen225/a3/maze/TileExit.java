package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.swing.Icon;

import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;

/**
 * The exit for a level
 * @author Claire
 */
public class TileExit implements Tile {

	private Position position;

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
		return IconFactory.INSTANCE.loadIcon("exit.png");
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
	public TileExit clone() 
	{
		TileExit tile = new TileExit();
		tile.setPosition(this.position.clone());
		return tile;
	}

}
