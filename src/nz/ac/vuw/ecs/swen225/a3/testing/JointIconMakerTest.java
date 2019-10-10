package nz.ac.vuw.ecs.swen225.a3.testing;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.a3.commons.JointIconMaker;

/**
 * @author ferguscurrie
 *	Test case for checking joint icon maker works 
 */
class JointIconMakerTest {

	/**
	 * Tests the icon
	 */
	@Test
	void test_icon() {
		
		//Making icons
		int width = 2;
		int height = 2;
		//Red Sqaure 
		BufferedImage bf1 = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bf1.createGraphics();
			//Create transparent background 
		g.setComposite(AlphaComposite.Clear);
		g.fillRect(0, 0, width, height);
			//Create sqaure
		g.setComposite(AlphaComposite.Src);
		g.setColor(Color.red);
		g.fillRect(0, 0, width, height);
		g.dispose();
		ImageIcon img = new ImageIcon(bf1);
		//Green small sqaure
		BufferedImage bf2 = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = bf2.createGraphics();
			//Create transparent background 
		g.setComposite(AlphaComposite.Clear);
		g.fillRect(0, 0, width, height);
			//Create sqaure
		g.setComposite(AlphaComposite.Src);
		g2.setColor(Color.green);
		g2.fillRect(0, 0, width/2, height/2);
		g2.dispose();
		ImageIcon img2 = new ImageIcon(bf2);
		//Add to array list
		ArrayList<ImageIcon> al = new ArrayList<ImageIcon>();
		al.add(img);
		al.add(img2);
		//Merge them
		ImageIcon merged = new JointIconMaker(al).getIcon();
		//Now test the merged icon by checking the correct pixel value is at each point  
		BufferedImage bi = new BufferedImage(merged.getIconWidth(),merged.getIconHeight(),BufferedImage.TYPE_INT_RGB);
			//TOP LEFT
		int rgb = bi.getRGB(0, 0);
		System.out.println(rgb);
		int red = (rgb & 0x00ff0000) >> 16;
		int green = (rgb & 0x0000ff00) >> 8;
		int blue = (rgb & 0x000000ff);
		System.out.println(red+" "+green+" "+blue);
			//TOP RIGHT
		rgb = bi.getRGB(0, 1);
		System.out.println(rgb);
			//BOTTOM LEFT
		
			//BOTTOM RIGHT
		fail();
		
	}

}
