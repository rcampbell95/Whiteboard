package view;

import java.awt.Graphics;
import java.awt.Rectangle;

import model.DRectModel;
import model.DShapeModel;

public class DRect extends DShape {

	/**
	 * Constructor
	 * @param model
	 * @param canvas
	 */
	public DRect(DShapeModel model, Canvas canvas) {
		super(model, canvas);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Draw a DRect
	 * @param g
	 * @param selected
	 */
	@Override
	public void draw(Graphics g, boolean selected) {
		//Rectangle shape = new Rectangle(model.getX(), model.getY(), model.getWidth(), model.getHeight());
		g.setColor(model.getColor());
		Rectangle bounds = model.getBounds();
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		//((Graphics2D)g).draw(shape);
		if(selected) {
			drawKnobs(g);
		}
	}

	/**
	 * Return DRectModel
	 * @return
	 */
	@Override
	public DRectModel getModel() {
		return (DRectModel) model;
	}

}
