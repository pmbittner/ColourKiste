package de.bittner.colourkiste.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

import de.bittner.colourkiste.workspace.tools.Tool;
import de.bittner.colourkiste.workspace.tools.ToolBox;

public class ToolChooserButton extends JToggleButton implements ActionListener {
	private final ToolBox user;
	private final Tool tool;
	
	public ToolChooserButton(ToolBox user, Tool tool) {
		super(tool.getName());
		
		this.user = user;
		this.tool = tool;
		
		user.OnToolChanged.addListener(this::onToolChanged);
		this.addActionListener(this);
	}
	
	private void updateSelectionStatus() {
		if (this.isSelected() && user.getTool() != tool) {
			user.setTool(tool);
		}
	}
	
	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
		updateSelectionStatus();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		updateSelectionStatus();
	}

	private void onToolChanged(Tool newTool) {
		if (newTool != tool)
			this.setSelected(false);
	}
}
