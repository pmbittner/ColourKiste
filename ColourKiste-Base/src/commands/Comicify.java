package commands;

import rendering.Texture;
import rendering.kernels.Erosion;

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
        Erosion erode = Erosion.Erosion();
        Erosion dilate = Erosion.Dilatation();

        Texture bw = texture.contrast(120);

        bw = erode.apply(bw);
        bw = erode.apply(bw);
        //bw = erode.apply(bw);
        bw = dilate.apply(bw);
        bw = dilate.apply(bw);
        bw = dilate.apply(bw);

        texture.set(bw);
    }
}
