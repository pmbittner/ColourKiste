package de.bittner.colourkiste.engine.components.input;

import de.bittner.colourkiste.event.EventHandler;
import de.bittner.colourkiste.math.Vec2;
import org.tinylog.Logger;
import org.variantsync.functjonal.Unit;

import java.awt.event.MouseEvent;
import java.util.function.Function;

public class EntityDragNDrop extends InputComponent {
    public final EventHandler<Unit> OnDragStart = new EventHandler<>();
    public final EventHandler<Unit> OnDragEnd = new EventHandler<>();

    @FunctionalInterface
    public interface DragPosition {
        Vec2 computeNewEntityPosition(Vec2 dragStartPos, Vec2 newCursorPos, Vec2 dragDelta);

        default DragPosition andThen(final Function<Vec2, Vec2> f) {
            return (o, n, d) -> f.apply(computeNewEntityPosition(o, n, d));
        }
    }

    public static final DragPosition DEFAULT = (dragStartPos, newCursorPos, delta) -> newCursorPos.minus(delta);

    private static final int DRAG_AND_DROP_BUTTON = MouseEvent.BUTTON1;
    private int dragButton;
    private Vec2 dragDelta, dragBeginPos;
    private DragPosition dragPosition;

    public EntityDragNDrop() {
        this(DEFAULT);
    }

    public EntityDragNDrop(final DragPosition dragPosition) {
        this.dragPosition = dragPosition;
        this.dragButton = -1;
        this.dragDelta = Vec2.all(0);
        this.dragBeginPos = Vec2.all(0);
    }

    private void startDragNDropAt(final Vec2 pos) {
        dragButton = DRAG_AND_DROP_BUTTON;
        dragBeginPos = getEntity().getLocation();
        dragDelta = pos.minus(dragBeginPos);
        OnDragStart.fire(Unit.Instance());
        Logger.info("delta = {}, pos = {}", dragDelta, dragBeginPos);
    }

    private void stopDragNDrop() {
        dragButton = -1;
        OnDragEnd.fire(Unit.Instance());
    }

    private void dragTo(Vec2 pos) {
        final Vec2 newPos = dragPosition.computeNewEntityPosition(dragBeginPos, pos, dragDelta);
        Logger.info(newPos);
        getEntity().setLocation(newPos);
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
