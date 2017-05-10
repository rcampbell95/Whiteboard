package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import model.DShapeModel;

public class DOval extends DShape {

	public DOval(DShapeModel model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(model.getColor());
		g.fillOval(model.getX(), model.getY(), model.getWidth(), model.getHeight());
		
	}

}
