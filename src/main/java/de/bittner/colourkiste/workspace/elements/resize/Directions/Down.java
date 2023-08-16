package de.bittner.colourkiste.workspace.elements.resize.Directions;

import de.bittner.colourkiste.engine.Camera;
import de.bittner.colourkiste.engine.components.input.EntityDragNDrop;
import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.rendering.Texture;
import de.bittner.colourkiste.workspace.Workspace;
import de.bittner.colourkiste.workspace.commands.resize.ResizeCommand;
import de.bittner.colourkiste.workspace.commands.resize.ResizeDownCommand;
import de.bittner.colourkiste.workspace.elements.resize.ResizeBar;

public class Down extends ResizeBar {
    public Down(final Workspace workspace) {
        super(90, workspace);
    }

    @Override
    public ResizeCommand resize(Camera camera, Vec2 delta) {
        return new ResizeDownCommand(camera, (int) delta.y());
    }

    @Override
    public void reset() {
        getEntity().setLocation(
                new Vec2(0, getHalfWorkpieceHeight())
        );
    }

    @Override
    public Vec2 computeNewEntityPosition(Vec2 dragStartPos, Vec2 newCursorPos, Vec2 dragDelta) {
        final Texture t = getWorkspace().getTexture();
        return EntityDragNDrop.DEFAULT
                .computeNewEntityPosition(dragStartPos, newCursorPos, dragDelta)
                // We cannot resize the workpiece to a height smaller than a pixel
                .withYMin(-getHalfWorkpieceHeight() + 1)
                // Make the resize bar snap to pixels
                .snapToPixels(t.getWidth(), t.getHeight())
                // When adjusting height, we should change width
                .withX(dragStartPos.x());
    }

    @Override
    protected int getBarLength() {
        return getWorkspace().getTexture().getWidth();
    }
}
