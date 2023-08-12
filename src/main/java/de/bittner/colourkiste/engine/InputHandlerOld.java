package de.bittner.colourkiste.engine;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;

import de.bittner.colourkiste.math.Vec2;
import de.bittner.colourkiste.rendering.WorkspaceScreen;
import de.bittner.colourkiste.workspace.commands.ICommand;
import de.bittner.colourkiste.rendering.Camera;
import de.bittner.colourkiste.rendering.Texture;
import de.bittner.colourkiste.workspace.tools.ToolBox;
import de.bittner.colourkiste.workspace.Workspace;

@Deprecated
public class InputHandlerOld implements MouseListener, MouseMotionListener, MouseWheelListener
{
    private WorkspaceScreen screen;
	private final Workspace workspace;
    private final ToolBox toolBox;

    private double xOnPress, yOnPress;

    private int buttonHold;

    public InputHandlerOld(WorkspaceScreen screen, Workspace workspace, ToolBox user) {
        this.screen = screen;
    	this.workspace = workspace;
        this.toolBox = user;
        buttonHold = -1;
    }

    private void cancelButtonAction() {
        buttonHold = -1;
        toolBox.getTool().abortUsage();
    }

    /**
     * unused
     * Invoked when the mouse button has been clicked (pressed and released) on a component.
     */
    @Override
    public void mouseClicked(MouseEvent arg0){
        if (arg0.getButton() == MouseEvent.BUTTON1) {
            Vec2 p = screen.screenToTextureCoord(arg0.getX(), arg0.getY());
            if (workspace.isPointOnImage(p)) {
                ICommand<Texture> toolCommand = toolBox.getTool().use(
                        workspace.getTexture(),
                        (int) p.x(),
                        (int) p.y());
                if (toolCommand != null)
                    workspace.runCommand(toolCommand);
            }
        }
    }

    /**
     * Invoked when the mouse enters a component.
     */
    @Override
    public void mouseEntered(MouseEvent arg0){

    }

    /**
     * Invoked when the mouse exits a component.
     */
    @Override
    public void mouseExited(MouseEvent arg0){

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    @Override
    public void mousePressed(MouseEvent arg0){
        if (buttonHold == -1 || buttonHold == arg0.getButton())
            buttonHold = arg0.getButton();
        else
            cancelButtonAction();
        
        if (buttonHold == MouseEvent.BUTTON1) {
            Vec2 p = screen.screenToTextureCoord(arg0.getX(), arg0.getY());
            toolBox.getTool().startUsage(workspace.getTexture(), (int)p.x(), (int)p.y());
            workspace.refreshAll();
        } else if (buttonHold == MouseEvent.BUTTON3) {
            Camera c = screen.getCamera();
            xOnPress = arg0.getX() - c.getLocation().x();
            yOnPress = arg0.getY() - c.getLocation().y();
        }
    }

    /**
     * Invoked when a mouse button has been released on a component.
     */
    @Override
    public void mouseReleased(MouseEvent arg0){
        if (buttonHold == arg0.getButton()) {
            if (buttonHold == MouseEvent.BUTTON1) {
                Vec2 p = screen.screenToTextureCoord(arg0.getX(), arg0.getY());
                ICommand<Texture> command = toolBox.getTool().finishUsage(workspace.getTexture(), (int)p.x(), (int)p.y());
                if (command != null)
                    workspace.runCommand(command);
            }
            buttonHold = -1;
            workspace.refreshAll();
        }
    }

    /**
     * Invoked when a mouse button is pressed on a component and then dragged.
     */
    @Override
    public void mouseDragged(MouseEvent arg0){
        if (buttonHold == MouseEvent.BUTTON1) {
            Vec2 p = screen.screenToTextureCoord(arg0.getX(), arg0.getY());
            toolBox.getTool().updateUsage(workspace.getTexture(), (int)p.x(), (int)p.y());
        } else if (buttonHold == MouseEvent.BUTTON3) {
            screen.getCamera().setLocation(new Vec2(
                arg0.getX() - xOnPress,
                arg0.getY() - yOnPress));
        }
        workspace.refreshAll();
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component but no buttons have been pushed.
     */
    @Override
    public void mouseMoved(MouseEvent arg0){

    }

    /**
     * Invoked when the mouse wheel is rotated. 
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent arg0) {
        Camera c = screen.getCamera();
        Vec2 p = screen.screenToViewportCoord(new Vec2(arg0.getX(), arg0.getY()));
        c.zoomTowards(
            -arg0.getWheelRotation(),
            p);
        workspace.refreshAll();
    }
}
