package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import model.DLineModel;
import model.DOvalModel;
import model.DRectModel;
import model.DShapeModel;
import model.DTextModel;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;


import javax.swing.JLabel;
public class Canvas extends JPanel{


	DefaultTableModel model;
	DShape selected;
	ArrayList<DShape> shapes = new ArrayList<>();
	

	
	JPanel east;


	public Canvas(){
		shapes = new ArrayList<DShape>();

		this.setLayout(new BorderLayout());
		this.setSize(400, 400);		
		this.setBackground(Color.WHITE);
		JPanel west = new JPanel();
		east = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				for(Iterator<DShape> i = shapes.iterator();i.hasNext();) {
						DShape shape = i.next();
						shape.draw(g);
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
		
		east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
		JButton b = new JButton("b");
		b.setVisible(false);
		east.add(b);
		
		east.setBackground(Color.WHITE);
		
		this.add(east, BorderLayout.CENTER);
		this.add(west, BorderLayout.WEST);

		for(Component comp : west.getComponents())
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
		rect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DShapeModel model = new DRectModel();
				addShape(model);
			}
			
		});
		rect.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JButton oval = new JButton("oval");
		
		oval.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					DShapeModel model = new DOvalModel();
					addShape(model);
				}
		});
		
		oval.setBorder(new EmptyBorder(10, 10, 10, 10));

		JButton line = new JButton("line");
		line.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("line");
			}
		});
		//line.setBorder(new EmptyBorder(10, 10, 10, 10));
		JButton text = new JButton("text");	
		text.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("text");
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
		buttonPane2.add(setColor);
		buttonPane2.setBorder(new EmptyBorder(10, 10, 10, 10));
		buttonPane2.setBackground(Color.WHITE);
		pan.add(buttonPane2);
	}
	
	private void addButtonPane3(JPanel pan)
	{
		JPanel buttonPane3 = new JPanel();
		buttonPane3.setLayout(new BoxLayout(buttonPane3, BoxLayout.X_AXIS));
		JTextField text2 = new JTextField("Whiteboard!");
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fonts= ge.getAvailableFontFamilyNames();
		JComboBox <String> comboBox = new JComboBox<>(fonts);	
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
		JButton moveToBack= new JButton("Move to Back");
		JButton removeSHape = new JButton("Remove Shape");
		buttonPane4.add(moveToFront);
		buttonPane4.add(moveToBack);
		buttonPane4.add(removeSHape);
		buttonPane4.setBorder(new EmptyBorder(10, 10, 10, 10));
		buttonPane4.setBackground(Color.WHITE);
		pan.add(buttonPane4);
	}

	private void addTablePane(JPanel pan)
	{
		
		String [] str = { "X", "Y", "Width", "Height"};
		
		model = new DefaultTableModel(0,4){
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		
		model.setColumnIdentifiers(str);
		JTable tablePane = new JTable(model);		
		tablePane.setLayout(new BoxLayout(tablePane, BoxLayout.X_AXIS));	
		JTableHeader head = tablePane.getTableHeader();
		head.setBackground(Color.GRAY);
		tablePane.setBackground(Color.WHITE);
		tablePane.setVisible(true);
		pan.add(new JScrollPane(tablePane));
	}
	
	private void addShape(DShapeModel model) {
		if(model instanceof DRectModel) {
			shapes.add(new DRect(model));
		}
		else if(model instanceof DOvalModel) {
			shapes.add(new DOval(model));
		}
		else if(model instanceof DLineModel) {
			shapes.add(new DLine(model));
		}
		else if(model instanceof DTextModel) {
			shapes.add(new DText(model));
		}
		east.repaint();
		
	}
}
