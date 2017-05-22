package view;

import model.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;

public class Whiteboard extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;



	private JFileChooser fileChooser;
	private JComboBox comboBox;
	private JFormattedTextField textField;
	private Box allControls;
	private Canvas canvas;
	private TableModel tableModel;
	private JTable table;
	private JButton rectButton, ovalButton, lineButton, textButton,setColorButton, moveFrontButton,
			moveBackButton, removeButton, saveImageButton, saveCanvasButton,openCanvasButton,
			startServerButton, startClientButton;
	int UPPER_BOUND = 75;
	int LOWER_BOUND = 25;
	int CANVAS_SIZE = 400;
	Random randGen = new Random();
	protected int width;
	protected int height;
	protected int x1, x2;
	protected int y1, y2;
	private ArrayList<ObjectOutputStream> outputs = new ArrayList<>();
	private ArrayList<JComponent> disableG = new ArrayList<>();
	private static final int NORMAL_MODE = 0;
	private static final int SERVER_MODE = 1;
	private static final int CLIENT_MODE = 2;

	public Whiteboard() {
		allControls = Box.createVerticalBox();
		disableG = new ArrayList<>();
		setLayout(new BorderLayout());
		addShapesBox();
		addSetColorBox();
		addTextBox();
		addMoveBox();
		addSaveBox();
		addServerBox();
		addTable();

		alignControls();
		add(allControls,BorderLayout.WEST);
		setUpCanvas();

		setTitle("Whiteboard");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		//setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);


	}
	public static void main(String[] args) {
		new Whiteboard();
		/*
		Whiteboard whiteboard = new Whiteboard();
		whiteboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		whiteboard.setSize(800,400);
		whiteboard.setLocationRelativeTo(null);
		whiteboard.setLayout(new BorderLayout());
		whiteboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		whiteboard.setTitle("Whiteboard");
		whiteboard.canvas = new Canvas();
		whiteboard.add(whiteboard.canvas, BorderLayout.CENTER);
		whiteboard.canvas.setVisible(true);
		whiteboard.setVisible(true);
		*/
	}

	public void addShapesBox() {
		Box horizontalBox = Box.createHorizontalBox();

		rectButton = new JButton("Rect");
		ovalButton = new JButton("Oval");
		lineButton = new JButton("Line");
		textButton = new JButton("Text");
		rectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addShape(new DRectModel());
			}
		});
		ovalButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addShape(new DOvalModel());
			}
		});
		lineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addShape(new DLineModel());
			}
		});
		textButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DTextModel model = new DTextModel();
				model.setText(textField.getText());
				model.setFontName(comboBox.getSelectedItem().toString());
				model.setFont(comboBox.getSelectedItem().toString(), 1);
				addShape(model);
			}
		});

		horizontalBox.add(rectButton);
		horizontalBox.add(ovalButton);
		horizontalBox.add(lineButton);
		horizontalBox.add(textButton);

		allControls.add(horizontalBox, BorderLayout.WEST);
		disableG.add(rectButton);
		disableG.add(ovalButton);
		disableG.add(lineButton);
		disableG.add(textButton);
	}

	public void addSetColorBox() {
		Box horizontalBox = Box.createHorizontalBox();

		setColorButton = new JButton("Set Color");
		setColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(canvas.hasSelected()) {
					Color newColor = JColorChooser.showDialog(Whiteboard.this,"Select Shape Color",canvas.getSelected().getColor());
					if(newColor != null && !newColor.equals(canvas.getSelected().getColor())) {
						canvas.setSelectedColor(newColor);
					}
				}
			}
		});

		horizontalBox.add(setColorButton);
		allControls.add(horizontalBox, BorderLayout.WEST);
		disableG.add(setColorButton);
	}

	public void addTextBox() {
		Box horizontalBox = Box.createHorizontalBox();

		textField = new JFormattedTextField("Whiteboard!");
		textField.setMaximumSize(new Dimension(300,40));

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fonts = ge.getAvailableFontFamilyNames();
		comboBox = new JComboBox<>(fonts);
		comboBox.setBackground(Color.WHITE);
		textField.setFont(comboBox.getFont());

		horizontalBox.add(textField);
		horizontalBox.add(comboBox);
		allControls.add(horizontalBox, BorderLayout.WEST);

		disableG.add(textField);

	}

	public void addMoveBox() {
		Box horizontalBox = Box.createHorizontalBox();

		moveFrontButton = new JButton("Move to Front");
		moveBackButton = new JButton("Move to Back");
		removeButton = new JButton ("Remove Shape");

		moveFrontButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.moveSelectedToFront();
			}
		});
		moveBackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.moveSelectedToBack();
			}
		});

		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(canvas.hasSelected()) {
					canvas.markSelectedShapeForRemoval();
				}
			}
		});

		horizontalBox.add(moveFrontButton);
		horizontalBox.add(moveBackButton);
		horizontalBox.add(removeButton);

		allControls.add(horizontalBox);

		disableG.add(textField);
		disableG.add(comboBox);
		disableG.add(moveFrontButton);
		disableG.add(moveBackButton);
		disableG.add(removeButton);
	}

	public void addSaveBox() {
		Box horizontalBox = Box.createHorizontalBox();

		saveImageButton = new JButton("Save Image");
		saveCanvasButton = new JButton("Save Canvas");
		openCanvasButton = new JButton("Open Canvas");

		saveImageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveImage();
			}
		});
		saveCanvasButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveCanvas();
			}
		});
		openCanvasButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openCanvas();
			}
		});

		horizontalBox.add(saveImageButton);
		horizontalBox.add(saveCanvasButton);
		horizontalBox.add(openCanvasButton);

		allControls.add(horizontalBox);

		fileChooser = new JFileChooser();

		disableG.add(openCanvasButton);
	}
	public void addServerBox() {
		Box horizontalBox = Box.createHorizontalBox();

		startServerButton = new JButton("Start Server");
		startClientButton = new JButton("Start Client");

		startServerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		startClientButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		horizontalBox.add(startServerButton);
		horizontalBox.add(startClientButton);

		allControls.add(horizontalBox);

		disableG.add(startServerButton);
		disableG.add(startClientButton);
	}
	private void saveImage() {
		int retVal = fileChooser.showSaveDialog(this);
		if(retVal == JFileChooser.APPROVE_OPTION) {
			canvas.saveImage(fileChooser.getSelectedFile());
		}
	}
	private void saveCanvas() {
		int retVal = fileChooser.showSaveDialog(this);
		if(retVal == JFileChooser.APPROVE_OPTION) {
			canvas.saveCanvas(fileChooser.getSelectedFile());
		}
	}
	private void openCanvas() {
		int retVal = fileChooser.showOpenDialog(this);
		if(retVal == JFileChooser.APPROVE_OPTION) {
			canvas.openCanvas(fileChooser.getSelectedFile());
		}
	}
	public void setUpCanvas() {
		canvas = new Canvas(this);
		add(canvas, BorderLayout.CENTER);

	}
	private void alignControls() {
		for(Component comp: allControls.getComponents()) {
			((JComponent)comp).setAlignmentX(Box.LEFT_ALIGNMENT);
		}
	}
	private void addTable() {
		tableModel = new TableModel();
		table = new JTable(tableModel);
		JTableHeader head = table.getTableHeader();
		head.setBackground(Color.GRAY);
		head.setReorderingAllowed(false);
		head.setResizingAllowed(false);

		table.setTableHeader(head);
		table.setBackground(Color.WHITE);
		table.setVisible(true);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(300,200));
		allControls.add(scrollPane, BorderLayout.WEST);
	}
	public void addToTable(DShape shape) {
		tableModel.addModel(shape.getModel());
		updateTableSelection(shape);
	}

	public void updateTableSelection(DShape selected) {
		table.clearSelection();
		if(selected != null) {
			int index = tableModel.getRowForModel(selected.getModel());
			table.setRowSelectionInterval(index,index);
		}
	}
	public void didMoveToFront(DShape shape) {
		tableModel.moveToFront(shape.getModel());
		updateTableSelection(shape);
	}
	public void didMoveToBack(DShape shape) {
		tableModel.moveToBack(shape.getModel());
		updateTableSelection(shape);
	}

	public void didRemove(DShape shape) {
		tableModel.removeModel(shape.getModel());
		updateTableSelection(null);
	}

	public void addShape(DShapeModel model) {
		width = LOWER_BOUND + randGen.nextInt(UPPER_BOUND);
		height = LOWER_BOUND + randGen.nextInt(UPPER_BOUND);
		x1 = randGen.nextInt(CANVAS_SIZE - width);
		y1 = randGen.nextInt(CANVAS_SIZE - height);
		x2 = randGen.nextInt(CANVAS_SIZE - width);
		y2 = randGen.nextInt(CANVAS_SIZE - height);

		if(model instanceof DLineModel) {
			((DLineModel)model).modifyWithPoints(new Point(x1,y1),new Point(x2,y2));
		} else {
			model.setBounds(x1,y2,width,height);
		}
		canvas.addShape(model);
	}


	public static class Message {
		public static final int ADD = 0;
		public static final int REMOVE = 1;
		public static final int FRONT = 2;
		public static final int BACK = 3;
		public static final int CHANGE = 4;

		public int command;
		public DShapeModel model;

		public Message() {
			command = -1;
			model = null;
		}
		public Message(int command, DShapeModel message) {
			this.command = command;
			this.model = model;
		}
		public int getCommand() {
			return command;
		}
		public void setCommand(int cmd) {
			command = cmd;
		}
		public DShapeModel getModel() {
			return model;
		}
		public void setModel(DShapeModel model) {
			this.model = model;
		}
	}
	
	
 	private class ClientHandler extends Thread {
 		private String name;
 		private int port;
 		
 		public ClientHandler(final String name, final int port) {
 			this.name = name;
 			this.port = port;
 		}
 		
 		public void runt() {
 			try {
 				Socket toServer = new Socket(name, port);
 				ObjectInputStream inStream = new ObjectInputStream(toServer.getInputStream());
 				
 				while(true) {
 					String xmlString = (String) in.readObject();
 					XMLDecoder modelDecoder = new XMLDecoder(new ByteArrayInputStream(xmlString.getBytes());
 					Message message = (Message) modelDecoder.readObject();
 					
 					processMessage(message);
 				}
 			}
 			catch(Exception e) {
 				e.printStackTrace();
 			}
 		}
 	}
 	
 	private class ServerAccepter extends Thread {
 		private int port;
 		
 		public ServerAccepter(int port) {
 			this.port = port;
 		}
 		
 		public void run() {
 			try {
 				Socket toClient = null;
 				ServerSocket serverSocket = new ServerSocket(port);
 				toClient = serverSocket.accept();
 				
 				final ObjectOutputStream out = new ObjectOutputStream(toClient.getOutputStream());
 				
 				if(!outputs.contains(out)) {
 					for(DShape shape : canvas.getShapes()) {
 						try {
 							out.writeObject(getXMLStringForMessage(new Message(Message.ADD, shape.getModel())));
 							out.flush();
 						}
 						catch(Exception e) {
 							e.printStackTrace();
 						}
 					}
 				}
 			}
 		}
 	}
 	
}
