package view;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import model.DLineModel;
import model.DShapeModel;
public class DLine extends DShape {

	public DLine(DShapeModel model, Canvas canvas) {
		super(model,canvas);
	}

	@Override
	public void draw(Graphics g, boolean selected) {
		DLineModel lineM = getModel();
		g.setColor(model.getColor());
		g.drawLine(lineM.getPoint1().x, lineM.getPoint1().y,lineM.getPoint2().x,lineM.getPoint2().y);
		if(selected) {
			drawKnobs(g);
		}
	}
	
	@Override
	public DLineModel getModel() {
		return (DLineModel)model;
	}
	
	@Override
	public ArrayList<Point> getKnobs() {
		if(knobs == null || needsRecomputeKnobs) {
			knobs = new ArrayList<Point>();
			DLineModel lineM = (DLineModel) model;
			knobs.add(new Point(lineM.getPoint1()));
			knobs.add(new Point(lineM.getPoint2()));
			needsRecomputeKnobs = false;
		}
		return knobs;
	}
}
