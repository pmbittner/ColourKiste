import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;

import gui.Workspace;
import gui.MainFrame;
import gui.InputHandler;
import rendering.Camera;
import rendering.Texture;

aspect Zoom {
	private double rendering.Camera.zoomMin = 0.5;
	private double rendering.Camera.zoomMax = 50;
	private double rendering.Camera.zoom = 1;

    /**
     * The camera zooms the given steps. If steps > 0 the camera will zoom in, else out.
     * x and y are the point, the camera should zoom at.
     */
    public void Camera.zoom(int steps, double x, double y) {
        double zoomPrev = zoom;
        double newZoomSqrt = 0.2*steps + Math.sqrt(zoom);
        zoom = Math.min(zoomMax, Math.max(zoomMin, newZoomSqrt * newZoomSqrt));

        double dZoom = zoom - zoomPrev;
        this.x -= dZoom * x;
        this.y -= dZoom * y;
    }

    public void Camera.setZoom(double zoom) {
        if (zoom == 0)
            throw new IllegalArgumentException("Zoom can't be zero!");
        this.zoom = zoom;
    }

    public double Camera.getZoom() { return zoom; }
    
    
       
    
    declare parents : InputHandler implements java.awt.event.MouseWheelListener;

    public void InputHandler.mouseWheelMoved(MouseWheelEvent arg0) {
        Camera c = imagePanel.getCamera();
        Point2D p = imagePanel.screenToLocalCoord(arg0.getX(), arg0.getY());
        c.zoom(
            -arg0.getWheelRotation(),
            p.getX(),
            p.getY());
        imagePanel.update();
    }
    
    
    
        
    after(Workspace w) : target(w) && execution(public Workspace.new(MainFrame)) {
		w.addMouseWheelListener(w.inputHandler);
    }
    
    void around(Workspace w) : target(w) && execution(private void Workspace.updateViewTransform()) {
        double zoom = w.getCamera().getZoom();
        
        w.viewTransform.setTransform(
            zoom, 0,
            0, zoom,
            w.getCamera().getX() + w.getWidth() / 2,
            w.getCamera().getY() + w.getHeight() / 2);
    }

    before(Workspace w) : target(w) && execution(void Workspace.setTexture(Texture)) {
        w.getCamera().setZoom(1);
    }
}