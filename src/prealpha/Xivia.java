package prealpha;

import javax.swing.JFrame;
/**
 * 
 * @author Sigve Solvaag
 * This class starts up the game and initializes the board class in its constructor.
 *
 */
public class Xivia extends JFrame {

	public Xivia() {
		
		add(new Board());
		setTitle("Xivia");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300); // change this later to take global variables
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(true);
	}
	
	public static void main (String[] args) {
		new Xivia();
	}
	
}
