package de.bittner.colourkiste.engine.input;

import de.bittner.colourkiste.engine.Entity;
import de.bittner.colourkiste.engine.InputListener;
import de.bittner.colourkiste.engine.Screen;
import de.bittner.colourkiste.engine.World;
import de.bittner.colourkiste.engine.components.hitbox.Hitbox;
import de.bittner.colourkiste.engine.components.input.InputComponent;
import de.bittner.colourkiste.math.Vec2;
import org.tinylog.Logger;
import org.variantsync.functjonal.functions.TriFunction;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.List;
import java.util.function.BiFunction;

/**
 * TODO: Refactor duplicate code in here
 */
public class EntityInputManager extends InputListener {
    private final Screen screen;
    private final World world;

    @FunctionalInterface
    private interface OnHit {
        boolean consumes(InputComponent i, Vec2 worldPos);
    }

    public EntityInputManager(Screen screen, World world) {
        this.screen = screen;
        this.world = world;
    }

    private void forAllInputEntities(
            final MouseEvent e,
            final OnHit onHit
    ) {
        final Vec2 worldPos = screen.screenToViewportCoord(new Vec2(e.getX(), e.getY()));

        final List<Entity> entities = world.getEntities();
        for (int i = entities.size() - 1; i >= 0; --i) {
            final Entity entity = entities.get(i);
            final InputComponent inputComponent = entity.get(InputComponent.class);
            if (inputComponent != null) {
                if (onHit.consumes(inputComponent, worldPos)) {
                    break;
                }
            }
        }
    }

    private void forAllInputEntitiesAt(
            final MouseEvent e,
            final OnHit onHit
    ) {
        forAllInputEntities(e, (i, worldPos) -> {
            final Hitbox hitbox = i.getEntity().require(Hitbox.class);
            if (hitbox.contains(worldPos)) {
                return onHit.consumes(i, worldPos);
            }

            return false;
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        forAllInputEntitiesAt(e, (i, worldPos) ->
                i.mouseClicked(e.getButton(), worldPos)
        );
    }

    @Override
    public void mousePressed(MouseEvent e) {
        forAllInputEntitiesAt(e, (i, worldPos) ->
                i.mouseDragStart(e.getButton(), worldPos)
        );
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        forAllInputEntities(e, (i, worldPos) ->
                i.mouseDragged(e.getButton(), worldPos)
        );

        screen.refresh();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        forAllInputEntities(e, (i, worldPos) ->
                i.mouseDragEnd(e.getButton(), worldPos)
        );
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        forAllInputEntities(e, (i, worldPos) ->
                i.mouseMoved(e.getButton(), worldPos)
        );
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {}
}
