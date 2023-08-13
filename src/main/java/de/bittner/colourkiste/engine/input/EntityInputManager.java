package de.bittner.colourkiste.engine.input;

import de.bittner.colourkiste.engine.Entity;
import de.bittner.colourkiste.engine.InputListener;
import de.bittner.colourkiste.engine.Screen;
import de.bittner.colourkiste.engine.World;
import de.bittner.colourkiste.engine.components.hitbox.Hitbox;
import de.bittner.colourkiste.engine.components.input.InputComponent;
import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.rendering.Texture;
import de.bittner.colourkiste.workspace.ICommand;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class EntityInputManager extends InputListener {
    private final Screen screen;
    private final World world;

    private int buttonHold = -1;

    public EntityInputManager(Screen screen, World world) {
        this.screen = screen;
        this.world = world;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        final Vec2 pos = screen.screenToViewportCoord(new Vec2(e.getX(), e.getY()));

        for (final Entity entity : world.getEntities()) {
            final InputComponent inputComponent = entity.get(InputComponent.class);
            if (inputComponent != null) {
                final Hitbox hitbox = entity.require(Hitbox.class);
                if (hitbox.contains(pos)) {
                    final boolean consumed = inputComponent.mouseDragStart(e.getButton(), entity.toEntitySpace(pos));
                    if (consumed) {
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        final Vec2 pos = screen.screenToViewportCoord(new Vec2(e.getX(), e.getY()));

        for (final Entity entity : world.getEntities()) {
            final InputComponent inputComponent = entity.get(InputComponent.class);
            if (inputComponent != null) {
                final boolean consumed = inputComponent.mouseDragged(e.getButton(), entity.toEntitySpace(pos));
                if (consumed) {
                    break;
                }
            }
        }

        screen.refresh();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        final Vec2 pos = screen.screenToViewportCoord(new Vec2(e.getX(), e.getY()));

        for (final Entity entity : world.getEntities()) {
            final InputComponent inputComponent = entity.get(InputComponent.class);
            if (inputComponent != null) {
                final boolean consumed = inputComponent.mouseDragEnd(e.getButton(), entity.toEntitySpace(pos));
                if (consumed) {
                    break;
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {}
}
