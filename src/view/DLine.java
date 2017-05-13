package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import model.DShapeModel;

public class DLine extends DShape {

	public DLine(DShapeModel model) {
		super(model);
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		Line2D line = new Line2D.Float(10,10,30,30);
		g2.setColor(model.getColor());
		g2.draw(line);
	}

}
