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

	/**
	 * Add a listener to the model's list of listeners
	 * @param listener
	 */
	public void addListener(ModelListener listener) {
		list.add(listener);
	}

	/**
	 * Remove the listener from the model's list of listeners
	 * @param listener
	 */
	public void removeListener(ModelListener listener) {
		list.remove(listener);
	}

	/**
	 * Set the size of the shape
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Set the color of the shape
	 * @param shapeColor
	 */
	public void setColor(Color shapeColor) {
		this.shapeColor = shapeColor;
		notifyListeners();
	}
	
	/**
	 * Move the shape by an x and y offset
	 * @param x
	 * @param y
	 */
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

	/**
	 * Get the x position of the shape
	 * @return
	 */
	public int getX() {
		return this.x1;
	}

	/**
	 * Get the y position of the shape
	 * @return
	 */
	public int getY() {
		return this.y1;
	}



	/**
	 * Get the color of the shape
	 * @return
	 */
	public Color getColor() {
		return this.shapeColor;
	}

	/**
	 * Get the bounding rectangle of the shape
	 * @return
	 */
	public Rectangle getBounds() {
		return bounds;
	}
	
	/**
	 * Set the bounding rectangle of the shape
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void setBounds(int x, int y, int width, int height) {
		bounds = new Rectangle(x,y,width,height);
		this.x1 = x;
		this.y1 = y;
		this.height = height;
		notifyListeners();
	}
	
	/**
	 * Set the bounding rectangle of the shape
	 * @param rect
	 */
	public void setBounds(Rectangle rect) {
		bounds = new Rectangle(rect);
		notifyListeners();
	}

	/**
	 * Add a table model to the listeners
	 * @param tableModel
	 */
	public void addTableListener(TableModel tableModel) {
		this.list.add(tableModel);		
	}

	/**
	 * Remove a table model from the listeners
	 * @param tableModel
	 */
	public void removeTableListener(TableModel tableModel) {
		if(this.list.contains(tableModel))
		{
			this.list.remove(tableModel);

		}
	}
	
	/**
	 * Get the ID of the model
	 * @return ID
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * Set the ID of the model
	 * @param ID
	 */
	public void setID(int ID) {
		this.ID = ID;
	}
	
	/**
	 * Copies the model of this DShapeModel to the model
	 * @param model
	 */
	public void mimic(DShapeModel model) {
		setID(model.getID());
		setBounds(model.getBounds());
		setColor(model.getColor());
		notifyListeners();
	}

	/**
	 * Mark the shape for removal
	 */
	public void markForRemoval() {
		markedForRemoval = true;
		notifyListeners();
	}
	
	/**
	 * Set anchor and cursor for model when resizing
	 */
	public void modifyWithPoints(Point anchor, Point cursor) {
		int x = (anchor.x < cursor.x ? anchor.x : cursor.x);
		int y = (anchor.y < cursor.y ? anchor.y : cursor.y);
		int width = Math.abs(anchor.x - cursor.x);
		int height = Math.abs(anchor.y - cursor.y);
		setBounds(new Rectangle(x,y,width,height));
	}
	
	/**
	 * Check if the shape is marked for removal
	 */
	public boolean markedForRemoval() {
		return markedForRemoval;
	}
}
