package view;

import model.DShapeModel;

import java.awt.BorderLayout;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;

public class Whiteboard extends JFrame {
	Canvas canvas;
	JFileChooser fileChooser;

	private ArrayList<ObjectOutputStream> outputs = new ArrayList<>();
	public static void main(String[] args) {
		
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
		
	}
}
