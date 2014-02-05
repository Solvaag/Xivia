package prealpha;


import java.awt.Image;
//import java.util.ArrayList;

import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Tile {
	
	private String[] tiletypes; // what terrain?
	private Image image; // terrain image
	private int x; //locationx
	private int y;// locationy
	private boolean visible; // tile visibility
	private int type; // the tile type
	private boolean collidable; // is it collidable? 
	
	
public Tile (int x, int y, int tile) {
	
	this.type = tile;
	tiletypes = new String[10];
	tiletypes[0] = "grass.png";
	tiletypes[1] = "wall.png"; 
	
	ImageIcon ii = new ImageIcon(this.getClass().getResource(tiletypes[type]));
	image = ii.getImage(); 
	
	visible = true;
	this.x = x;
	this.y = y;
	
	collidable = false;
	setCollide(type);
	
}
/**
 * mutator section
 */

public void setCollide (int type) {
	if (type == 1)
	{
		this.collidable = true;
	} else {
		this.collidable = false;
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

public boolean isCollidable() {
	return collidable;
}

public Rectangle getBounds() {
	int width = image.getWidth(null);
	int height = image.getHeight(null);
	return new Rectangle (x, y, width, height);
}

public int getType() {
	return type;
}
}
