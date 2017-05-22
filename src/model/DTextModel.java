package model;

import java.awt.Font;

public class DTextModel extends DShapeModel {
	private String text;
	private String fontName;
	private Font textFont;

	/**
	 * Gets the font of the text
	 * @return textFont
	 */
	public Font getFont() {
		return textFont;
	}

	/**
	 * Sets the font of the text
	 * @param name, size
	 */
	public void setFont(String name, double size) {
		textFont = new Font(name, 0, (int) size);
	}

	/**
	 * Gets the name of the font
	 * @return fontName
	 */
	public String getFontName() {
		return fontName;
	}

	/**
	 * Sets the font name
	 * @param fontName
	 */
	public void setFontName(String fontName) {
		this.fontName = fontName;
		notifyListeners();
	}

	/**
	 * Copies the model of this DShapeModel to the model
	 * @param model
	 */
	@Override
	public void mimic(DShapeModel model) {
		DTextModel textMimic = (DTextModel) model;
		setText(textMimic.getText());
		setFontName(textMimic.getFontName());
		super.mimic(model);
	}

	/**
	 * Sets the text to the string specified
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
		notifyListeners();
	}

	/**
	 * Gets the text
	 * @return text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Gets the height of the text
	 * @return height
	 */
	public int getHeight() {
		return height;
	}
}
