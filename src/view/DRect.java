package view;

import java.awt.Graphics;
import java.awt.Rectangle;

import model.DShapeModel;

public class DRect extends DShape {

	public DRect(DShapeModel model, Canvas canvas) {
		super(model, canvas);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g, boolean selected) {
		//Rectangle shape = new Rectangle(model.getX(), model.getY(), model.getWidth(), model.getHeight());
		g.setColor(model.getColor());
		Rectangle bounds = model.getBounds();
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		//((Graphics2D)g).draw(shape);
		if(selected) {
			drawKnobs(g);
		}
	}

}
