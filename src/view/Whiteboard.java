package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Whiteboard extends JFrame {

	public static void main(String[] args) {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Whiteboard whiteboard = new Whiteboard();
		whiteboard.setSize(400,400);
		whiteboard.setLayout(new BorderLayout());
		whiteboard.setTitle("Whiteboard");
		Canvas canvas = new Canvas();
		whiteboard.add(canvas, BorderLayout.CENTER);
		canvas.setVisible(true);		
		whiteboard.setVisible(true);
		
		
	}

}
