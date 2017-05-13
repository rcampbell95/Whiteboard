package view;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Point;

import model.DShapeModel;

public abstract class DShape {
	DShapeModel model;

	public DShape(DShapeModel model) {
		this.model = model;
	}

	public Rectangle getBounds() {
		return model.getBounds();
	}

	public void getKnobs() {
		;
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
}
