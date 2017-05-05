package model;

public interface ModelListener {
	/*
	Interface to listen for shape change notifications.
	The modelChanged() notification includes a pointer to
	the model that changed. There is not detail about
	what the exact change
	was. */
	public void modelChanged(DShapeModel model);
}
