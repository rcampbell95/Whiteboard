package view;

import java.awt.Graphics;

import model.DShapeModel;

public abstract class DShape {
	DShapeModel model;
	
	public DShape(DShapeModel model) {
		this.model = model;
	}
	
	public abstract void draw(Graphics g);

}
