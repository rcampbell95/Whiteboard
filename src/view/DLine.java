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


	/**
	 * Draw lines
	 * @param g
	 * @param selected
	 */
	@Override
	public void draw(Graphics g, boolean selected) {
		DLineModel lineM = getModel();
		g.setColor(getColor());
		g.drawLine(lineM.getPoint1().x, lineM.getPoint1().y,lineM.getPoint2().x,lineM.getPoint2().y);
		if(selected) {
			drawKnobs(g);
		}
	}

	/**
	 * DLineModel's model
	 * @return
	 */
	@Override
	public DLineModel getModel() {
		return (DLineModel)model;
	}

	/**
	 * Recomputed knobs for DLine
	 * @return
	 */
	@Override
	public ArrayList<Point> getKnobs() {
		if(knobs == null || needsRecomputeKnobs) {
			knobs = new ArrayList<>();
			DLineModel lineM = (DLineModel) model;
			knobs.add(new Point(lineM.getPoint1()));
			knobs.add(new Point(lineM.getPoint2()));
			needsRecomputeKnobs = false;
		}
		return knobs;
	}
}
