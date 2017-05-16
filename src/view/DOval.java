package view;

import java.awt.Graphics;
import java.awt.Rectangle;

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
		g.fillOval(model.getX(), model.getY(), model.getWidth(), model.getHeight());
		if(selected) {
			drawKnobs(g);
		}
		
	}

}
