package model;

public class DTextModel extends DShapeModel {

	private String text;
	private String fontName;
	public DTextModel() {
		text = "";
		fontName = "Dialog";
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
		notifyListeners();
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
}
