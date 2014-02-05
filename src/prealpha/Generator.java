package prealpha;

import java.util.Random;

public class Generator {
	
	/*
	 * fields
	 * Random generator.
	 * map array that takes integers
	 * map dimensions and wall percentage
	 */
	
	Random rand = new Random();
	
	public int row, col;
	
	public int[][]	map;
	
	public int mapwidth, mapheight, percentarewalls;
	
	
	
	public Generator (int width, int height, int percent) {
	
		this.mapwidth = width;
		this.mapheight = height ;
		this.percentarewalls = percent;
		
		
		this.row = 0;
		this.col = 0;
		
		map = new int[mapwidth * 10][mapheight * 10] ;
	}
	
	/*
	 * Accessors
	 * 
	 */
	
	public int getMapWidth() {
		return mapwidth;
	}
	
	public int getMapHeight() {
		return mapheight;
	}
	
	public int getPercentAreWalls() {
		return percentarewalls;
	}
	
	public int getMapPoint(int x, int y) {
		return map[x][y];
	}
	
	/*
	 * Mutators
	 */
	public void makeCaverns () {
		for ( int y = 0 ; y <= mapheight - 1; y++) {
			for ( int x = 0; x <= mapwidth - 1; x++) {
				map[x][y] = placeWallLogic(x, y);
			}
		}
		
		
	}
	
	public int placeWallLogic(int x, int y) {
		
		int numwalls = getAdjacentWalls(x,y,1,1);
		
		if (map[x][y] == 1) {
			if (numwalls >= 4) {
				return 1;
			}
			else if (numwalls < 2)  {
				return 0;
			}
		}
		else
		{
			if (numwalls >= 5) {
				return 1;
			}	
		}
		return 0;
	}

	public int getAdjacentWalls (int x, int y, int scopex, int scopey)  {
		int startx = x - scopex;
		int starty = y - scopey;
		int endx = x + scopex;
		int endy = y + scopey;
		
		int ix = startx;
		int iy = starty;
		
		int wallcounter = 0;
		
		for (iy = starty; iy <= endy; iy++) {
			for (ix = startx; ix <= endx; ix++) {
				if (! (ix == x && iy == y)) {
					if (isWall(ix, iy)) {
						wallcounter += 1;
					}
				}
			}
		}
		
		return wallcounter;
	}
	
	public boolean isWall(int x, int y) {
		if (isOutOfBounds (x, y))
		{
			return true;
		}
		
		if ( map[x][y] == 1 ) {
			return true;
		}
		
		if ( map[x][y] == 0){
			return false;
		}
		
		return false;
	}
	
	public boolean isOutOfBounds (int x, int y) {
		if (x < 0 || y<0)
		{
			return true;
		}
		else if ( x > mapwidth - 1 || y > mapheight -1 ) {
			return true;
		}
		
		return false;
	}
	
	public void randomFillMap() {
		int mapmiddle = 0;
		
		for (int row = 0; row < mapheight; row++) {
			for (int col = 0; col < mapwidth; col++) {
				
				if (col == 0)
				{
					map[col][row] = 1;
				}
				else if (row == 0)
				{
					map[col][row] = 1;
				}
				else if (col == mapwidth -1){
					map[col][row] = 1;
				}
				else if (row == mapheight -1) {
					map[col][row] = 1;
				}
				else {
					
					mapmiddle = (mapwidth / 2);
					
					if (row  == mapmiddle)
					{
						map[col][row] = 0;
					}
					else
					{
						map[col][row] = randomPercent(percentarewalls);
					}
					
					
					
				}
				
			}
		}
	}

	public int randomPercent (int percent) {
		if (percent >= rand.nextInt(100))
		{
			return 1;
		}
		return 0;
	}
}
