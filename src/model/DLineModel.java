package model;

public class DLineModel extends DShapeModel {
	private int x2;
	private int y2;
	@Override
	public int getWidth() {
		// TODO -- change to represent bounding box width
		return Math.abs(x2 - x1);
	}
	@Override
	public int getHeight() {
		// TODO -- change to represent bounding box height
		return Math.abs(y2 - y1);
	}
}