package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.JsonObject;

import nz.ac.vuw.ecs.swen225.a3.commons.ChapsFactory;

/**
 * @author Claire
 */
public class TileMessageFactory implements ChapsFactory<TileMessage> {

	@Override
	public TileMessage resurrect(JsonObject obj) 
	{
		Position pos = Position.resurrect(obj);
		String message = obj.getString("message");
		TileMessage res = new TileMessage(message);
		res.setPosition(pos);
		return res;
	}

	@Override
	public TileMessage newInstance() 
	{
		return new TileMessage();
	}

}
