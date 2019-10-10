package nz.ac.vuw.ecs.swen225.a3.plugin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.a3.application.MultiFactory;

/**
 * A dialog that allows a developer to select a Maze Object from
 * a list for board addition
 * 
 * @author Claire
 */
public class AddObjectDialog extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = -4836989681986061316L;
	
	private final JComboBox<String> locations = new JComboBox<>();
	
	private final JCheckBox edit = new JCheckBox("Edit?");
	
	private final JButton ok = new JButton("OK");
	private final JButton cancel = new JButton("Cancel");
	
	private boolean selected = false;
	
	/**
	 * @param owner The parent window
	 * @param factory A MultiFactory that can create the desired type
	 * @param type The base type we're creating
	 */
	public AddObjectDialog(Window owner, MultiFactory<?> factory, String type)
	{
		super(owner, "Add a " + type, ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLayout(new BorderLayout());
		ok.addActionListener(this);
		cancel.addActionListener(this);
		
		for(String s : factory.getAvailableTypes())
			locations.addItem(s);
		
		JPanel main = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 2;
		constraints.gridx = constraints.gridy = 0;
		
		main.add(new JLabel("Select the item you want to place from the dropdown:"), constraints);
		
		constraints.gridwidth = 1;
		constraints.gridy++;
		
		constraints.gridx = 0;
		main.add(new JLabel("Location:"), constraints);
		constraints.gridx = 1;
		main.add(locations, constraints);
		constraints.gridy++;
		
		this.add(main, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel(new GridBagLayout());
		constraints = new GridBagConstraints();
		constraints.weightx = 1.0D;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		bottom.add(new JPanel(), constraints);
		constraints.weightx = 0.1D;
		constraints.gridx++;
		bottom.add(edit, constraints);
		constraints.gridx++;
		bottom.add(ok, constraints);
		constraints.gridx++;
		bottom.add(cancel, constraints);
		
		this.add(bottom, BorderLayout.SOUTH);
		
		this.setSize(new Dimension(12 * 40, 4 * 40));
		LevelBuilderFrame.center(this);
	}
	
	/**
	 * @return The user's choice of object type
	 */
	public String getChoice()
	{
		return (String) locations.getSelectedItem();
	}
	
	/**
	 * @return Whether or not the user wants to edit
	 */
	public boolean wantsToEdit()
	{
		return edit.isSelected();
	}
	
	/**
	 * @return Whether or not this dialog has a choice from the user
	 */
	public boolean hasChoice()
	{
		return selected;
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		this.setVisible(false);
		this.dispose();
		
		if(arg0.getSource() == ok) 
			selected = true;
	}

}
