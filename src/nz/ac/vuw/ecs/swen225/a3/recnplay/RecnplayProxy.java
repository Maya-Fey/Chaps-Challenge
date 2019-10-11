package nz.ac.vuw.ecs.swen225.a3.recnplay;

import java.util.Collection;
import java.util.EnumSet;

import nz.ac.vuw.ecs.swen225.a3.application.GameState;
import nz.ac.vuw.ecs.swen225.a3.commons.Visible;
import nz.ac.vuw.ecs.swen225.a3.maze.ChapsAction;
import nz.ac.vuw.ecs.swen225.a3.maze.ChapsEvent;
import nz.ac.vuw.ecs.swen225.a3.maze.ChapsModel;

/**
 * A proxy for ChapsModel. Passes all events along as normal, except it
 * records all events.
 * 
 * @author Claire
 */
public class RecnplayProxy implements ChapsModel {
	
	private ChapsModel actual;
	
	private RecordedGame game;

	/**
	 * @param actual The model you want this to be a proxy to.
	 */
	public RecnplayProxy(ChapsModel actual) 
	{
		this.actual = actual;
	}
	
	/**
	 * Starts recording
	 */
	public void startRecording()
	{
		this.game = new RecordedGame(actual.getState());
	}
	
	/**
	 * @return The recorded game
	 */
	public RecordedGame stopRecording()
	{
		try {
			return game;
		} finally {
			game = null;
		}
	}
	
	/**
	 * @return Whether we're recording or not
	 */
	public boolean isRecording()
	{
		return this.game != null;
	}

	@Override
	public EnumSet<ChapsEvent> onAction(ChapsAction action) 
	{
		if(game != null)
			game.addAction(action);
		return actual.onAction(action);
	}

	@Override
	public GameState getState() 
	{
		return actual.getState();
	}

	@Override
	public void setState(GameState state) 
	{
		actual.setState(state);
	}

	@Override
	public Visible[][] getVisibleArea() 
	{
		return actual.getVisibleArea();
	}

	@Override
	public Collection<Visible> getInventoryIcons() 
	{
		return actual.getInventoryIcons();
	}

	@Override
	public int getTimeRemaining() 
	{
		return actual.getTimeRemaining();
	}

	@Override
	public int getChipsRemaining() 
	{
		return actual.getChipsRemaining();
	}

	@Override
	public String getTutorialMessage() 
	{
		return actual.getTutorialMessage();
	}

}
