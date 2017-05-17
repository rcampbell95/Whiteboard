package model;

import java.awt.Font;

public class DTextModel extends DShapeModel {
	private String text;
	private Font textFont;
	
	public Font getFont() {
		return textFont;
	}
	
	public void setFont(String name, double size) {
		textFont = new Font(name, 0, (int) size);
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
