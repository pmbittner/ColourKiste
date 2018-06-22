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

public class InputHandler implements MouseWheelListener
{
    /**
     * Invoked when the mouse wheel is rotated. 
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent arg0) {
        Camera c = imagePanel.getCamera();
        Point2D p = imagePanel.screenToLocalCoord(arg0.getX(), arg0.getY());
        c.zoom(
            -arg0.getWheelRotation(),
            p.getX(),
            p.getY());
        imagePanel.update();
    }
}
