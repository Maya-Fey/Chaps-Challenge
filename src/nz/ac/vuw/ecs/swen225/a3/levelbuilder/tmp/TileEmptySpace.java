package nz.ac.vuw.ecs.swen225.a3.levelbuilder.tmp;

import javax.json.JsonObject;
import javax.swing.Icon;

import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;
import nz.ac.vuw.ecs.swen225.a3.maze.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.FreeTile;
import nz.ac.vuw.ecs.swen225.a3.maze.Interactable;
import nz.ac.vuw.ecs.swen225.a3.maze.ModelAccessObject;
import nz.ac.vuw.ecs.swen225.a3.maze.Position;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;

/**
 * An empty space tile, possibly temporary
 * 
 * @author Claire
 */
public class TileEmptySpace implements Tile {

	@Override
	public Position getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPosition(Position position) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public JsonObject persist() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public Icon getIcon() 
	{
		return IconFactory.INSTANCE.loadIcon("freeTile.png");
	}

	@Override
	public boolean isFloor() 
	{
		return true;
	}

	@Override
	public boolean isSafe(Actor actor) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void onEnter(Interactable interactable, ModelAccessObject obj) {}
	public void onEnter(Actor actor, ModelAccessObject obj) {}

	@Override
	public FreeTile convertToFreeTile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tile clone() {
		// TODO Auto-generated method stub
		return null;
	}

}
