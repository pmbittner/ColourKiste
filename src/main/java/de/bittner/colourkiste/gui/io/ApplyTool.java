package de.bittner.colourkiste.gui.io;

import de.bittner.colourkiste.engine.InputListener;
import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.rendering.Texture;
import de.bittner.colourkiste.rendering.WorkspaceScreen;
import de.bittner.colourkiste.workspace.Workspace;
import de.bittner.colourkiste.workspace.commands.ICommand;
import de.bittner.colourkiste.workspace.tools.ToolBox;
import org.tinylog.Logger;

import java.awt.event.MouseEvent;

public class ApplyTool extends InputListener {
    private final WorkspaceScreen screen;
    private final Workspace workspace;
    private final ToolBox toolBox;

    private int buttonHold = -1;

    public ApplyTool(WorkspaceScreen screen, Workspace workspace, ToolBox user) {
        this.screen = screen;
        this.workspace = workspace;
        this.toolBox = user;
    }

    private void cancelButtonAction() {
        toolBox.getTool().abortUsage();
    }

    /**
     * unused
     * Invoked when the mouse button has been clicked (pressed and released) on a component.
     */
    @Override
    public void mouseClicked(MouseEvent arg0) {
        if (arg0.getButton() == MouseEvent.BUTTON1) {
            final Vec2 p = screen.screenToTextureCoord(arg0.getX(), arg0.getY());
            if (workspace.isPointOnImage(p)) {
                ICommand<Texture> toolCommand = toolBox.getTool().use(
                        screen.getWorkspace().getTexture(),
                        (int) p.x(),
                        (int) p.y());
                if (toolCommand != null) {
                    workspace.runCommand(toolCommand);
                }
            }
        }
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    @Override
    public void mousePressed(MouseEvent arg0) {
        boolean refresh = false;

        if (buttonHold == -1 || buttonHold == arg0.getButton()) {
            buttonHold = arg0.getButton();
        } else {
            cancelButtonAction();
            refresh = true;
        }

        if (buttonHold == MouseEvent.BUTTON1) {
            final Vec2 p = screen.screenToTextureCoord(arg0.getX(), arg0.getY());
            toolBox.getTool().startUsage(workspace.getTexture(), (int)p.x(), (int)p.y());
            refresh = true;
        }

        if (refresh) {
            screen.refresh();
        }
    }

    /**
     * Invoked when a mouse button has been released on a component.
     */
    @Override
    public void mouseReleased(MouseEvent arg0) {
        if (buttonHold == arg0.getButton()) {
            if (buttonHold == MouseEvent.BUTTON1) {
                Vec2 p = screen.screenToTextureCoord(arg0.getX(), arg0.getY());
                ICommand<Texture> command = toolBox.getTool().finishUsage(workspace.getTexture(), (int)p.x(), (int)p.y());
                if (command != null) {
                    workspace.runCommand(command);
                }
            }
        }

        buttonHold = -1;
    }

    /**
     * Invoked when a mouse button is pressed on a component and then dragged.
     */
    @Override
    public void mouseDragged(MouseEvent arg0) {
        if (buttonHold == MouseEvent.BUTTON1) {
            Vec2 p = screen.screenToTextureCoord(arg0.getX(), arg0.getY());
            toolBox.getTool().updateUsage(workspace.getTexture(), (int)p.x(), (int)p.y());
            screen.refresh();
        }
    }
}
