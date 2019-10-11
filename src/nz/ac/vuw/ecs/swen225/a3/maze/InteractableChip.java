package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.swing.Icon;

import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;

/**
 * An interactable representing the chips on the board
 * 
 * @author Claire
 */
public class InteractableChip implements Interactable {
	
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
		return IconFactory.INSTANCE.loadIcon("treasure.png");
	}

	@Override
	public int zIndex() 
	{
		return 1;
	}

	@Override
	public boolean isPushable() 
	{
		return false;
	}

	@Override
	public void onEnter(Actor actor, ModelAccessObject obj) 
	{
		if(actor.isPlayer()) {
			obj.addChips(1);
			obj.removeInteractable(this);
		}
	}

	@Override
	public InteractableChip clone()
	{
		InteractableChip nChip = new InteractableChip();
		nChip.setPosition(this.getPosition().clone());
		return nChip;
	}

	@Override
	public boolean isWalkable(Actor actor, ModelAccessObject obj) 
	{
		return true;
	}

	@Override
	public boolean isSafe(Actor actor, ModelAccessObject obj)
	{
		return true;
	}

}
