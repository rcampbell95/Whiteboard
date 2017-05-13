package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import model.DShapeModel;

public class DLine extends DShape {

	public DLine(DShapeModel model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		Line2D line = new Line2D.Float(100,100,250,250);
		g2.setColor(model.getColor());
		g2.draw(line);
	}

}
