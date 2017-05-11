package view;

import java.awt.Graphics;
import java.awt.Rectangle;

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
	
}
