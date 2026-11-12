import maze.model.Cell;
import maze.model.Direction;
import maze.model.Grid;

public class GridCheck {

	public static void main(String[] args) {
		Grid grid = new Grid(4, 4);
		System.out.println("New 4x4 grid,all walls up: ");
		grid.printGrid();
		
		Cell start = grid.getCell(0, 0);
		grid.removeWallBetween(start, Direction.RIGHT);      //(0, 0) -> (0, 1)
		grid.removeWallBetween(grid.getCell(0, 1), Direction.BOTTOM); // (0,1) -> (1,1)
		
		System.out.println("After removing two walls");
		grid.printGrid();
		
		System.out.println("Neighbour above (0,0): " + grid.getNeighbour(start, Direction.TOP));
        System.out.println("Neighbour right of (0,0): " + grid.getNeighbour(start, Direction.RIGHT));
        System.out.println("(0,1) has LEFT wall? " + grid.getCell(0, 1).hasWall(Direction.LEFT));

	}

}
