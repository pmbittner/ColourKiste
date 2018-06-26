package gui;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import commands.ICommand;
import rendering.Camera;
import rendering.Texture;
import tools.ToolBox;

public class InputHandler implements MouseListener, MouseMotionListener
{
	public Workspace imagePanel;
    private ToolBox toolBox;

    public int buttonHold;

    public InputHandler(Workspace imagePanel, ToolBox user) {
    	this.imagePanel = imagePanel;
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
            Point2D p = imagePanel.screenToTextureCoord(arg0.getX(), arg0.getY());
            if (imagePanel.isPointOnImage(p.getX(), p.getY())) {
                ICommand<Texture> toolCommand = toolBox.getTool().use(
                        imagePanel.getTexture(),
                        (int) p.getX(),
                        (int) p.getY());
                if (toolCommand != null)
                    imagePanel.runCommand(toolCommand);
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
            Point2D p = imagePanel.screenToTextureCoord(arg0.getX(), arg0.getY());
            toolBox.getTool().startUsage(imagePanel.getTexture(), (int)p.getX(), (int)p.getY());
            imagePanel.update();
        }
    }

    /**
     * Invoked when a mouse button has been released on a component.
     */
    @Override
    public void mouseReleased(MouseEvent arg0){
        if (buttonHold == arg0.getButton()) {
            if (buttonHold == MouseEvent.BUTTON1) {
                Point2D p = imagePanel.screenToTextureCoord(arg0.getX(), arg0.getY());
                ICommand<Texture> command = toolBox.getTool().finishUsage(imagePanel.getTexture(), (int)p.getX(), (int)p.getY());
                if (command != null)
                    imagePanel.runCommand(command);
            }
            buttonHold = -1;
            imagePanel.update();
        }
    }

    /**
     * Invoked when a mouse button is pressed on a component and then dragged.
     */
    @Override
    public void mouseDragged(MouseEvent arg0){
        if (buttonHold == MouseEvent.BUTTON1) {
            Point2D p = imagePanel.screenToTextureCoord(arg0.getX(), arg0.getY());
            toolBox.getTool().updateUsage(imagePanel.getTexture(), (int)p.getX(), (int)p.getY());
        }
        imagePanel.update();
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component but no buttons have been pushed.
     */
    @Override
    public void mouseMoved(MouseEvent arg0){

    }
}
