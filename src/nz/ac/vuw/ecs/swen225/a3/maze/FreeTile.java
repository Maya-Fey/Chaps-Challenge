package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.swing.Icon;

import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;

/**
 * Class to represent an empty or free tile
 * @author James
 *
 */
public class FreeTile implements Tile {
	
	private Position position;
	
	/**
	 * Blank constructor for newInstance
	 */
	public FreeTile() {}
	
	/**
	 * @param pos
	 */
	public FreeTile(Position pos)
	{
		this.position = pos;
	}
	
	public FreeTile clone() 
	{
		FreeTile tile = new FreeTile();
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
	public String getName() {
		return "FreeTile";
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
	public boolean isSafe(Actor actor) 
	{
		return true;
	}

	@Override
	public void onEnter(Interactable interactable, ModelAccessObject obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnter(Actor actor, ModelAccessObject obj) {
		// TODO Auto-generated method stub
		//TODO

	}

	@Override
	public FreeTile convertToFreeTile() {
		return null;
	}

//	/**
//	 * Method is run when a player is on this tile to convert this tile to a free tile
//	 * @return new free tile
//	 */
//	public FreeTile convertToFreeTile() {
//		return new FreeTile(this.position, icon);
//	}


}
