package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.Json;
import javax.json.JsonObject;

import nz.ac.vuw.ecs.swen225.a3.commons.Persistable;

/**
 * A 2D position representing somewhere in the maze
 * 
 * @author Claire 300436297
 */
public class Position implements Persistable {

	/**
	 * X-Coordinate. Width-wise
	 */
	public final int x;
	
	/**
	 * Y-Coordinate. Height-wise
	 */
	public final int y;
	
	/**
	 * @param x
	 * @param y
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public JsonObject persist() 
	{
		return Json.createObjectBuilder().add("x", x).add("y", y).build();
	}

	@Override
	public String getName() 
	{
		return "position";
	}
	
	/**
	 * Translates a position one space from its current position.
	 * 
	 * @param direction
	 * @return A new position that's one space away in the specified direction
	 */
	public Position translate(Direction direction)
	{
		switch(direction)
		{
			case EAST:
				return new Position(x + 1, y    );
			case NORTH:
				return new Position(x    , y + 1);
			case SOUTH:
				return new Position(x    , y - 1);
			case WEST:
				return new Position(x - 1, y    );
			default:
				throw new AssertionError("Invalid Position enum or unimplemented case statement.");
		}
	}
	
	/**
	 * Translates a position one space from its current position.
	 * Using a direction from chaps action rather than NSEW
	 * 
	 * @param direction
	 * @return A new position that's one space away in the specified direction
	 */
	public Position translate(ChapsAction direction)
	{
		switch(direction)
		{
			case RIGHT:
				return new Position(x + 1, y    );
			case UP:
				return new Position(x    , y + 1);
			case DOWN:
				return new Position(x    , y - 1);
			case LEFT:
				return new Position(x - 1, y    );
			default:
				throw new AssertionError("Invalid Position enum or unimplemented case statement.");
		}
	}
	
	/**
	 * A cardinal direction on a 2D plane
	 * 
	 * @author Claire 300436297
	 */
	public enum Direction
	{
		/**
		 * North. X+0, Y+1
		 */
		NORTH,
		
		/**
		 * South. X+0, Y-1
		 */
		SOUTH,
		
		/**
		 * East. X+1, Y+0
		 */
		EAST,
		
		/**
		 * West. X-1, Y+0
		 */
		WEST
	}
	
}
