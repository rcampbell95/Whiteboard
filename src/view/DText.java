package view;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

import model.DShapeModel;
import model.DTextModel;

public class DText extends DShape {

	public DText(DShapeModel model, Canvas canvas) {
		super(model, canvas);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g,boolean selected) {
		// TODO Auto-generated method stub
		DTextModel textModel = (DTextModel)model;
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(model.getColor());
		
		computeFont(g);
		g2.setFont(textModel.getFont());
		// Get clip for drawing within the rectangle bounds
		Shape clip = g.getClip();
		// Intersect the clip with the text shape bounds.
		// i.e. we won't lay down any pixels that fall outside our                    
		//bounds
		g.setClip(clip.getBounds().createIntersection(getBounds()));
		// Restore the old clip


		g2.drawString(textModel.getText(),model.getX(),model.getY() + model.getHeight());
		g.setClip(clip);
		
		if(selected) {
			drawKnobs(g);
		}
	}
	
	public void computeFont(Graphics g) {
		double size = 1.0;
		DTextModel textModel = (DTextModel)model;
		
		String fontName = textModel.getFont().getFontName();
		FontMetrics fontSize = g.getFontMetrics(textModel.getFont());
		
		textModel.setFont(fontName, size);
		
		
		while(fontSize.getHeight() <= textModel.getHeight()) {
			size = (size*1.10) + 1;
			textModel.setFont(fontName, size);
			fontSize = g.getFontMetrics(textModel.getFont());
		}
		

	}
	
	public void setFont(String name, int size) {
		((DTextModel)model).setFont(name, size);
	}
	@Override
	public DTextModel getModel() {
		return (DTextModel)model;
	}
}
