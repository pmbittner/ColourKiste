package commands;

import rendering.Texture;

public class Comicify implements ICommand<Texture> {
    private Texture previous = null;

    @Override
    public boolean execute(Texture texture) {
        previous = new Texture(texture);
        comicify(texture);
        return true;
    }

    @Override
    public void undo(Texture texture) {
        texture.setAwtImage(previous.getAwtImage());
    }

    private void comicify(Texture texture) {
        texture.set(texture.contrast(145));
    }
}
