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
		Ellipse2D.Double oval = new Ellipse2D.Double(model.getX(), model.getY(), model.getWidth(), model.getHeight());
		((Graphics2D)g).draw(oval);
		
	}

}
