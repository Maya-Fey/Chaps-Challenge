package nz.ac.vuw.ecs.swen225.a3.levelbuilder;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import nz.ac.vuw.ecs.swen225.a3.commons.GameConstants;
import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;
import nz.ac.vuw.ecs.swen225.a3.commons.Visible;
import nz.ac.vuw.ecs.swen225.a3.levelbuilder.tmp.ActorPlayer;
import nz.ac.vuw.ecs.swen225.a3.maze.Actor;
import nz.ac.vuw.ecs.swen225.a3.maze.FreeTile;
import nz.ac.vuw.ecs.swen225.a3.maze.Interactable;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;

/**
 * A model representing the level builder. Used for building the level's game state for
 * later exporting.
 * 
 * @author Claire
 */
public class LevelBuilderModel {
	
	private final List<Actor> actors = new ArrayList<>();
	private final List<Interactable> interactables = new ArrayList<>();

	private final List2D<Tile> tiles = new List2D<>(new FreeTile());
	
	@SuppressWarnings("unchecked")
	private final List<Visible>[][] buffer = (List<Visible>[][]) new List<?>[GameConstants.VISIBILE_SIZE][GameConstants.VISIBILE_SIZE];
	
	/**
	 * Constructs a basic model with chap at the center position
	 */
	public LevelBuilderModel()
	{
		for(int i = 0; i < GameConstants.VISIBILE_SIZE; i++)
			for(int j = 0; j < GameConstants.VISIBILE_SIZE; j++)
				buffer[i][j] = new ArrayList<>();
		actors.add(new ActorPlayer());
	}
	
	/**
	 * Produces an array of visibles representing a visible section of the level, marked
	 * by an x and y center coordinate.
	 * 
	 * @param xc
	 * @param yc
	 * @return The array of visibles
	 */
	public Visible[][] render(int xc, int yc)
	{
		Visible[][] arr = new Visible[GameConstants.VISIBILE_SIZE][GameConstants.VISIBILE_SIZE];
		
		int minx = xc - GameConstants.VISIBILE_SIZE / 2;
		int miny = yc - GameConstants.VISIBILE_SIZE / 2;
		int maxx = xc + GameConstants.VISIBILE_SIZE / 2;
		int maxy = yc + GameConstants.VISIBILE_SIZE / 2;
		
		for(int i = 0; i < GameConstants.VISIBILE_SIZE; i++)
			for(int j = 0; j < GameConstants.VISIBILE_SIZE; j++)
				buffer[i][j].clear();
		
		for(Actor actor : actors)
		{
			int x = actor.getPosition().x;
			int y = actor.getPosition().y;
			if(minx <= x && x <= maxx)
				if(miny <= y && y <= maxy)
					buffer[x - minx][y - miny].add(actor);
		}
		
		for(Interactable interactable : interactables)
		{
			int x = interactable.getPosition().x;
			int y = interactable.getPosition().y;
			if(minx <= x && x <= maxx)
				if(miny <= y && y <= maxy)
					buffer[x - minx][y - miny].add(interactable);
		}
		
		for(int x = minx; x <= maxx; x++)
			for(int y = miny; y <= maxy; y++)
				buffer[x - minx][y - miny].add(tiles.get(x, y));
		
		for(int i = 0; i < GameConstants.VISIBILE_SIZE; i++)
			for(int j = 0; j < GameConstants.VISIBILE_SIZE; j++)
				arr[i][GameConstants.VISIBILE_SIZE - j - 1] = new RenderVisible(IconFactory.INSTANCE.composite(buffer[i][j]));
		//             ^ Invert the y-axis
		
		return arr;
	}
	
	private static class RenderVisible implements Visible
	{
		private final Icon icon;
		
		private RenderVisible(Icon icon)
		{
			this.icon = icon;
		}
		
		@Override
		public Icon getIcon() 
		{
			return icon;
		}

		@Override
		public int zIndex() 
		{
			return 0;
		}
		
	}
	
}
