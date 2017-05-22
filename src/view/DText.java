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

	public DText(DShapeModel model, Canvas canvas) {
		super(model, canvas);
		// TODO Auto-generated constructor stub
		needsRecomputeFont = true;
		computedFont = null;
		lastFont = "";
		lastHeight = -1;
	}

	@Override
	public void draw(Graphics g,boolean selected) {
		// TODO Auto-generated method stub
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
	
	public void setFont(String name) {
		((DTextModel)model).setFont(name, 1);
	}
	
	public void setFontName(String fontName) {
		((DTextModel)model).setFontName(fontName);
	}
	@Override
	public DTextModel getModel() {
		return (DTextModel)model;
	}
}
