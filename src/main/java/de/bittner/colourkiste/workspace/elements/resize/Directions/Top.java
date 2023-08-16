package de.bittner.colourkiste.workspace.elements.resize.Directions;

import de.bittner.colourkiste.engine.Camera;
import de.bittner.colourkiste.engine.components.input.EntityDragNDrop;
import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.rendering.Texture;
import de.bittner.colourkiste.workspace.Workspace;
import de.bittner.colourkiste.workspace.commands.resize.ResizeCommand;
import de.bittner.colourkiste.workspace.commands.resize.ResizeUpCommand;
import de.bittner.colourkiste.workspace.elements.resize.ResizeBar;

public class Top extends ResizeBar {
    public Top(final Workspace workspace) {
        super(270, workspace);
    }

    @Override
    public ResizeCommand resize(Camera camera, Vec2 delta) {
        return new ResizeUpCommand(camera, (int) -delta.y());
    }

    public void reset() {
        getEntity().setLocation(
                new Vec2(0, -getHalfWorkpieceHeight())
        );
    }

    @Override
    public Vec2 computeNewEntityPosition(Vec2 dragStartPos, Vec2 newCursorPos, Vec2 dragDelta) {
        final Texture t = getWorkspace().getTexture();
        return EntityDragNDrop.DEFAULT
                .computeNewEntityPosition(dragStartPos, newCursorPos, dragDelta)
                .withYMax(getHalfWorkpieceHeight() - 1)
                .snapToPixels(t.getWidth(), t.getHeight())
                .withX(dragStartPos.x());
    }

    @Override
    protected int getBarLength() {
        return getWorkspace().getTexture().getWidth();
    }
}
