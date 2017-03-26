import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class Grids {
private int x;
private  int y;

 //Constructor
	public Grids(int gridX, int gridY) {
		
		 x = gridX;
		 y = gridY;
}
	//Method 
	public void CheckGrid(MyPanel one){
		int gridX = x;
		int gridY = y;
		
	if(MyPanel.statusArray[gridX][gridY] == 0){
		MyPanel.statusArray[gridX][gridY] = 1;
		if(MyPanel.MineArray[x][y] == 0 || MyPanel.MineArray [x][y]==1){
			 
			 for(int m = 0; m <= MyPanel.TOTAL_COLUMNS; m++)
			 {
				 for(int n = 0; n <= MyPanel.TOTAL_ROWS; n++)
				 {
					if(m>=0 && n>=0 && m<MyPanel.TOTAL_COLUMNS && n<MyPanel.TOTAL_ROWS) 
					{
					  if( MyPanel.MineArray[m][n] == 0 || MyPanel.MineArray [m][n]==1 )	
					  {
						  MyPanel.colorArray[m][n] = Color.BLACK;
						  one.repaint();
					  }
					 
					}
				 } 
			 }	 
			 
			 
		// Game Over
		}
		else{
			MyPanel.colorArray[gridX][gridY] = Color.GRAY;
			one.repaint();
			int minecounter=0;
			
			 for(int i = -1; i <= 1; i++){
				 for(int u = -1; u <= 1; u++){
					 
				 	//if( MyPanel.MineArray[gridX-1+i][gridY-1+u]==0 || MyPanel.MineArray[gridX-1+i][gridY-1+u]==1  )
					if(gridX+i>=0 && gridY+u>=0 && gridX+i<MyPanel.TOTAL_COLUMNS && gridY+u<MyPanel.TOTAL_ROWS) {
					  if( MyPanel.MineArray[gridX+i][gridY+u]==0 || MyPanel.MineArray[gridX+i][gridY+u]==1 )	{
						 minecounter = minecounter + 1;
			
					  }
					 
					}
				 }
				 
			 }
			 System.out.println(" Mines around " + gridX + " ,  " + gridY + " = " +  minecounter);
			 if(minecounter != 0){
				 System.out.println(" Print Numer of Mines JLabel");

				 
			 }
			 else{			 
				 System.out.println(" Execute NeighbordGrids ");
				 this.NeighborGrids(one);
			 }
		}
	  }
	}
	public void NeighborGrids(MyPanel two){
		int grid2X;
		int grid2Y;
		
		for(int i = -1; i <= 1; i++){
			 for(int u = -1; u <= 1; u++){		
				 grid2X = x + i;
				 grid2Y = y + u;
				 System.out.println(" Grid1 Axis " + grid2X + " " + grid2Y);
				
			  
			  if (grid2X >= 0 && grid2X < MyPanel.TOTAL_COLUMNS && grid2Y >= 0 && grid2Y < MyPanel.TOTAL_ROWS)
			  {
				 if(MyPanel.statusArray[grid2X][grid2Y] == 0){
					 MyPanel.statusArray[grid2X][grid2Y] = 1;
					 System.out.println(" Grid1 Values " + MyPanel.MineArray[grid2X][grid2Y]);
					 if (MyPanel.MineArray[grid2X][grid2Y] == 0 || MyPanel.MineArray[grid2X][grid2Y] == 1){
						 System.out.println("NeighbordGrids Grid Value BLACK");
						 MyPanel.colorArray[grid2X][grid2Y] = Color.BLACK;
						 //two.repaint();
						 //Do Nothing
					 }
					 else{
						 System.out.println("NeighbordGrids Grid Value GRAY");
						 MyPanel.colorArray[grid2X][grid2Y] = Color.GRAY;
						 two.repaint();
						 int minecounter2=0;
							
						 for(int a = -1; a <= 1; a++){
							 for(int b = -1; b <= 1; b++){		 
							 	//if( MyPanel.MineArray[gridX-1+i][gridY-1+u]==0 || MyPanel.MineArray[gridX-1+i][gridY-1+u]==1  )
								if(grid2X+a>=0 && grid2Y+b>=0 && grid2X+a< MyPanel.TOTAL_COLUMNS && grid2Y+b< MyPanel.TOTAL_ROWS) {
								  if( MyPanel.MineArray[grid2X+a][grid2Y+b]==0 || MyPanel.MineArray[grid2X+a][grid2Y+b]==1 )	{
									 minecounter2 = minecounter2 + 1;
								  } 
								}
							 }		 
						 }
						 if(minecounter2 != 0){
							 System.out.println(" Mines2 around " + grid2X + " , " + grid2Y + " = " +  minecounter2);
						 }
						 else{
							 System.out.println("Call NeighborGrids Again");
							 Grids nextGrids = new Grids(grid2X, grid2Y);
							 nextGrids.NeighborGrids(two);	 
						 	}
						 }
				   }
				 }
			 	}
			 }
		}


}



