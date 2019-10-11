package nz.ac.vuw.ecs.swen225.a3.testing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.maze.InteractableItem;
import nz.ac.vuw.ecs.swen225.a3.maze.TileFree;
import nz.ac.vuw.ecs.swen225.a3.plugin.EditJsonDialog;
import nz.ac.vuw.ecs.swen225.a3.plugin.LevelBuilderFrame;

/**
 * @author Claire
 */
public class PluginTests {
	
	/**
	 * 
	 */
	@Test
	void testFrameInit()
	{
		LevelBuilderFrame frame = new LevelBuilderFrame();
		frame.setVisible(false);
		frame.dispose();
		assertTrue(true);
	}
	
	/**
	 * 
	 */
	@Test
	void testDialogInit()
	{
		new EditJsonDialog(null, new TileFree().persist(), false);
	}
	
	/**
	 * 
	 */
	@Test
	void testNothingEditable()
	{
		assertFalse(EditJsonDialog.canEdit(new TileFree().persist()));
	}

	/**
	 * 
	 */
	@Test
	void testSomethingEditable()
	{
		assertTrue(EditJsonDialog.canEdit(new InteractableItem().persist()));
	}

	
}
