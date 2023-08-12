package tools;

import commands.ICommand;
import commands.PipetteCommand;
import gui.MainFrame;
import rendering.Texture;

public class Pipette extends ToolAdapter {
    public Pipette() {
        super("Pipette");
    }

    public ICommand<Texture> use(Texture t, int x, int y) {
        final MainFrame main = getImagePanel().getMainFrame();
        return new PipetteCommand(x, y,
                main::setColor
//               , this::getActiveColor // uncomment this to have undo for pipette
        );
    }
}
