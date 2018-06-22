package gui;

public class ImagePanel
{
    private DropTargetAdapter dropTargetHandler;
    
    private void init() {
    	original();
        dropTargetHandler = new ImagePanelDropHandler(this);
    }
}
