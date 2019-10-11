package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.swing.Icon;

import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;

/**
 * The exit lock
 * 
 * @author Claire
 */
public class InteractableExitLock implements Interactable {
	
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
		return IconFactory.INSTANCE.loadIcon("exitLock.png");
	}

	@Override
	public int zIndex() 
	{
		return -1; //Always show on top;
	}

	@Override
	public boolean isPushable() 
	{
		return false;
	}

	@Override
	public boolean isWalkable(Actor actor, ModelAccessObject obj) 
	{
		return obj.hasAllChips();
	}

	@Override
	public boolean isSafe(Actor actor, ModelAccessObject obj) 
	{
		return true;
	}

	@Override
	public void onEnter(Actor actor, ModelAccessObject obj) 
	{
		obj.removeInteractable(this);
	}

	@Override
	public Interactable clone() 
	{
		InteractableExitLock item = new InteractableExitLock();
		item.setPosition(this.getPosition());
		return item;
	}

}
