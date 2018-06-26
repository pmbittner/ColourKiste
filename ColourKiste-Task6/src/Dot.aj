import java.util.List;
import java.awt.Color;
import tools.Tool;
import tools.DotTool;

aspect Dot extends ToolPlugin {
	declare precedence: Cut, ChangeColor, Fill, Pencil, Dot;

	after(List<Tool> tools) : PluginLoading(tools) {
        tools.add(new DotTool(Color.BLACK));
	}
}