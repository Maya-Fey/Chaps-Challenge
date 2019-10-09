package nz.ac.vuw.ecs.swen225.a3.levelbuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * A 2D list. Used to represent an expanding 2D plane.
 * 
 * @author Claire
 * 
 * @param <Type> The type this list stores
 */
public class List2D<Type> {
	
	private final Type NULL_VALUE;
	
	private List<List<Type>> arr = new ArrayList<>();
	
	private int minX, minY;
	
	private int sizeX, sizeY;
	
	/**
	 * Constructs a 1x1 plane
	 * 
	 * @param default_ The default value to be used when expanding the list.
	 */
	public List2D(Type default_)
	{
		this.NULL_VALUE = default_;
		this.minX = this.minY;
		arr.add(new ArrayList<>());
		arr.get(0).add(NULL_VALUE);
		this.sizeX = this.sizeY = 1;
	}
	
	/**
	 * Gets a value at that position on the plane.
	 * 
	 * @param x
	 * @param y
	 * @return The value at that coordinate, or the specified default value if it's out of bounds
	 */
	public Type get(int x, int y)
	{
		x -= minX;
		y -= minY;
		
		if(x < 0 || y < 0 || x >= sizeX || y >= sizeY) {
			return NULL_VALUE;
		}
		
		return arr.get(x).get(y);
	}
	
	/**
	 * Puts the value at the specified position
	 * 
	 * @param value The value to write
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public void set(Type value, int x, int y)
	{
		ensureMinX(x);
		ensureMinY(y);
		ensureMaxX(x);
		ensureMaxY(y);
		
		arr.get(x - minX).set(y - minY, value);
	}
	
	/**
	 * Ensures the minimum x is a certain value
	 * 
	 * @param x the needed x
	 */
	protected void ensureMinX(int x)
	{
		if(x >= minX)
			return;
		
		//The amount of columns we need to insert
		int diff = minX - x;
		
		for(int i = 0; i < diff; i++)
			arr.add(0, genCol());
		
		minX = x;
		sizeX += diff;
	}
	
	/**
	 * Ensures the minimum y is a certain value
	 * 
	 * @param y the needed y
	 */
	protected void ensureMinY(int y)
	{
		if(y >= minY)
			return;
		
		//The amount of rows we need to insert
		int diff = minY - y;
		
		for(int x = 0; x < sizeX; x++)
			for(int i = 0; i < diff; i++)
				arr.get(x).add(0, NULL_VALUE);
		
		minY = y;
		sizeY += diff;
	}
	
	/**
	 * Ensures the maximum x is a certain value
	 * 
	 * @param x the needed x
	 */
	protected void ensureMaxX(int x)
	{
		if(x <= maxX())
			return;
		
		//The amount of columns we need to insert
		int diff = x - maxX();
		
		for(int i = 0; i < diff; i++)
			arr.add(genCol());
		
		sizeX += diff;
	}
	
	/**
	 * Ensures the maximum y is a certain value
	 * 
	 * @param y the needed y
	 */
	protected void ensureMaxY(int y)
	{
		if(y <= maxY())
			return;
		
		//The amount of rows we need to insert
		int diff = y - maxY();
		
		for(int x = 0; x < sizeX; x++)
			for(int i = 0; i < diff; i++)
				arr.get(x).add(NULL_VALUE);
		
		sizeY += diff;
	}
	
	/**
	 * @return The max X, inclusive!
	 */
	private int maxX()
	{
		return minX + sizeX - 1;
	}
	
	/**
	 * @return The max Y, inclusive!
	 */
	private int maxY()
	{
		return minY + sizeY - 1;
	}
	
	private List<Type> genCol()
	{
		List<Type> col = new ArrayList<>();
		for(int i = 0; i < sizeY; i++)
			col.add(NULL_VALUE);
		return col;
	}

}
