package de.bittner.colourkiste.commands;

import de.bittner.colourkiste.rendering.Texture;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PipetteCommand implements ICommand<Texture> {
    private final Consumer<Color> setColor; // non null
    private final Supplier<Color> getColor; // may be null
    private Color previousColor;
    private final int x, y;

    public PipetteCommand( int x, int y, final Consumer<Color> setColor) {
        this(x, y, setColor, null);
    }
    
    public PipetteCommand(int x, int y, final Consumer<Color> setColor, final Supplier<Color> getColor) {
        this.x = x;
        this.y = y;
        assert setColor != null;
        this.setColor = setColor;
        this.getColor = getColor;
    }
    
    @Override
    public boolean execute(Texture texture) {
        if (getColor != null) {
            previousColor = getColor.get();
        } else {
            previousColor = null;
        }
        
        setColor.accept(texture.getColorAt(x, y));
        
        return true;
    }

    @Override
    public void undo(Texture texture) {
        if (previousColor != null) {
            setColor.accept(previousColor);
        }
    }
}
