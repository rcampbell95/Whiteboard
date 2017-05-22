package view;

import java.awt.Graphics;

import java.awt.Rectangle;

import model.DOvalModel;
import model.DShapeModel;

public class DOval extends DShape {

	public DOval(DShapeModel model, Canvas canvas) {
		super(model, canvas);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void draw(Graphics g, boolean selected) {
		g.setColor(model.getColor());
		Rectangle bounds = model.getBounds();
		g.fillOval(bounds.x,bounds.y,bounds.width,bounds.height);
		if(selected) {
			drawKnobs(g);
		}
		
	}
	@Override
	public DOvalModel getModel() {
		return (DOvalModel) model;
	}

}
