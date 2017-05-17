package view;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import model.DShapeModel;
import model.DTextModel;

public class DText extends DShape {

	public DText(DShapeModel model) {
		super(model);
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
		g2.drawString(textModel.getText(),model.getX(),model.getY());
		if(selected) {
			drawKnobs(g);
		}
	}
	
	public void computeFont(Graphics g) {
		double size = 1.0;
		DTextModel textModel = (DTextModel)model;
		
		String fontName = textModel.getFont().getFontName();
		FontMetrics fontSize = g.getFontMetrics(textModel.getFont());
		
		
		while(fontSize.getHeight() <= textModel.getHeight()) {
			size = (size*1.10) + 1;
			textModel.setFont(fontName, size);
			fontSize = g.getFontMetrics(textModel.getFont());
		}
		

	}
	
	public void setFont(String name, int size) {
		((DTextModel)model).setFont(name, size);
	}
}
