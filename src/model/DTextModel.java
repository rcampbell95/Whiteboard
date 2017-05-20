package model;

import java.awt.Font;

public class DTextModel extends DShapeModel {
	private String text;
	private String fontName;
	private Font textFont;
	
	public Font getFont() {
		return textFont;
	}
	
	public void setFont(String name, double size) {
		textFont = new Font(name, 0, (int) size);
	}

	public String getFontName() {
		return fontName;
	}
	
	public void setFontName(String fontName) {
		this.fontName = fontName;
		notifyListeners();
	}

	@Override
	public void mimic(DShapeModel model) {
		DTextModel textMimic = (DTextModel) model;
		setText(textMimic.getText());
		setFontName(textMimic.getFontName());
		super.mimic(model);
	}

	public void setText(String text) {
		this.text = text;
		notifyListeners();
	}
	
	public String getText() {
		return text;
	}
	
	public int getHeight() {
		return height;
	}
}
