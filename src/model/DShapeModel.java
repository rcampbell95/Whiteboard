package model;

import java.awt.Color;

public class DShapeModel {
	private int x = 0;
	private int y = 0;
	private int width = 0;
	private int height = 0;
	private Color shapeColor = Color.GRAY;
	
	public void setCoordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void setColor(Color shapeColor) {
		this.shapeColor = shapeColor;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
}
