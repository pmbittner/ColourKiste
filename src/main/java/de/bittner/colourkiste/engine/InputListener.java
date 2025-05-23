package de.bittner.colourkiste.engine;

import java.awt.event.*;

public abstract class InputListener implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private Screen screen;

    void setScreen(Screen screen) {
        this.screen = screen;
    }

    protected Screen getScreen() {
        return screen;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {}
}

