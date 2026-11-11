package maze.model;

/**
 * Cell represents one square of the maze grid.
 * 
 * Each cell knows the position in the grid,its four walls and whether it has been visited.
 * 
 * A new cell is start with all walls standing
 */

public class Cell {
	// 0 = top row
	private final int row;
	
	//0 = left column
	private final int col;
	
	//The four walls start with true
	private boolean topWall = true;
	private boolean rightWall = true;
	private boolean bottomWall = true;
	private boolean leftWall = true;
	
	private boolean visited = false;
	
	public Cell(int row,int col) {
		this.row = row;
		this.col = col;
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}
	
	public boolean hasWall(Direction dir) {
		switch(dir) {
			case TOP: return topWall;
			case RIGHT: return rightWall;
			case BOTTOM: return bottomWall;
			default: return leftWall;     // the only one left is LEFT
		}
	}
	
	public void removeWall(Direction dir) {
		switch(dir) {
			case TOP: topWall = false; break;
			case RIGHT: rightWall = false; break;
			case BOTTOM: bottomWall = false; break;
			default: leftWall = false; break;
		}
	}
	
	public boolean isVisited() {
		return visited;
	}
	
	//Marks this cell as visited or not visited
	
	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	//Shows the cell as text Cell(2, 3)
	@Override
	public String toString() {
		return "Cell(" + row + ","  + col + ")";
	}
	
	
	
	

}
