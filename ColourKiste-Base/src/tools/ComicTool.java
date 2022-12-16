package tools;

import commands.Comicify;
import commands.ICommand;
import commands.SwitchColorCommand;
import rendering.Texture;

public class ComicTool extends ToolAdapter {
    public ComicTool() {
        super("Comic");
    }

    public ICommand<Texture> use(Texture t, int x, int y) {
        return new Comicify();
    }
}
