package de.bittner.colourkiste.rendering;

import de.bittner.colourkiste.gui.Workspace;

import javax.swing.*;
import java.awt.*;

public class WorkspaceScreen extends JPanel {
    private final Workspace workspace;

    /** GRAPHICS and RENDERING **/
    private final Texture background;

    public WorkspaceScreen(Workspace workspace) {
        super(true);
        this.workspace = workspace;
        background = createBackground();
    }

    private Texture createBackground() {
        int size = 100;
        int step = 10;

        Texture background = new Texture(size, size);

        background.setColor(new java.awt.Color(100, 100, 100));
        background.fill();

        background.setColor(new java.awt.Color(120, 120, 120));
        for (int x = 0; x < size; x += step)
            for (int y = x % (2*step); y < size; y += 2*step)
                background.fillRect(x, y, step, step);

        return background;
    }

    protected void paintComponent(Graphics gc) {
        super.paintComponent(gc);

        workspace.updateViewTransform();

        Graphics2D g2 = (Graphics2D) gc;

        // draw Background
        for (int x = 0; x < this.getWidth(); x += background.getWidth())
            for (int y = 0; y < this.getHeight(); y += background.getHeight())
                g2.drawImage(background.getAwtImage(), null, x, y);

        // draw all WorkingElements
        for (WorkingElement we : workspace.getWorkingElements()) {
            we.draw(g2, workspace.getViewTransform());
        }
    }

    public void refresh() {
        repaint();
    }
}
