package tools;

import commands.ICommand;
import rendering.Texture;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ClickTool extends ToolAdapter {
    private final BiFunction<Integer, Integer, ICommand<Texture>> onClick;
    
    public ClickTool(String name, BiFunction<Integer, Integer, ICommand<Texture>> onClick) {
        super(name);
        this.onClick = onClick;
    }

    public ClickTool(String name, Supplier<ICommand<Texture>> onClick) {
        this(name, (x, y) -> onClick.get());
    }

    public ICommand<Texture> use(Texture t, int x, int y) {
        return onClick.apply(x, y);
    }
}
