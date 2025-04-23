package de.bittner.colourkiste.workspace.tools;

import de.bittner.colourkiste.workspace.Workspace;
import de.bittner.colourkiste.workspace.ICommand;
import de.bittner.colourkiste.rendering.Texture;

import java.util.function.Function;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import org.variantsync.functjonal.functions.TriFunction;

public class ClickTool extends ToolAdapter {
    private final TriFunction<Workspace, Integer, Integer, ICommand<Texture>> onClick;
    
    public ClickTool(String name, TriFunction<Workspace, Integer, Integer, ICommand<Texture>> onClick) {
        super(name);
        this.onClick = onClick;
    }

    public ICommand<Texture> use(Texture t, int x, int y) {
        return onClick.apply(getWorkspace(), x, y);
    }
}
