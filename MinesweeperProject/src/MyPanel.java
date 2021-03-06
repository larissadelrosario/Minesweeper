import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 25;
	private static final int GRID_Y = 25;
	private static final int INNER_CELL_SIZE = 29;
	private char[] code = {'0','1','2','3','4','5','6','7','8'};
	public static  int TOTAL_COLUMNS = 9;
	public static int TOTAL_ROWS = 9;   //Last row has only one cell
	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public static Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	public static int[][] statusArray = new int[TOTAL_COLUMNS][TOTAL_ROWS];
	public static int [][] minesArray = new int[TOTAL_COLUMNS][TOTAL_ROWS];
	
	
	public MyPanel() {   //This is the constructor... this code runs first to initialize
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}

		//Initialize Array Color, Status and Neighbor Mines to Default
		for (int x = 0; x < TOTAL_COLUMNS; x++) {   
			for (int y = 0; y < TOTAL_ROWS; y++) {
				colorArray[x][y] = Color.WHITE;
				statusArray[x][y] = 0;
				minesArray[x][y] = 0;
			}
		}
	}
	public void paintComponent(Graphics g) {
	
		super.paintComponent(g);
			
		//Compute interior coordinates
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		int x2 = getWidth() - myInsets.right - 1;
		int y2 = getHeight() - myInsets.bottom - 1;
		int width = x2 - x1;
		int height = y2 - y1;

		
		//Paint the background
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x1, y1, width + 1, height + 1);
		
		//Draw the grid
		//By default, the grid will be 9x9 (see above: TOTAL_COLUMNS and TOTAL_ROWS) 
		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS)));
		}


		//Paint cell colors and number of neighbor mines (if applicable)
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS; y++) {
				if ((x == 0) || (y != TOTAL_ROWS)) {
					Color c = colorArray[x][y];
					g.setColor(c);
					g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
					g.setColor(Color.BLACK);
					if ( minesArray[x][y] != 0 ) {
						g.drawChars(code,minesArray[x][y],1,x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 10,y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1))+20);
					}
				}
			}
		}
			
	}
	
	
	public  int getGridX(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x == 0 && y == TOTAL_ROWS ) {    //The lower left extra cell
			return x;
		}
		if (x < 0 || x > TOTAL_COLUMNS || y < 0 || y > TOTAL_ROWS) {   //Outside the rest of the grid
			return -1;
		}
		return x;
	}
	
	
	public int getGridY(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x == 0 && y == TOTAL_ROWS) {    //The lower left extra cell
			return y;
		}
		if (x < 0 || x > TOTAL_COLUMNS  || y < 0 || y > TOTAL_ROWS) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}
	
	
	public static int[][] MineArray = new int[MyPanel.TOTAL_COLUMNS][MyPanel.TOTAL_ROWS];{
		Random generator = new Random();
		
		for (int i = 0; i < TOTAL_COLUMNS; i++) {
			for (int u = 0; u < TOTAL_ROWS; u++) {
				MineArray[i][u] = generator.nextInt(12);
				
			}
		}
	}
}
