package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

import tools.Tool;
import tools.ToolUser;
import tools.ToolUser.ToolChangedListener;

public class ToolChooserButton extends JToggleButton implements ActionListener, ToolChangedListener {
	private ToolUser user;
	private Tool tool;
	
	public ToolChooserButton(ToolUser user, Tool tool) {
		super(tool.getName());
		
		this.user = user;
		this.tool = tool;
		
		user.addToolChangedListener(this);
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (this.isSelected())
			user.setTool(tool);
		else
			user.setTool(null);
	}

	@Override
	public void onToolChanged(Tool newTool) {
		if (newTool != tool)
			this.setSelected(false);
	}
}
