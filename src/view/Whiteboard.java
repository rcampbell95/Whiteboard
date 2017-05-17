package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Whiteboard extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		
		Whiteboard whiteboard = new Whiteboard();
		whiteboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		whiteboard.setSize(800,400);
		whiteboard.setLocationRelativeTo(null);
		whiteboard.setLayout(new BorderLayout());
		whiteboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		whiteboard.setTitle("Whiteboard");
		Canvas canvas = new Canvas();
		whiteboard.add(canvas, BorderLayout.CENTER);
		canvas.setVisible(true);		
		whiteboard.setVisible(true);
		
	}

}
