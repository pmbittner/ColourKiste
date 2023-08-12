package de.bittner.colourkiste.workspace.commands;

import de.bittner.colourkiste.rendering.Texture;
import de.bittner.colourkiste.imageprocessing.kernels.Erosion;

public class Comicify extends UndoableTextureManipulation {
    public void manipulate(Texture texture) {
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
