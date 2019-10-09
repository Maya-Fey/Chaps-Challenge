package nz.ac.vuw.ecs.swen225.a3.levelbuilder.tmp;

import javax.swing.Icon;

import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.FreeTile;
import nz.ac.vuw.ecs.swen225.a3.maze.Interactable;
import nz.ac.vuw.ecs.swen225.a3.maze.ModelAccessObject;
import nz.ac.vuw.ecs.swen225.a3.maze.Position;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;

/**
 * A wall tile
 * 
 * @author Claire
 */
public class TileWall implements Tile {
	
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
	public String getName() 
	{
		return null;
	}

	@Override
	public Icon getIcon() 
	{
		return IconFactory.INSTANCE.loadIcon("wallTile.png");
	}

	@Override
	public boolean isFloor() 
	{
		return false;
	}

	@Override
	public boolean isSafe(Actor actor) 
	{
		return true;
	}

	@Override
	public void onEnter(Interactable interactable, ModelAccessObject obj) {}

	@Override
	public void onEnter(Actor actor, ModelAccessObject obj) {}

	@Override
	public FreeTile convertToFreeTile() 
	{
		return null;
	}

	@Override
	public Tile clone() {
		// TODO Auto-generated method stub
		return null;
	}

}
