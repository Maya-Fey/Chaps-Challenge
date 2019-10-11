package nz.ac.vuw.ecs.swen225.a3.testing;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.commons.IconFactory;
import nz.ac.vuw.ecs.swen225.a3.commons.Visible;

/**
 * JUnit testing
 * 
 * @author ferguscurrie
 *
 */
class TestingIconFactory {

	// make sure freeTile loads
	/**
	 * Testing icon load works
	 */
	@Test
	void test_icon_load() {
		try {
			IconFactory.INSTANCE.loadIcon("dirt.png");
		} catch (Error e) {
			assert (false);
		}
		assert (true);

	}

	/**
	 * Testing cache works , make sure 2 loads are reference equals ==
	 */
	@Test
	void test_cache() {

		ImageIcon i = (ImageIcon) IconFactory.INSTANCE.loadIcon("dirt.png");
		ImageIcon j = (ImageIcon) IconFactory.INSTANCE.loadIcon("dirt.png");

		assert (i == j);

	}

	/**
	 * Testing composite icon, works correctly.
	 */
	@Test
	void test_composite() {

		// First image = red square
		BufferedImage bui = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
		Graphics2D fg = bui.createGraphics();
		fg.setColor(Color.red);
		fg.drawRect(0, 0, 2, 2);
		fg.dispose();
		ImageIcon ri = new ImageIcon(bui);

		// Second image = blue square
		BufferedImage bii = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gg = bii.createGraphics();
		gg.setColor(Color.red);
		gg.drawRect(0, 0, 2, 2);
		gg.dispose();
		ImageIcon bi = new ImageIcon(bii);

		// Making composite call
		ArrayList<ImageIcon> imgicons = new ArrayList<ImageIcon>();
		imgicons.add(bi);
		imgicons.add(ri);
		List<Visible> visibles = new ArrayList<Visible>();
		//adding ri, lower z value 
		visibles.add(new Visible() {

			@Override
			public Icon getIcon() {
				return ri;
			}

			@Override
			public int zIndex() {
				return 0;
			}

		});
		//adding bi
		visibles.add(new Visible() {

			@Override
			public Icon getIcon() {
				return bi;
			}

			@Override
			public int zIndex() {
				return 1;
			}

		});
		ImageIcon comi = (ImageIcon) IconFactory.INSTANCE.composite(visibles);
		
		//Convert combind to bufferedimage 
		BufferedImage bufCom = new BufferedImage(comi.getIconWidth(),comi.getIconHeight(),BufferedImage.TYPE_INT_RGB);
		Graphics2D bg = bufCom.createGraphics();
		comi.paintIcon(null, bg, 0, 0);
		bg.dispose();
		
		//Get color value 
		int col = bufCom.getRGB(0,0);
		int r = (col >> 16) & 0xFF; 
		int g = (col >> 8) & 0xFF; 
		int b = col & 0xFF; 
		
		//Check its correct, red square is drawn with lower z value than blue square so color should be red
		//	r = 255 , b = 0 , g = 0
		assertTrue(r == 255);
		assertTrue(g == 0);
		assertTrue(b == 0);

	}

}
