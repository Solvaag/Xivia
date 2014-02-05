package prealpha;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Player {

	/**
	 * This is the fields section. Fields required to work:
	 * geometry
	 * image
	 * visibility bool
	 */
	private String avatar = "avatar.png";
	private int x;
	private int y;
	private int dx;
	private int dy;
	private boolean directx;
	private boolean directy;
	private boolean visible;
	private Image image;
	
	public Player(int posx, int posy) {
		ImageIcon ii = new ImageIcon(this.getClass().getResource(avatar));
		image = ii.getImage();
		visible = true;
		this.x = posx;
		this.y = posy;
		directx = true;
		directy = true;
		
	}
	
	// this is the movement method pt 1
	// it updates player location
	public void move (int exdx, int exdy) {
		x += dx + exdx; 
		y += dy + exdy;
		
		/*
		if ( exdx != 0) x = exdx;
		if ( exdy != 0) x = exdy;
		*/
		
		if (x < 1) {
			x = 1;
		}
		
		if (x > 8000) {
			x = 8000;
		}
		
		if (y < 1) {
			y = 1;
		}
		
		if (y > 6000) {
			y = 6000;
		}
	}
	
	
	/** 
	 * Accessor section.
	 * 
	 */
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Image getImage() {
		return image;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public boolean isXAllowed() {
		return directx;
	}
	
	public boolean isYAllowed() {
		return directy;
	}
	
	public Rectangle getBounds() {
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		return new Rectangle (x, y, width, height);
	}
	
	/**
	 * 
	 * @param visible
	 */
	public void setDirectX(boolean allowance) {
		this.directx = allowance;
	}
	
	public void setDirectY(boolean allowance) {
		this.directy = allowance;
	}
	
	//Visibility toggle
	//Takes bool and sets it to this.visible
	public void setVisible (boolean visible) {
		this.visible = visible;
	}
	
	public void keyPressed (KeyEvent e) {
		
		int key = e.getKeyCode();
		
		// Implemented boolean switches for the collider. Player can't move unless these are true. 
		
		if ((key == KeyEvent.VK_W) && (directy == true)) dy = -1;
		
		if ((key == KeyEvent.VK_S) && (directy == true)) dy = 1;
		
		if ((key == KeyEvent.VK_A) && (directx == true)) dx = -1;
		
		if ((key == KeyEvent.VK_D) && (directx == true)) dx = 1;
		
	}
	
	public void keyReleased (KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_W) dy = 0;
		
		if (key == KeyEvent.VK_S) dy = 0;
		
		if (key == KeyEvent.VK_A) dx = 0;
		
		if (key == KeyEvent.VK_D) dx = 0;
	}
}
