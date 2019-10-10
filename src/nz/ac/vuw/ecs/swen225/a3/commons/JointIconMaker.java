package nz.ac.vuw.ecs.swen225.a3.commons;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * Class to combine multiple icons that are on top of each other
 * Usage : construct new JointIconMaker then call getIcon() 
 * 
 *  * 
 * Could be an issue with icon's having correct transparency 
 * 
 * @author ferguscurrie
 * 
 *
 */
public class JointIconMaker {
	
	private ImageIcon combindIcon = null;
	
	/**
	 * 
	 * constructor for JointIconMake, sets icon to the joint image
	 * The li parameter should be order with bottom icon first. 
	 * @param li
	 */
	public JointIconMaker(ArrayList<ImageIcon> li) {
		//Create resulting image
		BufferedImage result = new BufferedImage(li.get(0).getIconWidth(),li.get(0).getIconHeight(),BufferedImage.TYPE_INT_ARGB);
		//Convert ImageIcons to BufferedImages
		ArrayList<BufferedImage> bufLi = new ArrayList<BufferedImage>();
		for(ImageIcon ii : li) {
			bufLi.add((BufferedImage)(ii.getImage()));
		}
		//Draw from bottom to top
		Graphics2D g = result.createGraphics();
	
		for(BufferedImage bi : bufLi) {	
			g.drawImage(bi,0,0,null);
		}
		
		g.dispose();
		//Create ImageIcon from result, setting combindIcon to it. 
		combindIcon = new ImageIcon(result);
	}
	
	/**
	 * method to get the combine icon 
	 * @return ImageIcon
	 */
	public ImageIcon getIcon() {
		if(combindIcon == null) {
			return null;
		}
		return combindIcon;	
	}
	
}
