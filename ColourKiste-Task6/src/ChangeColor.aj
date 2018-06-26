import java.util.List;

import tools.ColorSwitchTool;
import tools.Tool;

aspect ChangeColor extends ToolPlugin {
	declare precedence: Cut, ChangeColor;

	after(List<Tool> tools) : PluginLoading(tools) {
        tools.add(new ColorSwitchTool());
	}
}