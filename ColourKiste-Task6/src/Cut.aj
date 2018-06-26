import java.util.List;
import java.awt.Color;
import tools.Tool;
import tools.AreaSelectionTool;
import tools.DotTool;
import tools.PencilTool;

aspect Cut extends ToolPlugin {
	after(List<Tool> tools) : PluginLoading(tools) {
        tools.add(new AreaSelectionTool());
	}
}