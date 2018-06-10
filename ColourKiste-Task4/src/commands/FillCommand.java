package commands;

import java.awt.Color;

import rendering.Texture;

public class FillCommand implements ICommand<Texture> {

	private int x, y;
	private Color color;
	private Color colorToReplace;
	private Texture textureBefore;
	
	public FillCommand(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	private void fill(Texture t, int fillX, int fillY) {
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
