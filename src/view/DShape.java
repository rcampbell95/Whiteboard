package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import model.DShapeModel;
import model.DTextModel;
import model.ModelListener;

public abstract class DShape implements ModelListener {
	DShapeModel model;
	ArrayList<Point> knobs;
	Rectangle lastBounds;
	Canvas canvas;
	boolean needsRecomputeKnobs;

	/**
	 * DShape's Constructor
	 * @param model
	 * @param canvas
	 */
	public DShape(DShapeModel model, Canvas canvas) {
		this.model = model;
		this.canvas = canvas;
		knobs = null;
		lastBounds = new Rectangle(getBounds());
		needsRecomputeKnobs = false;
		model.addListener(this);
	}

	/**
	 * Default drawKnobs
	 * @param g
	 */
	public void drawKnobs(Graphics g) {
		g.setColor(Color.BLACK);
		for(Point point: getKnobs()) {
			g.fillRect(point.x - 9/2, point.y - 9/2, 9, 9);
		}
	}

	/**
	 * Return model's bounds
	 * @return model.getBounds()
	 */
	public Rectangle getBounds() {
		return model.getBounds();
	}

	/**
	 * Returns model's bounds including knobs
	 * @return
	 */
	public Rectangle getBigBounds() {
		return getBigBoundsForModel(model);
	}

	/**
	 *
	 * @param model
	 * @return new Rectangle
	 */
	public Rectangle getBigBoundsForModel(DShapeModel model) {
		Rectangle b = model.getBounds();
		return new Rectangle(b.x-9/2,b.y - 9/2,b.width + 9,b.height + 9);
	}

	/**
	 *
	 * @return Rectangle bounds of last position
	 */
	public Rectangle getBigBoundsOfLastPosition() {
		return new Rectangle(lastBounds.x - 9/2,lastBounds.y - 9/2,lastBounds.width + 9,lastBounds.height + 9);
	}

	/**
	 * Move the shape
	 * @param x
	 * @param y
	 */
	public void move(int x, int y) {
		needsRecomputeKnobs = true;
		model.move(x,y);
	}

	/**
	 * Computes knobs
	 * @return ArrayList<Point>
	 */
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

	/**
	 *
	 * @param point
	 * @return Anchor Point for a selected knob
	 */
	public Point getAnchorForSelectedKnob(Point point) {
		int index = getKnobs().indexOf(point);
		return new Point(knobs.get((index + knobs.size() / 2) % knobs.size()));
	}

	/**
	 *
	 * @param point
	 * @param knobCenter
	 * @return if Mouse is within knob
	 */
	public boolean selectedKnob(Point point, Point knobCenter)
	{
		Rectangle knob = new Rectangle(knobCenter.x - 9 / 2, knobCenter.y - 9 / 2, 9, 9);
		return knob.contains(point);
	}

	/**
	 * Abstract draw method
	 * @param g
	 * @param selected
	 */
	public abstract void draw(Graphics g, boolean selected);

	/**
	 * Abstract getter for Model
	 * @return
	 */
	public abstract DShapeModel getModel();

	/**
	 * Getter for Model's ID
	 * @return ID
	 */
	public int getModelID() {
		return model.getID();
	}

	/**
	 * Sets the color of the object
	 * @param c
	 */
	public void setColor(Color c) {
		model.setColor(c);
	}

	/**
	 * Gets the color of the object
	 * @return Color
	 */
	public Color getColor() {
		return model.getColor();
	}

	/**
	 * Returns the text of a DTextModel
	 * @return String
	 */
	public String getText() {
		return ((DTextModel)model).getText();
	}

	/**
	 * Setter for DTextModel's text
	 * @param text
	 */
	public void setText(String text) {
		((DTextModel)model).setText(text);
	}

	/**
	 *
	 * @param point
	 * @return if shape contains a Point
	 */
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

	/**
	 * Adds Anchor and cursor points
	 * @param anchor
	 * @param cursor
	 */
	public void modifyShapeWithPoints(Point anchor, Point cursor) {
		needsRecomputeKnobs = true;
		model.modifyWithPoints(anchor, cursor);
	}

	/**
	 * DShape's communication to Model's markForRemoval
	 */
	public void markForRemoval() {
		model.markForRemoval();
	}

	/**
	 * DShape's default modelChanged
	 * @param model
	 */
	@Override
	public void modelChanged(DShapeModel model) {
		if(this.model == model) {
			if(model.markedForRemoval()) {
				canvas.removeShape(this);
				return;
			}
			canvas.repaintShape(this);
			if(!lastBounds.equals(getBounds())) {
				canvas.repaintArea(getBigBoundsOfLastPosition());
				lastBounds = new Rectangle(getBounds());
			}
		}
	}
}
