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

	/**
	 * 
	 */
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
				
				selectObjectForClick(e.getPoint());
			}

		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				int dx = e.getX() - lastX;
				int dy = e.getY() - lastY;
				lastX = e.getX();
				lastY = e.getY();
				if(movingPoint != null) {
					movingPoint.x += dx;
					movingPoint.y += dy;
					selected.modifyShapeWithPoints(anchorPoint, movingPoint);
				} else if(selected != null) {
					selected.move(dx,dy);
				}
			}
		});
	}

	private void saveCanvas() {
		int retVal = fileChooser.showSaveDialog(this);
		if(retVal == JFileChooser.APPROVE_OPTION) {
			this.saveCanvas(fileChooser.getSelectedFile());
		}
	}
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

	public void moveSelectedToFront() {
	    moveToFront(selected);
    }


    public void markSelectedShapeForRemoval() {
	    markForRemoval(selected);
	    selected = null;
    }
    public void markForRemoval(DShape shape) {
        shape.getModel().removeListener(this);
        shape.markForRemoval();
	}
	public void moveToFront(DShape object) {
	    whiteboard.didMoveToFront(object);
		if(!shapes.isEmpty() && shapes.remove(object)) {
			shapes.add(object);
			
		}
		repaintShape(object);
	}

	public void moveSelectedToBack() {
	    moveToBack(selected);
    }

	public void moveToBack(DShape object) {

		if (!shapes.isEmpty() && shapes.remove(object)){
			shapes.add(0, object);
		}
		whiteboard.didMoveToBack(object);
		repaintShape(object);
	}


	public void addShape(DShapeModel model)
	{
		//System.out.println(model.getX() + " " + model.getY() + " " + model.getWidth() + " " + model.getHeight());
		
		
		if(selected != null) {
			repaintShape(selected);

		}
		DShape shape = null;
		if (model instanceof DRectModel)
		{
			shape = new DRect(model, this);

			selected = shape;
			shapes.add(shape);
			//model.setBounds(10, 10, 20, 30);

		} else if (model instanceof DOvalModel)
		{
			shape = new DOval(model, this);
			selected = shape;
			shapes.add(shape);

		} else if (model instanceof DLineModel)
		{

			shape = new DLine(model,this);
			selected = shape;
			shapes.add(shape);
		} else if (model instanceof DTextModel)
		{
			shape = new DText(model,this);
			//System.out.println(text2.getText());
			selected = shape;
			
			shapes.add(shape);
		}
		model.addListener(this);

		whiteboard.addToTable(shape);
		repaintShape(shape);
		
	}

	public void setSelectedColor(Color c) {
		selected.setColor(c);
	}

	public DShape getSelected() {
	    return selected;
    }

	public boolean hasSelected() {
		return selected != null;
	}

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
		whiteboard.updateTableSelection(selected);
		repaint();
	}
	public void repaintArea(Rectangle bounds) {
		repaint(bounds);
		
	}
	public void removeShape(DShape shape) {
		shapes.remove(shape);
		whiteboard.didRemove(shape);
		repaintArea(shape.getBigBounds());
	}
	public void repaintShape(DShape shape) {
		if(shape == selected) {
			repaint(shape.getBigBounds());
			
		} else {
			repaint(shape.getBounds());
			
		}
	}

	public void updateTableSelection(DShape selected) {
	    tablePane.clearSelection();
	    if(selected != null) {
	        int index = model.getRowForModel(selected.getModel());
	        tablePane.setRowSelectionInterval(index,index);
        }
    }
    public void didRemove(DShape shape) {
	    model.removeModel(shape.getModel());
	    updateTableSelection(null);
    }
    public void clearTable() {
	    updateTableSelection(null);
	    model.clearTable();
    }
    public void didMoveToFront(DShape shape) {
	    model.moveToFront(shape.getModel());
	    updateTableSelection(shape);
    }
    public void didMoveToBack(DShape shape) {
		model.moveToBack(shape.getModel());
		updateTableSelection(shape);
	}
    public ArrayList<DShape> getShapes() {
		return shapes;
	}
	public void saveImage() {
		int retVal = fileChooser.showSaveDialog(this);
		if(retVal == JFileChooser.APPROVE_OPTION) {
			saveImage(fileChooser.getSelectedFile());
		}
	}
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

	public void openCanvas() {
		int retVal = fileChooser.showOpenDialog(this);
		if(retVal == JFileChooser.APPROVE_OPTION) {
			openCanvas(fileChooser.getSelectedFile());
		}
	}
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

	public void markAllForRemoval() {
		selected = null;
		for(int i = shapes.size() - 1;i>=0;i--) {
			markForRemoval(shapes.get(i));
		}
	}
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
		validate();
		repaint();
	}
}
