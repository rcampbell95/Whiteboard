package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;

import model.DLineModel;
import model.DOvalModel;
import model.DRectModel;
import model.DShapeModel;
import model.DTextModel;
import model.TableModel;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.image.BufferedImage;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;

import model.DLineModel;
import model.DOvalModel;
import model.DRectModel;
import model.DShapeModel;
import model.DTextModel;
import model.ModelListener;
import model.TableModel;

public class Canvas extends JPanel implements ModelListener 
{

	
	private static final long serialVersionUID = 1L;
	private Whiteboard whiteboard;
	TableModel model;
	DShape selected;
	ArrayList<DShape> shapes;
	JTable tablePane = new JTable(model);
	JFileChooser fileChooser;
	String textInput;
	JFormattedTextField text2;


	private int lastX;
	private int lastY;
	private Point movingPoint;
	private Point anchorPoint;
	JComboBox<String> comboBox;
	

	/**
	 * The constructor for canvas
	 * @param board the whiteboard that canvas is being added to
	 */
	public Canvas(Whiteboard board)
	{
		selected = null;
		movingPoint = null;
		whiteboard = board;
		shapes = new ArrayList<DShape>();
		setMinimumSize(new Dimension(400,400));
		setPreferredSize(getMinimumSize());
		setBackground(Color.WHITE);
		

		addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
			if(whiteboard.isNotClient())
				selectObjectForClick(e.getPoint());
			}

		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if(whiteboard.isNotClient()) {
					int dx = e.getX() - lastX;
					int dy = e.getY() - lastY;
					lastX = e.getX();
					lastY = e.getY();
					if (movingPoint != null) {
						movingPoint.x += dx;
						movingPoint.y += dy;
						selected.modifyShapeWithPoints(anchorPoint, movingPoint);
					} else if (selected != null) {
						selected.move(dx, dy);
					}
				}
			}
		});
	}
	/**
	 * Opens the dialogue to save canvas
	 */
	private void saveCanvas() {
		int retVal = fileChooser.showSaveDialog(this);
		if(retVal == JFileChooser.APPROVE_OPTION) {
			this.saveCanvas(fileChooser.getSelectedFile());
		}
	}
	/**
	 * Saves canvas as an XML file
	 * @param file the file that canvas is being saved to
	 */
	public void saveCanvas(File file) {
		try {
			XMLEncoder xmlOut = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));
			DShapeModel[] shapeModels = getShapeModels().toArray(new DShapeModel[0]);
			xmlOut.writeObject(shapeModels);
			xmlOut.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Sends the selected instance variable
	 * to the front using moveToFront
	 */
	public void moveSelectedToFront() {
	    moveToFront(selected);
    }

	/**
	 * Takes the selected shape and 
	 * marks it for removal
	 */
    public void markSelectedShapeForRemoval() {
	    markForRemoval(selected);
	    selected = null;
    }
    /**
     * Takes the given shape, removes its listener
     * and marks for removal
     * @param shape the DShape that is to be marked for removal
     */
    public void markForRemoval(DShape shape) {
        shape.getModel().removeListener(this);
        shape.markForRemoval();
	}
    /**
     * Moves the given shape in front of overlapping shape
     * @param object the given object that is to be moved to the front
     */
	public void moveToFront(DShape object) {

		if(!shapes.isEmpty() && shapes.remove(object)) {
			shapes.add(object);
		}
		whiteboard.didMoveToFront(object);
		if(whiteboard.isServer())
			whiteboard.doSend(Whiteboard.Message.FRONT, object.getModel());
		repaintShape(object);
	}
	/**
	 * Sends the selected object behind any DShapes 
	 * it may be overlapping using moveToBack method
	 * 
	 */
	public void moveSelectedToBack() {
	    moveToBack(selected);
    }
	/**
	 * Moves the given object behind any DShapes it may be overlapping
	 * @param object the given object that is to be moved to the front
	 */
	public void moveToBack(DShape object) {

		if (!shapes.isEmpty() && shapes.remove(object)){
			shapes.add(0, object);
		}
		whiteboard.didMoveToBack(object);
		if(whiteboard.isServer())
			whiteboard.doSend(Whiteboard.Message.BACK,object.getModel());
		repaintShape(object);
	}
	/**
	 * If the selected is a DText, sets the selected's 
	 * text equal to the given String text
	 * @param text the String that is to be selected's text
	 */
	public void setTextForSelected(String text) {
		if(selected instanceof DText) {
			((DText)selected).setText(text);
		}
	}
	/**
	 * Finds and returns the shape that has
	 * a specific ID
	 * @param ID the ID of the shape being searched
	 * @return shape the shape with the corresponding ID
	 */
	public DShape getShapeWithID(int ID) {
		for(DShape shape : shapes) {
			if(shape.getModelID() == ID) {
				return shape;
			}
		}
		return null;
	}
	/**
	 * Adds a shape to the ArrayList of canvas' shapes
	 * @param model the model type of the shape that is being added
	 */
	public void addShape(DShapeModel model)
	{
		//System.out.println(model.getX() + " " + model.getY() + " " + model.getWidth() + " " + model.getHeight());

		if(whiteboard.isNotClient()) {
			model.setID(Whiteboard.getNextIDNumber());
		}
		if(selected != null) {
			repaintShape(selected);

		}
		DShape shape = null;
		if (model instanceof DRectModel)
		{
			shape = new DRect(model, this);


			shapes.add(shape);
			//model.setBounds(10, 10, 20, 30);

		} else if (model instanceof DOvalModel)
		{
			shape = new DOval(model, this);

			shapes.add(shape);

		} else if (model instanceof DLineModel)
		{

			shape = new DLine(model,this);

			shapes.add(shape);
		} else if (model instanceof DTextModel)
		{
			shape = new DText(model,this);
			//System.out.println(text2.getText());

			
			shapes.add(shape);
		}
		model.addListener(this);

		whiteboard.addToTable(shape);
		if(whiteboard.isNotClient()) {
			selected = shape;
		}
		if(whiteboard.isServer()) {
			whiteboard.doSend(Whiteboard.Message.ADD, model);
		}
		repaintShape(shape);
		
	}
	/**
	 * Sets the selected's color to a given color
	 * @param c the color being set to selected
	 */
	public void setSelectedColor(Color c) {
		selected.setColor(c);
	}
	/**
	 * Returns the selected instance variable
	 * @return selected
	 */
	public DShape getSelected() {
	    return selected;
    }
	/**
	 * Sets the font for the selected instance variable
	 * if said selected is of type DText
	 * @param fontName the name of the font being set to selected
	 */
	public void setFontForSelected(String fontName) {
		if(selected instanceof DText) {
			((DText) selected).setFontName(fontName);
		}
	}
	/**
	 * Checks if selected is null
	 * @return 
	 */
	public boolean hasSelected() {
		return selected != null;
	}
	/**
	 * Checks if the mouse has clicked a shape and sets
	 * that shape to the selected
	 * @param pt the given point of mouse click
	 */
	public void selectObjectForClick(Point pt)
	{
		lastX = pt.x;
		lastY = pt.y;
		movingPoint = null;
		anchorPoint = null;
		if(selected != null) {
			for(Point point: selected.getKnobs()) {
				if(selected.selectedKnob(pt, point)) {
					movingPoint = new Point(point);
					anchorPoint = selected.getAnchorForSelectedKnob(point);
					break;
				}
			}
		} if(movingPoint == null) {
			selected = null;
			for(DShape shape: shapes) {
				if(shape.containsPoint(pt)) {
					selected = shape;
				}
			}
		}
		if(selected != null && whiteboard.isServer()) {
			whiteboard.doSend(Whiteboard.Message.CHANGE, selected.getModel());
		}
		whiteboard.updateTableSelection(selected);
		repaint();
	}
	/**
	 * Repaints an area of canvas within a given bound
	 * @param bounds the rectangle bounds to be repainted
	 */
	public void repaintArea(Rectangle bounds) {
		repaint(bounds);
		
	}
	/**
	 * Removes a shape from the canvas
	 * @param shape the shape to be removed
	 */
	public void removeShape(DShape shape) {
		shapes.remove(shape);
		whiteboard.didRemove(shape);
		if(whiteboard.isServer())
			whiteboard.doSend(Whiteboard.Message.REMOVE, shape.getModel());
		repaintArea(shape.getBigBounds());
	}
	/**
	 * Repaints a shape into the canvas
	 * @param shape the given shape that is to be repainted
	 */
	public void repaintShape(DShape shape) {
		if(shape == selected) {
			repaint(shape.getBigBounds());
			
		} else {
			repaint(shape.getBounds());
			
		}
	}
	/**
	 * Updates the table with the selected
	 * @param selected the shape tablePane is being updated with
	 */
	public void updateTableSelection(DShape selected) {
	    tablePane.clearSelection();
	    if(selected != null) {
	        int index = model.getRowForModel(selected.getModel());
	        tablePane.setRowSelectionInterval(index,index);
        }
    }
	/**
	 * Removes a shapes model from the table model
	 * @param shape the shape whose model is being deleted
	 */
    public void didRemove(DShape shape) {
	    model.removeModel(shape.getModel());
	    updateTableSelection(null);
    }
    /**
     * Clears the tablePane
     */
    public void clearTable() {
	    updateTableSelection(null);
	    model.clearTable();
    }
    /**
     * Rearranges the order of the table
     * if a shape is moved to front
     * @param shape the shape being moved to front
     */
    public void didMoveToFront(DShape shape) {
	    model.moveToFront(shape.getModel());
	    updateTableSelection(shape);
    }
    /**
     * Rearranges the order of the table
     * if a shape is moved to back
     * @param shape the shape being moved to back
     */
    public void didMoveToBack(DShape shape) {
		model.moveToBack(shape.getModel());
		updateTableSelection(shape);
	}
    /**
     * Returns the ArrayList of shapes on the canvas
     * @return shapes
     */
    public ArrayList<DShape> getShapes() {
		return shapes;
	}
    /**
     * Displays the file saver
     */
	public void saveImage() {
		int retVal = fileChooser.showSaveDialog(this);
		if(retVal == JFileChooser.APPROVE_OPTION) {
			saveImage(fileChooser.getSelectedFile());
		}
	}
	/**
	 * Saves the canvas as a PNG file
	 * @param file the file the canvas png is being saved to
	 */
	public void saveImage(File file) {
		DShape wasSelected = selected;
		selected = null;
		BufferedImage image = (BufferedImage) createImage(getWidth(),getHeight());
		Graphics g = image.getGraphics();
		paintAll(g);
		g.dispose();
		try {
			javax.imageio.ImageIO.write(image, "PNG", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		selected = wasSelected;
	}
	/**
	 * Displays the file open dialogue
	 */
	public void openCanvas() {
		int retVal = fileChooser.showOpenDialog(this);
		if(retVal == JFileChooser.APPROVE_OPTION) {
			openCanvas(fileChooser.getSelectedFile());
		}
	}
	/**
	 * Opens a previously saved canvas file
	 * @param file the canvas file being opend
	 */
	public void openCanvas(File file) {
		markAllForRemoval();
		try {
			XMLDecoder xmlIn = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));
			DShapeModel[] shapeModels = (DShapeModel[]) xmlIn.readObject();
			xmlIn.close();
			for(DShapeModel shapeModel: shapeModels) {
				addShape(shapeModel);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Marks all of canvas' shapes for removal
	 */
	public void markAllForRemoval() {
		selected = null;
		for(int i = shapes.size() - 1;i>=0;i--) {
			markForRemoval(shapes.get(i));
		}
	}
	/**
	 * Returns the models of all the shapes on canvas
	 * @return models
	 */
	public ArrayList<DShapeModel> getShapeModels() {
		ArrayList<DShapeModel> models = new ArrayList<>();
		for(DShape shape : shapes) {
			models.add(shape.getModel());
		}
		return models;
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(DShape shape: shapes) {
			shape.draw(g,(selected == shape));
		}
	}

	@Override
	public void modelChanged(DShapeModel model) {
		if(whiteboard.isServer() && !model.markedForRemoval())
			whiteboard.doSend(Whiteboard.Message.CHANGE, model);
	}
}
