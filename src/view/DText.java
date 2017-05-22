package view;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.LineMetrics;

import model.DShapeModel;
import model.DTextModel;

public class DText extends DShape {
	private boolean needsRecomputeFont;
	private Font computedFont;
	private String lastFont;
	private int lastHeight;

	/**
	 * Constructor for DText, sets default values
	 */
	public DText(DShapeModel model, Canvas canvas) {
		super(model, canvas);
		needsRecomputeFont = true;
		computedFont = null;
		lastFont = "";
		lastHeight = -1;
	}

	/**
	 * Draws the text and if selected, the knobs
	 */
	@Override
	public void draw(Graphics g,boolean selected) {
		DTextModel textModel = (DTextModel)model;
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(textModel.getColor());
		
		Font font = computeFont(g);
		int fontOffset = (int) font.getLineMetrics(textModel.getText(), ((Graphics2D) g).getFontRenderContext()).getDescent();
		int yPosition = textModel.getBounds().y + textModel.getBounds().height - fontOffset;
		g2.setFont(font);
		
		// Get clip for drawing within the rectangle bounds
		Shape clip = g2.getClip();

		//bounds
		g2.setClip(clip.getBounds().createIntersection(getBounds()));



		g2.drawString(textModel.getText(),textModel.getBounds().x, yPosition);
		g2.setClip(clip);
		
		if(selected) {
			drawKnobs(g);
			
		}
	}

	/**
	 * Computes the font of the text
	 * @param g
	 * @return computedFont
	 */
	public Font computeFont(Graphics g) {
		if(needsRecomputeFont) {
			double size = 1.0;
			double previousSize = size;
			DTextModel textModel = (DTextModel)model;

			while(true) {
				computedFont = new Font(textModel.getFontName(), Font.PLAIN, (int) size);
				LineMetrics fontMetrics = computedFont.getLineMetrics(getText(), ((Graphics2D) g).getFontRenderContext());
				
				if(fontMetrics.getHeight() > textModel.getBounds().getHeight()) {
					break;
				}
				previousSize = size;
				size = (size*1.10) + 1;
			}
			computedFont = new Font(textModel.getFontName(), Font.PLAIN, (int) previousSize);
		}
		return computedFont;
		
		
	}

	/**
	 * Implements the modelChanged interface, updates the text
	 * @param model
	 */
	@Override
	public void modelChanged(DShapeModel model) {
		DTextModel textModel = (DTextModel) model;
		if (textModel.getBounds().height != lastHeight
				|| !textModel.getFontName().equals(lastFont)) {
			lastHeight = textModel.getBounds().height;
			lastFont = textModel.getFontName();
			needsRecomputeFont = true;
		}
		super.modelChanged(textModel);
	}

	/**
	 * Sets the font
	 * @param name
	 */
	public void setFont(String name) {
		((DTextModel)model).setFont(name, 1);
	}

	/**
	 * Sets the font name
	 * @param fontName
	 */
	public void setFontName(String fontName) {
		((DTextModel)model).setFontName(fontName);
	}

	/**
	 * Gets the model of the text
	 */
	@Override
	public DTextModel getModel() {
		return (DTextModel)model;
	}
}
