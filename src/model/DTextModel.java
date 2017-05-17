package model;

import java.awt.Font;

public class DTextModel extends DShapeModel {
	private String text;
	private Font textFont;
	
	public Font getFont() {
		return textFont;
	}
	
	public void setFont(Font font) {
		textFont = font;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}
}
