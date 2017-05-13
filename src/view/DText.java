package view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import model.DShapeModel;

public class DText extends DShape {

	public DText(DShapeModel model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(model.getColor());
		g2.drawString(model.getText(),10,10);
	}

	public String getText() {
		return model.getText();
	}
	public void setText(String text) {
		model.setText(text);
	}
}
