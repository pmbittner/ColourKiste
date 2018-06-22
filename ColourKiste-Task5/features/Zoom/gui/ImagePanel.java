package gui;

public class ImagePanel
{
    private void init() {
    	original();
        this.addMouseWheelListener(inputHandler);
    }
    
    private void updateViewTransform() {
        double zoom = camera.getZoom();
        
        viewTransform.setTransform(
            zoom, 0,
            0, zoom,
            camera.getX() + getWidth() / 2,
            camera.getY() + getHeight() / 2);
    }

    /** GET AND SET **/

    public void setTexture(Texture texture) {
        camera.setZoom(1);
        original(texture);
    }
}
