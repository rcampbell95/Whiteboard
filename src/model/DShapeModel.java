package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public abstract class DShapeModel {
	protected int x1 = 0;
	protected int y1 = 0;
	protected int width = 0;
	protected int height = 0;
	protected Color shapeColor = Color.GRAY;
	
	ArrayList<ModelListener> listeners;
	
	public DShapeModel() {
		Random randGen = new Random();
		int UPPER_BOUND = 75;
		int LOWER_BOUND = 25;
		int CANVAS_SIZE = 400;
		
		x1 = randGen.nextInt(CANVAS_SIZE);
		y1 = randGen.nextInt(CANVAS_SIZE);
		width = LOWER_BOUND + randGen.nextInt(UPPER_BOUND);
		height = LOWER_BOUND + randGen.nextInt(UPPER_BOUND);
		
	}
	
	public void setCoordinate(int x, int y) {
		this.x1 = x;
		this.y1 = y;
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void setColor(Color shapeColor) {
		this.shapeColor = shapeColor;
	}
	
	public int getX() {
		return this.x1;
	}
	
	public int getY() {
		return this.y1;
	}
	
	public abstract int getWidth();
	
	public abstract int getHeight();
	
	public Color getColor() {
		return this.shapeColor;
	}
}
