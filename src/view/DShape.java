package view;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import model.DShapeModel;

public abstract class DShape {
	DShapeModel model;
	ArrayList<Point> knobs;
	boolean needsRecomputeKnobs;

	public DShape(DShapeModel model) {
		this.model = model;
		knobs = null;
		needsRecomputeKnobs = false;
	}
	public void drawKnobs(Graphics g) {
		g.setColor(Color.BLACK);
		for(Point point: getKnobs()) {
			g.fillRect(point.x - 9/2, point.y - 9/2, 9, 9);
		}
	}

	public Rectangle getBounds() {
		return model.getBounds();
	}

	public ArrayList<Point> getKnobs() {
		if(knobs == null || needsRecomputeKnobs) {
			knobs = new ArrayList<>();
			Rectangle bounds = model.getBounds();
			for(int i = 0;i<2;i++) {
				for(int j = 0;j<2;j++) {
					knobs.add(new Point(bounds.x+bounds.width*i,bounds.y+bounds.height*j));
				}
			}
			Point temp = knobs.remove(2);
			knobs.add(temp);
			needsRecomputeKnobs = false;
		}
		return knobs;
	}

	public Point getAnchorForSelectedKnob(Point point) {
		int index = getKnobs().indexOf(point);
		return new Point(knobs.get((index + knobs.size() / 2) % knobs.size()));
	}
	public boolean selectedKnob(Point point, Point knobCenter)
	{
		Rectangle knob = new Rectangle(knobCenter.x - 9 / 2, knobCenter.y - 9 / 2, 9, 9);
		return knob.contains(point);
	}

	public abstract void draw(Graphics g);

	public void setColor(Color c) {
		model.setColor(c);
	}
	public Color getColor() {
		return model.getColor();
	}
	public String getText() {
		return model.getText();
	}
	public void setText(String text) {
		model.setText(text);
	}
	public boolean containsPoint(Point point) {
		Rectangle bounds = model.getBounds();

		if(bounds.contains(point)) {
			return true;
		} if(bounds.width == 0 && Math.abs(point.x-bounds.x) <= 3 
				&& point.y <= bounds.y + bounds.height && point.y >= bounds.y) {
			return true;
		} if(bounds.height == 0 && Math.abs(point.y-bounds.y) <= 3 && point.x >= bounds.x
				&& point.x <= bounds.x + bounds.width) {
			return true;
		}
		return false;
	}
	public void modifyShapeWithPoints(Point anchor, Point cursor) {
		needsRecomputeKnobs = true;
		model.modifyWithPoints(anchor, cursor);
	}
}
