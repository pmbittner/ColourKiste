import java.util.List;
import java.awt.Color;
import tools.Tool;
import tools.FillTool;

aspect Fill extends ToolPlugin {
	declare precedence: Cut, ChangeColor, Fill;

	after(List<Tool> tools) : PluginLoading(tools) {
        tools.add(new FillTool(Color.ORANGE));
	}
}