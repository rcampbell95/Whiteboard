package model;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.Point;
import java.util.Random;

public abstract class DShapeModel {
	ArrayList<ModelListener> list = new ArrayList<>();
	protected int x1 = 0;
	protected int y1 = 0;
	protected int width = 0;
	protected int height = 0;
	protected Color shapeColor = Color.GRAY;
	protected String text;

	public DShapeModel() {
		Random randGen = new Random();
		int UPPER_BOUND = 75;
		int LOWER_BOUND = 25;
		int CANVAS_SIZE = 400;

		x1 = randGen.nextInt(CANVAS_SIZE);
		y1 = randGen.nextInt(CANVAS_SIZE);
		width = LOWER_BOUND + randGen.nextInt(UPPER_BOUND);
		height = LOWER_BOUND + randGen.nextInt(UPPER_BOUND);
		text = "";
	}


	public void addListener(ModelListener listener) {
		list.add(listener);
	}

	public void removeListener(ModelListener listener) {
		list.remove(listener);
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

	public Rectangle getBounds() {
		Rectangle bounds = new Rectangle(x1, y1, this.getWidth(), this.getHeight());
		return bounds;
	}
	public void setBounds(int x, int y, int width, int height) {
		x1 = x;
		y1 = y;
		this.width = width;
		this.height = height;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public void addTableListener(TableModel tableModel) {
		this.list.add(tableModel);		
	}

	public void removeTableListener(TableModel tableModel) {
		if(this.list.contains(tableModel))
		{
			this.list.remove(tableModel);

		}
	}
	public void modifyWithPoints(Point anchor, Point cursor) {
		int x = (anchor.x < cursor.x ? anchor.x : cursor.x);
		int y = (anchor.y < cursor.y ? anchor.y : cursor.y);
		int width = Math.abs(anchor.x - cursor.x);
		int height = Math.abs(anchor.y - cursor.y);
		setBounds(x,y,width,height);
	}
}
