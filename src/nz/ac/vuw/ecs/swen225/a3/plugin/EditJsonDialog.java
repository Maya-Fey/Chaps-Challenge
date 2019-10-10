package nz.ac.vuw.ecs.swen225.a3.plugin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

/**
 * A dialog for editing JSON objects.
 * 
 * @author Claire
 */
public class EditJsonDialog extends JDialog implements ActionListener, DocumentListener {
	
	private static final long serialVersionUID = 3631218886532303835L;
	
	private final Map<JButton, String> subObjects = new HashMap<>();
	private final Map<JCheckBox, String> boolValues = new HashMap<>();
	private final Map<Document, JTextField> allDocs = new HashMap<>();
	private final Map<JTextField, String> stringValues = new HashMap<>();
	private final Map<JTextField, String> intValues = new HashMap<>();
	private final Map<String, JsonValue> allValues = new HashMap<>();
	
	private final JButton ok = new JButton("OK");
	private final JButton cancel = new JButton("Cancel");
	
	private boolean edited = false;
	
	/**
	 * Construct an editor fot the selected JSON object
	 * @param owner
	 * @param obj
	 * @param ignoreCoords Whether we want to make coordinates (x, y) editable or not
	 */
	public EditJsonDialog(Window owner, JsonObject obj, boolean ignoreCoords)
	{
		super(owner, "Edit JSON object", ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLayout(new BorderLayout());
		ok.addActionListener(this);
		cancel.addActionListener(this);
		
		JPanel main = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 2;
		constraints.gridx = constraints.gridy = 0;
		
		main.add(new JLabel("Edit the raw object information below:"), constraints);
		
		constraints.gridwidth = 1;
		constraints.gridy++;
		
		for(Entry<String, JsonValue> entry : obj.entrySet())
		{			
			allValues.put(entry.getKey(), entry.getValue());
			
			if(ignoreCoords)
				if(entry.getKey().equals("name") || entry.getKey().equals("x") || entry.getKey().equals("y"))
					continue;
			
			constraints.gridx = 0;
			main.add(new JLabel(entry.getKey() + ": "), constraints);
			constraints.gridx = 1;
			
			boolean boolValue = false;
			
			switch(entry.getValue().getValueType())
			{
				case OBJECT:
					JButton edit = new JButton("Edit JSON Object");
					edit.addActionListener(this);
					subObjects.put(edit, entry.getKey());
					main.add(edit, constraints);	
					break;
				case NUMBER:
					JTextField field = new JTextField("");
					field.setText("" + ((JsonNumber) entry.getValue()).intValue());
					allDocs.put(field.getDocument(), field);
					field.getDocument().addDocumentListener(this);
					intValues.put(field, entry.getKey());
					main.add(field, constraints);
					break;
				case STRING:
					field = new JTextField("");
					field.setText(((JsonString) entry.getValue()).getString());
					allDocs.put(field.getDocument(), field);
					field.getDocument().addDocumentListener(this);
					stringValues.put(field, entry.getKey());
					main.add(field, constraints);
					break;
				case TRUE:
					boolValue = true;
					//Fall through
				case FALSE:
					JCheckBox checkbox = new JCheckBox();
					checkbox.addActionListener(this);
					checkbox.setSelected(boolValue);
					boolValues.put(checkbox, entry.getKey());
					main.add(checkbox, constraints);
					break;
				case NULL:
					main.add(new JLabel("<Null Value>"), constraints);
					break;
				case ARRAY:
					main.add(new JLabel("<Array of " + entry.getValue().asJsonArray().getValueType().toString() + ">"), constraints);
					break;
				default:
					main.add(new JLabel("<Unable to read data>"), constraints);
					break;
			}
			
			constraints.gridy++;
		}
		
		this.add(main, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel(new GridBagLayout());
		constraints = new GridBagConstraints();
		constraints.weightx = 1.0D;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		bottom.add(new JPanel(), constraints);
		constraints.weightx = 0.1D;
		constraints.gridx++;
		bottom.add(ok, constraints);
		constraints.gridx++;
		bottom.add(cancel, constraints);
		
		this.add(bottom, BorderLayout.SOUTH);
		
		this.setSize(new Dimension(12 * 40, 4 * 40));
		LevelBuilderFrame.center(this);
	}
	
	/**
	 * @return If the user has decided they want their edits or not
	 */
	public boolean hasEdit()
	{
		return this.edited;
	}
	
	/**
	 * @return The edited version of this object
	 */
	public JsonObject edited()
	{
		JsonObjectBuilder builder = Json.createObjectBuilder();
		
		for(Entry<String, JsonValue> entry : allValues.entrySet())
			builder = builder.add(entry.getKey(), entry.getValue());
		
		return builder.build();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource() == cancel) 
		{
			this.setVisible(false);
			this.dispose();
		} else if(arg0.getSource() == ok) {
			edited = true;
			this.setVisible(false);
			this.dispose();
		} else if(subObjects.containsKey(arg0.getSource())) {
			JsonObject toEdit = (JsonObject) allValues.get(subObjects.get(arg0.getSource()));
			EditJsonDialog dialog = new EditJsonDialog(this, toEdit, false);
			dialog.setVisible(true);
			if(dialog.hasEdit())
			{
				JsonObject newObj = dialog.edited();
				allValues.put(subObjects.get(arg0.getSource()), newObj);
			}
		} else if(boolValues.containsKey(arg0.getSource())) {
			allValues.put(boolValues.get(arg0.getSource()), ((JCheckBox) arg0.getSource()).isSelected() ? JsonValue.TRUE : JsonValue.FALSE);
		}
	}
	
	/*
	 * If one of the text fields is updated, find it using the document map,
	 * extract the text, and update the relevant value
	 */
	private void onText(DocumentEvent event)
	{
		JTextField origin = allDocs.get(event.getDocument());
		if(stringValues.containsKey(origin))
		{
			String key = stringValues.get(origin);
			allValues.put(key, Json.createValue(origin.getText()));
		} else if(intValues.containsKey(origin)) 
		{
			String key = intValues.get(origin);
			try {
				int i = Integer.parseInt(origin.getText());
				allValues.put(key, Json.createValue(i));
			} catch(NumberFormatException e) {}
		}
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		onText(arg0);
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		onText(arg0);
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		onText(arg0);
	}

	/**
	 * @param obj The object we're trying to edit
	 * @return Whether it has any fields or not
	 */
	public static boolean canEdit(JsonObject obj)
	{
		for(Entry<String, JsonValue> entry : obj.entrySet()) {
			if(entry.getKey().equals("name") || entry.getKey().equals("x") || entry.getKey().equals("y"))
				continue;
			return true;
		}
		return false;
	}

}
