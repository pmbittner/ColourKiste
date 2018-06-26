import java.util.List;

import tools.Tool;

abstract aspect ToolPlugin {
	pointcut PluginLoading(List<Tool> tools) : execution(private static void Main.createTools(List<Tool>)) && args(tools);
}