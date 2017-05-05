package model;

public class DLineModel extends DShapeModel {
	private int x2;
	private int y2;
	@Override
	public int getWidth() {
		return Math.abs(x2 - x1);
	}
	@Override
	public int getHeight() {
		return Math.abs(y2 - y1);
	}
	
}