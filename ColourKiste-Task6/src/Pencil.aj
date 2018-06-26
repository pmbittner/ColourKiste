import java.util.List;
import java.awt.Color;
import tools.Tool;
import tools.DotTool;
import tools.PencilTool;

aspect Pencil extends ToolPlugin {
	declare precedence: Cut, ChangeColor, Fill, Pencil;

	after(List<Tool> tools) : PluginLoading(tools) {
        tools.add(new PencilTool(Color.BLACK));
	}
}