package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class Canvas extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TableModel model;
	DShape selected;
	ArrayList<DShape> shapes;
	JPanel east;
	String textInput;
	JTextField text2;
	private int lastX;
	private int lastY;
	private Point movingPoint;
	private Point anchorPoint;
	JComboBox<String> comboBox;
	


	public Canvas()
	{
		shapes = new ArrayList<DShape>();

		this.setLayout(new BorderLayout());
		this.setSize(400, 400);
		this.setBackground(Color.WHITE);
		JPanel west = new JPanel();
		east = new JPanel()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				for (Iterator<DShape> i = shapes.iterator(); i.hasNext(); )
				{
					DShape shape = i.next();
					shape.draw(g, (selected == shape));
				}
			}
		};

		west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
		addButtonPane1(west);
		addButtonPane2(west);
		addButtonPane3(west);
		addButtonPane4(west);
		addTablePane(west);
		west.setBackground(Color.WHITE);

		east.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				int x = e.getX();
				int y = e.getY();
				System.out.println(x + "    " + y);
				selectObjectForClick(e.getPoint());
			}

		});
		
		east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
		JButton b = new JButton("b");
		b.setVisible(false);
		east.add(b);

		east.setBackground(Color.WHITE);

		this.add(east, BorderLayout.CENTER);
		this.add(west, BorderLayout.WEST);

		for (Component comp : west.getComponents())
		{
			((JComponent) comp).setAlignmentX(Box.LEFT_ALIGNMENT);
		}
	}

	private void addButtonPane1(JPanel pan)
	{
		JPanel buttonPane1 = new JPanel();
		buttonPane1.setLayout(new BoxLayout(buttonPane1, BoxLayout.X_AXIS));
		JLabel label = new JLabel("Add: ");
		label.setBorder(new EmptyBorder(10, 10, 10, 10));
		JButton rect = new JButton("rect");
		//rect.setBorder(new EmptyBorder(10, 10, 10, 10));

		rect.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				DShapeModel model = new DRectModel();
				addShape(model);
			}

		});

		JButton oval = new JButton("oval");
		//oval.setBorder(new EmptyBorder(10, 10, 10, 10));
		oval.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				DShapeModel model = new DOvalModel();
				addShape(model);
			}
		});

		JButton line = new JButton("line");
//		line.setBorder(new EmptyBorder(10, 10, 10, 10));
		line.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				DShapeModel model = new DLineModel();
				addShape(model);
			}
		});
		//line.setBorder(new EmptyBorder(10, 10, 10, 10));

		JButton text = new JButton("text");
//		text.setBorder(new EmptyBorder(10, 10, 10, 10));
		text.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				DShapeModel model = new DTextModel();
				addShape(model);
				//System.out.println("text");
			}
		});
		//text.setBorder(new EmptyBorder(10, 10, 10, 10));
		buttonPane1.add(label);
		buttonPane1.add(rect);
		buttonPane1.add(oval);
		buttonPane1.add(line);
		buttonPane1.add(text);
		buttonPane1.setBorder(new EmptyBorder(10, 10, 10, 10));
		buttonPane1.setBackground(Color.WHITE);
		pan.add(buttonPane1);

	}

	private void addButtonPane2(JPanel pan)
	{
		JPanel buttonPane2 = new JPanel();
		buttonPane2.setLayout(new BoxLayout(buttonPane2, BoxLayout.X_AXIS));
		JButton setColor = new JButton("Set Color");
		setColor.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (e.getSource() == setColor)
				{
					if (selected != null)
					{
						Color initialColor = Color.CYAN;
						Color color = JColorChooser.showDialog(buttonPane2, "Select a color", initialColor);
						System.out.println(color);
						if(color != null && !color.equals(selected.getColor())) {
							setSelectedColor(color);
						}
						east.repaint();
					}
				}
			}
		});

		buttonPane2.add(setColor);
		buttonPane2.setBorder(new EmptyBorder(10, 10, 10, 10));
		buttonPane2.setBackground(Color.WHITE);
		pan.add(buttonPane2);
	}

	private void addButtonPane3(JPanel pan)
	{
		JPanel buttonPane3 = new JPanel();
		buttonPane3.setLayout(new BoxLayout(buttonPane3, BoxLayout.X_AXIS));
		text2 = new JTextField("Whiteboard!");
		text2.setMaximumSize(new Dimension(300,40));

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fonts = ge.getAvailableFontFamilyNames();
		comboBox = new JComboBox<>(fonts);
		//comboBox.setBorder(new EmptyBorder(10, 10, 10, 10));
		comboBox.setBackground(Color.WHITE);
		text2.setFont(comboBox.getFont());
		//text2.setBorder(new EmptyBorder(10, 10, 10, 10));

		buttonPane3.add(text2);
		buttonPane3.add(comboBox);
		buttonPane3.setBorder(new EmptyBorder(10, 10, 10, 10));
		buttonPane3.setBackground(Color.WHITE);
		pan.add(buttonPane3);
	}

	private void addButtonPane4(JPanel pan)
	{

		JPanel buttonPane4 = new JPanel();
		buttonPane4.setLayout(new BoxLayout(buttonPane4, BoxLayout.X_AXIS));
		JButton moveToFront = new JButton("Move to Front");
		moveToFront.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				moveToFront(selected);
			}
		});
		JButton moveToBack = new JButton("Move to Back");
		moveToBack.addActionListener(new ActionListener()
		 {
			 @Override
			 public void actionPerformed(ActionEvent e)
			 {
			 	moveToBack(selected);
			 }
		 });
		 JButton removeShape = new JButton("Remove Shape");
		 buttonPane4.add(moveToFront);
		 buttonPane4.add(moveToBack);
		 buttonPane4.add(removeShape);
		 buttonPane4.setBorder(new EmptyBorder(10, 10, 10, 10));
		 buttonPane4.setBackground(Color.WHITE);
		 pan.add(buttonPane4);
	}

	public void moveToFront(DShape object) {
		if(!shapes.isEmpty() && shapes.remove(object)) {
			shapes.add(object);
		}
		repaint(object);
	}

	public void moveToBack(DShape object) {
		if (!shapes.isEmpty() && shapes.remove(object)){
			shapes.add(0,object);
		}
		repaint(object);
	}

	public void repaint(DShape object) {
		repaint(object.getBounds());
	}


	private void addTablePane(JPanel pan)
	{

		String[] str = {"X", "Y", "Width", "Height"};
		model = new TableModel();
		
		//model.setColumnIdentifiers(str);
		JTable tablePane = new JTable(model);

		tablePane.setLayout(new BoxLayout(tablePane, BoxLayout.X_AXIS));

		JTableHeader head = tablePane.getTableHeader();
		head.setBackground(Color.GRAY);
		tablePane.setBackground(Color.WHITE);
		tablePane.setVisible(true);
		pan.add(new JScrollPane(tablePane));
	}


	private void addShape(DShapeModel model)
	{
		//System.out.println(model.getX() + " " + model.getY() + " " + model.getWidth() + " " + model.getHeight());
		DShape shape;
		if (model instanceof DRectModel)
		{
			shape = new DRect(model);
			selected = shape;
			shapes.add(shape);

		} else if (model instanceof DOvalModel)
		{
			shape = new DOval(model);
			selected = shape;
			shapes.add(shape);

		} else if (model instanceof DLineModel)
		{
			shape = new DLine(model);
			selected = shape;
			shapes.add(shape);
		} else if (model instanceof DTextModel)
		{
			shape = new DText(model);
			//System.out.println(text2.getText());
			((DTextModel)model).setText(text2.getText());
			((DText)shape).setFont(comboBox.getSelectedItem().toString(), 1);
			selected = shape;
			shapes.add(shape);
		}
		east.repaint();
	}

	public void setSelectedColor(Color c) {
		selected.setColor(c);
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
		repaint();
	}
}
