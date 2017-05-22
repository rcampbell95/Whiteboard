package model;

import java.awt.Point;

public class DLineModel extends DShapeModel {
	Point p1;
	Point p2;
	
	public DLineModel() {
		p1 = new Point(getBounds().x,getBounds().y);
		p2 = new Point(getBounds().x+getBounds().width,getBounds().y+getBounds().height);
	}
	
	public Point getPoint1() {
		return p1;
	}
	public void setPoint1(Point point) {
		p1 = new Point(point);
	}
	public Point getPoint2() {
		return p2;
	}
	public void setPoint2(Point point) {
		p2 = new Point(point);
	}
	
	@Override
	public void move(int x, int y) {
		p1.x += x;
		p1.y += y;
		p2.x += x;
		p2.y += y;
		super.move(x, y);
	}
	
	@Override
	public void modifyWithPoints(Point anchor, Point cursor) {
		p1 = new Point(anchor);
		p2 = new Point(cursor);
		super.modifyWithPoints(anchor, cursor);
	}
	@Override
	public void mimic(DShapeModel model) {
		DLineModel lineMimic = (DLineModel) model;
		setPoint1(lineMimic.getPoint1());
		setPoint2(lineMimic.getPoint2());
		super.mimic(model);
	}
}