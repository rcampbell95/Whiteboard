package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import model.DShapeModel;

public class DRect extends DShape {

	public DRect(DShapeModel model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		//Rectangle shape = new Rectangle(model.getX(), model.getY(), model.getWidth(), model.getHeight());
		g.setColor(model.getColor());
		g.fillRect(model.getX(), model.getY(), model.getWidth(), model.getHeight());
		//((Graphics2D)g).draw(shape);
	}

}
