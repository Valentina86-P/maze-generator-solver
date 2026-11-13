import maze.algoritms.MazeGenerator;
import maze.model.Cell;
import maze.model.Direction;
import maze.model.Grid;


public class GeneratorCheck {

	public static void main(String[] args) {
        testSize(10, 10, true);   // size from the assignment
        testSize(30, 30, false);   // bigger size from the assignment
        testSize(1, 1, true);      // edge case: only one cell
        testSize(1, 8, true);

	}
	
	private static void testSize(int rows, int cols, boolean print) {
		Grid grid = new Grid(rows, cols);
		MazeGenerator generator = new MazeGenerator(grid);
		generator.generate();
		
		if(print) {
			grid.printGrid();
		}
		int cells = rows * cols;
		int passages = countPassages(grid);
		boolean allVisited = allVisited(grid);
		
		boolean ok = (passages == cells -1) && allVisited;
		
		System.out.println(rows + "x" + cols
                + ": passages = " + passages
                + " (expected " + (cells - 1) + ")"
                + ", all visited = " + allVisited
                + " -> " + (ok ? "OK" : "FAIL"));
	}
	/**
	 * Counts the open walls (passages) in the maze
	 * between two cells is counted once
	 */
	private static int countPassages(Grid grid) {
		int count = 0;
		for(int r =0; r < grid.getRows();r++) {
			for(int c = 0; c< grid.getCols();c++) {
				Cell cell = grid.getCell(r,c);
				//passage to the right (not on the last column)
				if(c < grid.getCols() - 1 && !cell.hasWall(Direction.RIGHT)) {
					count++;
				}
				//passage below(not on the last row)
				if (r< grid.getRows() - 1 && !cell.hasWall(Direction.BOTTOM)) {
					count++;
				}
			}
		}
		return count;
	}
	//return true if every cell in the grid has been visited
	private static boolean allVisited(Grid grid) {
		for(int r = 0; r < grid.getRows(); r++) {
			for(int c = 0; c< grid.getCols();c++) {
				if (!grid.getCell(r, c).isVisited()) {
					return false;
				}
			}
		}
		return true;
	}

}
