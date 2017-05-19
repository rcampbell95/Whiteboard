package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import model.DShapeModel;
import model.DTextModel;

public class DText extends DShape {

	public DText(DShapeModel model, Canvas canvas) {
		super(model, canvas);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g,boolean selected) {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(model.getColor());
		Rectangle bounds = model.getBounds();
		//g2.drawString(model.getText(),bounds.x,bounds.y);
		if(selected) {
			drawKnobs(g);
		}
	}

	public String getText() {
		return getModel().getText();
	}
	public void setText(String text) {
		getModel().setText(text);
	}
	@Override
	public DTextModel getModel() {
		return (DTextModel)model;
	}
}
