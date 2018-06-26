import java.awt.event.MouseEvent;

import gui.InputHandler;
import gui.Workspace;
import rendering.Camera;

aspect Move {
	declare parents : InputHandler implements java.awt.event.MouseListener;
	declare parents : InputHandler implements java.awt.event.MouseMotionListener;

	private double InputHandler.xOnPress;
	private double InputHandler.yOnPress;
	
    after(InputHandler i, MouseEvent arg0) : target(i) && execution(public void InputHandler.mousePressed(MouseEvent)) && args(arg0) {
        if (i.buttonHold == MouseEvent.BUTTON3) {
            Camera c = i.imagePanel.getCamera();
            i.xOnPress = arg0.getX() - c.getX();
            i.yOnPress = arg0.getY() - c.getY();
        }
    }
	
    before(InputHandler i, MouseEvent arg0) : target(i) && execution(public void InputHandler.mouseDragged(MouseEvent)) && args(arg0) {
    	if (i.buttonHold == MouseEvent.BUTTON3) {
            i.imagePanel.getCamera().setLocation(
                arg0.getX() - i.xOnPress,
                arg0.getY() - i.yOnPress);
        }
    }
}