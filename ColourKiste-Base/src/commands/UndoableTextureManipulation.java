package commands;

import rendering.Texture;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class UndoableTextureManipulation implements ICommand<Texture> {
    private Texture previous = null;

    public abstract void manipulate(Texture texture);

    @Override
    public final boolean execute(Texture texture) {
        previous = new Texture(texture);
        manipulate(texture);
        return true;
    }

    @Override
    public final void undo(Texture t) {
        t.setAwtImage(previous.getAwtImage());
    }

    public static UndoableTextureManipulation Manipulate(Consumer<Texture> manipulation) {
        return new UndoableTextureManipulation() {
            @Override
            public void manipulate(Texture texture) {
                manipulation.accept(texture);
            }
        };
    }

    public static UndoableTextureManipulation Convert(Function<Texture, Texture> manipulation) {
        return new UndoableTextureManipulation() {
            @Override
            public void manipulate(Texture texture) {
                texture.set(manipulation.apply(texture));
            }
        };
    }
}
