package gui;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import java.awt.geom.Point2D;

import commands.ICommand;
import rendering.Camera;
import rendering.Texture;

public class InputHandler implements MouseListener, MouseMotionListener
{
    private double xOnPress, yOnPress;

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    @Override
    public void mousePressed(MouseEvent arg0){
        original(arg0);

        if (buttonHold == MouseEvent.BUTTON3) {
            Camera c = imagePanel.getCamera();
            xOnPress = arg0.getX() - c.getX();
            yOnPress = arg0.getY() - c.getY();
        }
    }
    
    /**
     * Invoked when a mouse button is pressed on a component and then dragged.
     */
    @Override
    public void mouseDragged(MouseEvent arg0){
        if (buttonHold == MouseEvent.BUTTON3) {
            imagePanel.getCamera().setLocation(
                arg0.getX() - xOnPress,
                arg0.getY() - yOnPress);
        }
    	original(arg0);
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component but no buttons have been pushed.
     */
    @Override
    public void mouseMoved(MouseEvent arg0){

    }
}
