package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
public class Canvas extends JPanel{

	DefaultTableModel model = new DefaultTableModel();
	List<DShape> shapes;

	public Canvas(){
		shapes = new ArrayList<DShape>();

		this.setLayout(new BorderLayout());
		this.setSize(400, 400);		
		this.setBackground(Color.WHITE);
		JPanel west = new JPanel();
		west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
		addButtonPane1(west);
		addButtonPane2(west);
		addButtonPane3(west);
		addButtonPane4(west);
		addTablePane(west);
		west.setBackground(Color.WHITE);
		
		JPanel east = new JPanel();
		east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
		JButton b = new JButton("b");
		b.setVisible(false);
		east.add(b);
		
		east.setBackground(Color.BLACK);
		
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
		rect.setBorder(new EmptyBorder(10, 10, 10, 10));
		JButton oval = new JButton("oval");
		oval.setBorder(new EmptyBorder(10, 10, 10, 10));
		JButton line = new JButton("line");
		line.setBorder(new EmptyBorder(10, 10, 10, 10));
		JButton text = new JButton("text");	
		text.setBorder(new EmptyBorder(10, 10, 10, 10));
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
		buttonPane3.setLayout(new FlowLayout());//(buttonPane3, BoxLayout.X_AXIS));
		JTextField text2 = new JTextField("Whiteboard!");
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fonts= ge.getAvailableFontFamilyNames();
		JComboBox <String> comboBox = new JComboBox<>(fonts);	
		comboBox.setBorder(new EmptyBorder(10, 10, 10, 10));
		comboBox.setBackground(Color.WHITE);
		text2.setFont(comboBox.getFont());
		text2.setBorder(new EmptyBorder(10, 10, 10, 10));
		
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
		int rows = 10;
		DefaultTableModel model = new DefaultTableModel(rows, str.length);
		model.setColumnIdentifiers(str);
		JTable tablePane = new JTable(model);
		
		tablePane.setLayout(new BoxLayout(tablePane, BoxLayout.X_AXIS));	
		JTableHeader head = tablePane.getTableHeader();
		head.setBackground(Color.GRAY);
		tablePane.setBackground(Color.WHITE);
		tablePane.setVisible(true);
		pan.add(new JScrollPane(tablePane));
	}
	
	
	protected void paintComponent(Graphics g) {
		for(Iterator<DShape> i = shapes.iterator();i.hasNext();) {
			DShape shape = i.next();
			shape.draw(g);
		}
	}
}

