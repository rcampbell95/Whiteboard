package model;

import org.w3c.dom.css.Rect;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.Point;
import java.util.Random;

import model.TableModel;

public abstract class DShapeModel {
	ArrayList<ModelListener> list = new ArrayList<>();
	int UPPER_BOUND = 75;
	int LOWER_BOUND = 25;
	int CANVAS_SIZE = 400;

	protected Random randGen = new Random();
	protected int width;
	protected int height;
	protected int x1;
	protected int y1;

	protected Color shapeColor;
	private Rectangle bounds;
	private int ID;
	private boolean markedForRemoval;


	public DShapeModel() {
		this(0,0);
	}

	public DShapeModel(int x, int y) {
		this(x,y,0,0,Color.GRAY);
	}
	public DShapeModel(int x, int y, int width, int height, Color color) {
		bounds = new Rectangle(x,y,width,height);
		shapeColor = color;
		list = new ArrayList<>();
		markedForRemoval = false;
	}

	public void addListener(ModelListener listener) {
		list.add(listener);
	}

	public void removeListener(ModelListener listener) {
		list.remove(listener);
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setColor(Color shapeColor) {
		this.shapeColor = shapeColor;
		notifyListeners();
	}
	
	public void move(int x, int y) {
		bounds.x += x;
		bounds.y += y;
		notifyListeners();
	}
	
	public void notifyListeners() {
		for(ModelListener listener: list) {
			listener.modelChanged(this);
		}
	}

	public int getX() {
		return this.x1;
	}

	public int getY() {
		return this.y1;
	}



	public Color getColor() {
		return this.shapeColor;
	}

	public Rectangle getBounds() {
		return bounds;
	}
	public void setBounds(int x, int y, int width, int height) {
		bounds = new Rectangle(x,y,width,height);
		this.x1 = x;
		this.y1 = y;
		this.height = height;
		notifyListeners();
	}
	public void setBounds(Rectangle rect) {
		bounds = new Rectangle(rect);
		notifyListeners();
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

	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public void mimic(DShapeModel model) {
		setID(model.getID());
		setBounds(model.getBounds());
		setColor(model.getColor());
		notifyListeners();
	}


	public void markForRemoval() {
		markedForRemoval = true;
		notifyListeners();
	}
	public void modifyWithPoints(Point anchor, Point cursor) {
		int x = (anchor.x < cursor.x ? anchor.x : cursor.x);
		int y = (anchor.y < cursor.y ? anchor.y : cursor.y);
		int width = Math.abs(anchor.x - cursor.x);
		int height = Math.abs(anchor.y - cursor.y);
		setBounds(new Rectangle(x,y,width,height));
	}
	public boolean markedForRemoval() {
		return markedForRemoval;
	}
}
