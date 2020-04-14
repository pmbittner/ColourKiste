package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tools.Tool;
import tools.ToolBox;
import tools.ToolBox.ToolChangedListener;

public class ToolChooserButton extends JToggleButton implements ActionListener, ToolChangedListener {
	private ToolBox user;
	private Tool tool;
	
	public ToolChooserButton(ToolBox user, Tool tool) {
		super(tool.getName());
		
		this.user = user;
		this.tool = tool;
		
		user.addToolChangedListener(this);
		this.addActionListener(this);
	}
	
	private void updateSelectionStatus() {
		if (this.isSelected() && user.getTool() != this) {
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

	@Override
	public void onToolChanged(Tool newTool) {
		if (newTool != tool)
			this.setSelected(false);
	}
}
