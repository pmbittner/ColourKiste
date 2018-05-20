package tools;
import commands.ICommand;
import commands.SwitchColorCommand;
import rendering.Texture;

public class ColorDeleteTool extends ToolAdapter
{
    public ICommand<Texture> use(Texture t, int x, int y) {
        return new SwitchColorCommand(t.getColorAt(x, y), Texture.clr_TRANSPARENT);
    }
}