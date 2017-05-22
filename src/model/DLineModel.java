package model;

import java.awt.Point;

public class DLineModel extends DShapeModel {
	Point p1;
	Point p2;
	
	/***
	 * DLineModel Constructor
	 * Sets the points based on the bounding rectangle
	 */
	public DLineModel() {
		p1 = new Point(getBounds().x,getBounds().y);
		p2 = new Point(getBounds().x+getBounds().width,getBounds().y+getBounds().height);
	}
	
	/**
	 * Returns one of the points of the line
	 * @return p1
	 */
	public Point getPoint1() {
		return p1;
	}
	
	/**
	 * Sets the point p1
	 * @param point
	 */
	public void setPoint1(Point point) {
		p1 = new Point(point);
	}
	
	/**
	 * Returns one of the points of the line
	 * @return
	 */
	public Point getPoint2() {
		return p2;
	}
	
	/**
	 * Sets the point p2
	 * @param point
	 */
	public void setPoint2(Point point) {
		p2 = new Point(point);
	}
	
	/**
	 * Translate the line by an x and y distance
	 */
	@Override
	public void move(int x, int y) {
		p1.x += x;
		p1.y += y;
		p2.x += x;
		p2.y += y;
		super.move(x, y);
	}
	
	
	/**
	 * Sets the anchor and the cursor of the point for resizing
	 * @param anchor, cursor
	 */
	@Override
	public void modifyWithPoints(Point anchor, Point cursor) {
		p1 = new Point(anchor);
		p2 = new Point(cursor);
		super.modifyWithPoints(anchor, cursor);
	}
	
	/**
	 * Copies the model of this DShapeModel to the model
	 * @param model
	 */
	@Override
	public void mimic(DShapeModel model) {
		DLineModel lineMimic = (DLineModel) model;
		setPoint1(lineMimic.getPoint1());
		setPoint2(lineMimic.getPoint2());
		super.mimic(model);
	}
}