package de.bittner.colourkiste.workspace.elements.resize.Directions;

import de.bittner.colourkiste.engine.Camera;
import de.bittner.colourkiste.engine.components.input.EntityDragNDrop;
import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.rendering.Texture;
import de.bittner.colourkiste.workspace.Workspace;
import de.bittner.colourkiste.workspace.commands.resize.ResizeCommand;
import de.bittner.colourkiste.workspace.commands.resize.ResizeLeftCommand;
import de.bittner.colourkiste.workspace.elements.resize.ResizeBar;

public class Left extends ResizeBar {
    public Left(final Workspace workspace) {
        super(180, workspace);
    }

    @Override
    public ResizeCommand resize(Camera camera, Vec2 delta) {
        return new ResizeLeftCommand(camera, (int) -delta.x());
    }

    @Override
    public void reset() {
        getEntity().setLocation(
                new Vec2(-getHalfWorkpieceWidth(), 0)
        );
    }

    @Override
    public Vec2 computeNewEntityPosition(Vec2 dragStartPos, Vec2 newCursorPos, Vec2 dragDelta) {
        final Texture t = getWorkspace().getTexture();
        return EntityDragNDrop.DEFAULT
                .computeNewEntityPosition(dragStartPos, newCursorPos, dragDelta)
                .withXMax(getHalfWorkpieceWidth() - 1)
                .snapToPixels(t.getWidth(), t.getHeight())
                .withY(dragStartPos.y());
    }

    @Override
    protected int getBarLength() {
        return getWorkspace().getTexture().getHeight();
    }
}
