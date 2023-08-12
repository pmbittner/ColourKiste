package de.bittner.colourkiste.commands;

import java.awt.Color;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

import de.bittner.colourkiste.rendering.Texture;

public class FillCommand implements ICommand<Texture> {

	private final int x;
    private final int y;
	private final Color color;
	private Color colorToReplace;
	private Texture textureBefore;
	
	public FillCommand(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	private void fill(Texture t, int fillX, int fillY) {
		boolean[][] visited = new boolean[t.getWidth()][t.getHeight()];
		Queue<Point> pixelsToVisit = new LinkedList<>();
		
		pixelsToVisit.add(new Point(fillX, fillY));
		
		Point current;
		Point[] neighbours = new Point[4];
		
		while (!pixelsToVisit.isEmpty()) {
			current = pixelsToVisit.poll();
			visited[current.x][current.y] = true;
			
			if (t.getColorAt(current.x, current.y).equals(colorToReplace)
					&& !t.getColorAt(current.x, current.y).equals(color))
			{
				t.setColorAt(current.x, current.y, color);

				neighbours[0] = new Point(current.x - 1, current.y);
				neighbours[1] = new Point(current.x + 1, current.y);
				neighbours[2] = new Point(current.x, current.y - 1);
				neighbours[3] = new Point(current.x, current.y + 1);
				
				for (int i = 0; i < 4; ++i) {
					Point ni = neighbours[i];
					if (!(ni.x < 0 || ni.y < 0 || ni.x >= t.getWidth() || ni.y >= t.getHeight() || visited[ni.x][ni.y])) {
						pixelsToVisit.add(ni);
					}
				}
			}
		}
		
		if (fillX < 0 || fillY < 0 || fillX >= t.getWidth() || fillY >= t.getHeight())
			return;
		
		if (t.getColorAt(fillX, fillY).equals(colorToReplace) && !t.getColorAt(fillX, fillY).equals(color)) {
			t.setColorAt(fillX, fillY, color);

			fill(t, fillX - 1, fillY);
			fill(t, fillX + 1, fillY);
			
			fill(t, fillX, fillY - 1);
			fill(t, fillX, fillY + 1);
		}
	}

	@Override
	public boolean execute(Texture t) {
		textureBefore = new Texture(t);
		colorToReplace = t.getColorAt(x, y);
		fill(t, x, y);
		return true;
	}

	@Override
	public void undo(Texture t) {
		t.setAwtImage(textureBefore.getAwtImage());
	}

}
