package de.bittner.colourkiste.gui.io;

import de.bittner.colourkiste.engine.components.graphics.TextureGraphics;
import de.bittner.colourkiste.engine.components.input.InputComponent;
import de.bittner.colourkiste.math.Transform;
import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.rendering.Texture;
import de.bittner.colourkiste.workspace.ICommand;
import de.bittner.colourkiste.workspace.Workspace;
import de.bittner.colourkiste.workspace.tools.ToolBox;

import java.awt.event.MouseEvent;

public class ApplyTool extends InputComponent {
    private static final int MOUSE_BUTTON = MouseEvent.BUTTON1;
    private final Workspace workspace;
    private final ToolBox toolBox;

    private int dragButton = -1;

    public ApplyTool(Workspace workspace, ToolBox user) {
        this.workspace = workspace;
        this.toolBox = user;
    }

    private Vec2 toTextureCoord(final Vec2 entitySpaceCoord) {
        final TextureGraphics textureGraphics = getEntity().require(TextureGraphics.class);
        return Transform.invert(textureGraphics.getRelativeTransform(), entitySpaceCoord);
    }

    private void startUsageAt(Vec2 pos) {
        final Vec2 p = toTextureCoord(pos);
        dragButton = MOUSE_BUTTON;
        toolBox.getTool().startUsage(workspace.getTexture(), (int)p.x(), (int)p.y());
        workspace.refreshAll();
    }

    private void abortUsage() {
        dragButton = -1;
        toolBox.getTool().abortUsage();
    }

    private void finishUsageAt(final Vec2 pos) {
        dragButton = -1;
        Vec2 p = toTextureCoord(pos);
        ICommand<Texture> command = toolBox.getTool().finishUsage(workspace.getTexture(), (int)p.x(), (int)p.y());
        if (command != null) {
            workspace.runCommand(command);
        }
    }

    /**
     * unused
     * Invoked when the mouse button has been clicked (pressed and released) on a component.
     */
    @Override
    public boolean mouseClicked(int button, Vec2 pos) {
        if (button == MouseEvent.BUTTON1) {
            final Vec2 p = toTextureCoord(pos);
            if (workspace.isPointOnImage(p)) {
                final ICommand<Texture> toolCommand = toolBox.getTool().use(
                        workspace.getTexture(),
                        (int) p.x(),
                        (int) p.y());
                if (toolCommand != null) {
                    workspace.runCommand(toolCommand);
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    @Override
    public boolean mouseDragStart(int button, Vec2 pos) {
        if (isDragging()) {
            abortUsage();
            return true;
        } else if (button == MOUSE_BUTTON) {
            startUsageAt(pos);
            return true;
        }

        return false;
    }

    /**
     * Invoked when a mouse button has been released on a component.
     */
    @Override
    public boolean mouseDragEnd(int button, Vec2 pos) {
        if (button == dragButton) {
            finishUsageAt(pos);
            return true;
        }

        return false;
    }

    /**
     * Invoked when a mouse button is pressed on a component and then dragged.
     */
    @Override
    public boolean mouseDragged(int button, Vec2 pos) {
        if (isDragging()) {
            Vec2 p = toTextureCoord(pos);
            toolBox.getTool().updateUsage(workspace.getTexture(), (int)p.x(), (int)p.y());
            workspace.refreshAll();
            return true;
        }

        return false;
    }

    private boolean isDragging() {
        return dragButton == MOUSE_BUTTON;
    }
}
