package de.bittner.colourkiste.tools;

import de.bittner.colourkiste.commands.ICommand;
import de.bittner.colourkiste.commands.PipetteCommand;
import de.bittner.colourkiste.gui.MainFrame;
import de.bittner.colourkiste.rendering.Texture;

public class Pipette extends ToolAdapter {
    public Pipette() {
        super("Pipette");
    }

    public ICommand<Texture> use(Texture t, int x, int y) {
        final MainFrame main = getWorkspace().getMainFrame();
        return new PipetteCommand(x, y,
                main::setColor
//               , this::getActiveColor // uncomment this to have undo for pipette
        );
    }
}
