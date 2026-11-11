package maze.model;
/**
 * Grid represents the whole maze: a 2D grid of cells
 * 
 * row 0 is the top row, rows increase going down
 * col 0 is the left column, columns increase going right
 * cells[row][col] is the cell at that position
 * 
 * any position outside the grid returns null
 */

public class Grid {
	//number of rows
	private final int rows;
	
	//number of columns
	private final int cols;
	
	//The 2D array that holds every cell of the maze
	private final Cell[][] cells;
	
	/**
	 * Create a grid of rows x colls
	 * Every cell starts with all four walls standing
	 */
	public Grid(int rows, int cols) {
		if(rows <1 || cols <1) {
			throw new IllegalArgumentException("Grid must be at least 1 x 1");
		}
		this.rows = rows;
		this.cols = cols;
		
		// create the 2D array, then fill it with new cells
		cells = new Cell[rows][cols];
		for(int r =0; r < rows; r++) {
			for(int c =0; c< cols; c++) {
				cells[r][c] = new Cell(r,c);
			}
		}
	}
	
	public int getRows() {
		return rows;
	}
	public int getCols() {
		return cols;
	}
	/**
	 * Checks if a position is inside the grid.
	 */
	public boolean isInside(int row,int col ) {
		return row >= 0 && row < rows && col>= 0 && col <cols;
	}
	//Returns the cell at (row, col)
	public Cell getCell(int row,int col) {
		if(!isInside(row,col)) {
			return null;
		}
		return cells[row][col];
	}
	/**
	 * Returns the neighbour of a cell in the given direction
	 * return the neighbour, or null if it would be outside the grid
	 */
	public Cell getNeighbour(Cell cell,Direction dir) {
		int newRow = cell.getRow() + dir.getDRow();
		int newCol = cell.getCol() + dir.getDCol();
		return getCell(newRow, newCol);
	}
	/**
	 * Knocks down the wall between a cell and its neighbour.
     * The wall is removed on BOTH sides so the two cells agree.
	 */

	public void removeWallBetween(Cell cell,Direction dir) {
		Cell neighbour = getNeighbour(cell,dir);
		if (neighbour == null) {
			return; // never remove the outer border of the maze
		}
		cell.removeWall(dir);
		neighbour.removeWall(dir.opposite());
	}
	/**
	 * Marks every cell as not visited.
	 */
	public void resetVisited() {
		for(int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				cells[r][c].setVisited(false);
			}
		}
	}
	
	/**
     * Prints the maze in the console using +, --- and | characters.
    */
	public void printGrid() {
        StringBuilder sb = new StringBuilder();

        // top border of the maze
        for (int c = 0; c < cols; c++) {
            sb.append("+---");
        }
        sb.append("+\n");

        for (int r = 0; r < rows; r++) {
            // line 1: left border, then each cell and its right wall
            sb.append("|");
            for (int c = 0; c < cols; c++) {
                sb.append("   ");
                sb.append(cells[r][c].hasWall(Direction.RIGHT) ? "|" : " ");
            }
            sb.append("\n");

            // line 2: bottom wall of each cell in this row
            sb.append("+");
            for (int c = 0; c < cols; c++) {
                sb.append(cells[r][c].hasWall(Direction.BOTTOM) ? "---" : "   ");
                sb.append("+");
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }
}
