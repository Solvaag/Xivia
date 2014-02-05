package prealpha;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {

	/**
	 * Class fields. 
	 * includes player class,  timer, and ingame bool.
	 * Also an arraylist for tiles and a tileclass declaration. 
	 * 
	 */
	private Timer timer; // for game loop
	private Player player;
	private boolean ingame;
	private ArrayList tiles;
	private Tile tile;
	private Generator world;
	private int exdx, exdy;
	//private int B_WIDTH;
	//private int B_HEIGHT;
	private static int dir;
	
	public Board ()	{
		
		addKeyListener ( new TAdapter());
		setFocusable(true); // what??
		setBackground(Color.GREEN);
		setDoubleBuffered(true);
		ingame = true;
		
		dir = 0;
		
		
		// declare world stuff
		world = new Generator(80, 40, 45);
		world.randomFillMap();
		world.makeCaverns();
		world.makeCaverns();
		initTiles();
		
		/**
		 * Player start location logic.
		 * Is embedded in constructor, since we don't get a chance to initialize it later. yet.
		 */
		int startx = 0;
		int starty = 0;
		
		for ( int i = 0; i < (world.getMapHeight()); i++){
			for (int ir = 0; i < world.getMapWidth(); ir++) {
				if (world.getMapPoint(i, ir) == 0) {
					startx = i * 20;
					starty = ir * 20;
					System.out.println("Found a player location!");
					break; 
				}
			}
		}
		
		player = new Player(startx, starty); // Glemte å initialize denne, skapte sykt med problemer
		//collision deltas are 0
		exdx = 0;
		exdx = 0;
		
		setSize (800, 600);
		
		timer = new Timer(5, this);
		timer.start();
	}
	
	/**
	 * @title Tile Generator
	 * this method makes the tiles
	 */
	
	public void initTiles() {
		tiles = new ArrayList();
		int col = world.getMapWidth();
		int row = world.getMapHeight();
		
		
		for (int r = 0; r < row; r++) {
			for (int c = 0; c < col; c++){
				tiles.add(new Tile((c * 20), (r * 20), world.getMapPoint(c, r)));
			}
			
		}
		
	}
	
	/**
	 * @param graphics
	 * 
	 * This method takes graphics objects and draws them using the Graphics class.
	 * Currently it will only render the player object and the version state.
	 */
	
	public void paint (Graphics g) {
		super.paint(g);
		
		Graphics g2d = (Graphics2D) g;
		
		for (int i = 0; i < tiles.size(); i++) {
			Tile a = (Tile)tiles.get(i);
			if (a.isVisible()) 
				g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
		} 
		
		if (player.isVisible()) g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);
		
		
		
		g2d.setColor(Color.white);
		g2d.drawString("Xivia PreAlpha", 5, 15);
		
		
		
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
	
	public void checkCollisions() {
		
		/*
		 * Lay out a "plus sign" of off-set colliders.
		 * 
		 */
		
		// north box
		Rectangle nb = player.getBounds();
		nb.setLocation((player.getX()), (player.getY() - 20));
		
		// south box
		Rectangle sb = player.getBounds();
		sb.setLocation((player.getX()), (player.getY() + 20));
		
		// west box
		Rectangle wb = player.getBounds();
		wb.setLocation((player.getX() - 20), (player.getY()));
				
		// east box
		Rectangle eb = player.getBounds();
		eb.setLocation((player.getX() + 20), (player.getY()));
		
		/*
		 * Get player direction!
		 */
		
		int key = Board.dir;
		int localdir = 0;
		ArrayList compass = new ArrayList();
		compass.add(nb);
		compass.add(sb);
		compass.add(wb);
		compass.add(eb);
		
		if (key == KeyEvent.VK_W) {
			localdir = 0;
			System.out.println("W hit!");
		}
		
		if (key == KeyEvent.VK_S) {
			localdir = 1;
			System.out.println("S hit!");
		}
		
		if (key == KeyEvent.VK_A) {
			localdir = 2;
			System.out.println("A hit!");
		}
		
		if (key == KeyEvent.VK_D) {
			localdir = 3;
			System.out.println("D hit!");
		}
		
		// North 1, South 2, West 3, East 4
		
		
		/*
		 * check sum boxes
		 */
		
		for (int j = 0; j < tiles.size(); j++) {
			Tile t = (Tile) tiles.get(j);
			Rectangle r2 = t.getBounds();
			Rectangle r3 = (Rectangle) compass.get(localdir);
			
			if (r3.intersects(r2) && t.isCollidable()) {
				System.out.println(localdir);
				if ((localdir == 2) || (localdir == 3)) {
					if (player.isXAllowed()) {
						player.setDirectX(false);
						System.out.println("Collision! Disallowing X Axis.");
					} else {
						if (!player.isXAllowed()) {
							player.setDirectX(true);
							System.out.println("No collision! Allowing X Axis.");
						}
						
					}
				}  
				
				if ((localdir == 0) || (localdir == 1)) {
					if (player.isYAllowed()) {
						player.setDirectY(false);
						System.out.println("Collision! Disallowing Y Axis.");
					} else {
						if (!player.isYAllowed()){
							player.setDirectY(true);
							System.out.println("No collision! Allowing Y Axis.");
						}
						
					}
				}
			} else if (r3.intersects(r2) && (!t.isCollidable())) {

				System.out.println(localdir);
				if ((localdir == 2) || (localdir == 3)) {
					if (!player.isXAllowed()) {
						player.setDirectX(true);
						System.out.println("No collision! Allowing X Axis.");
					} 
						
					}
				}  
				
				if ((localdir == 0) || (localdir == 1)) {
					if (!player.isYAllowed()){
						player.setDirectY(true);
						System.out.println("No collision! Allowing Y Axis.");
					} 
						
					}
				}
			

		
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
		this.exdx = 0;
		this.exdx = 0;
		
		player.move(exdx, exdy);
		
		checkCollisions();
		
		repaint();
	}
	
	private class TAdapter extends KeyAdapter {
		
		public void keyReleased (KeyEvent e) {
			player.keyReleased (e);
			Board.dir = e.getKeyCode();
			
		}
		
		public void keyPressed (KeyEvent e) {
			player.keyPressed (e);
			
		}
	}
}
