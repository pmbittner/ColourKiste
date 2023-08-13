package de.bittner.colourkiste.engine.components.input;

import de.bittner.colourkiste.math.Vec2;

import java.awt.event.MouseEvent;

public class EntityDragNDrop extends InputComponent {
    private static final int DRAG_AND_DROP_BUTTON = MouseEvent.BUTTON1;
    private int dragButton = -1;
    private Vec2 dragDelta;
    private Vec2 lastEntityPos;

    private void startDragNDropAt(Vec2 pos) {
        dragButton = DRAG_AND_DROP_BUTTON;
        dragDelta = pos;
        lastEntityPos = getEntity().getLocation();
    }

    private void stopDragNDrop() {
        dragButton = -1;
    }

    private void dragTo(Vec2 pos) {
        final Vec2 newPos = lastEntityPos.add(pos.minus(dragDelta));
        getEntity().setLocation(newPos);
        lastEntityPos = newPos;
    }

    @Override
    public boolean mouseDragStart(int button, Vec2 pos) {
        if (isDragging()) {
            stopDragNDrop();
            return true;
        } else if (button == DRAG_AND_DROP_BUTTON) {
            startDragNDropAt(pos);
            return true;
        }

        return false;
    }

    @Override
    public boolean mouseDragged(int button, Vec2 pos) {
        if (isDragging()) {
            dragTo(pos);
            return true;
        }

        return false;
    }


    @Override
    public boolean mouseDragEnd(int button, Vec2 pos) {
        if (button == dragButton) {
            stopDragNDrop();
            return true;
        }

        return false;
    }

    private boolean isDragging() {
        return dragButton == DRAG_AND_DROP_BUTTON;
    }
}
