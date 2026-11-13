import maze.algoritms.MazeGenerator;
import maze.algoritms.MazeSolver;
import maze.model.Cell;
import maze.model.Direction;
import maze.model.Grid;

/**
 * Tests the solver on different maze sizes.
 * For each maze it checks that the path starts at the top-left,
 * ends at the bottom-right and never goes through a wall.
 */
public class SolverCheck {

    public static void main(String[] args) {
        testSize(10, 10, true);     // size from the assignment, printed
        testSize(30, 30, false);    // size from the assignment
        testSize(100, 100, false);  // big maze, to check it still works
        testSize(1, 1, false);      // edge case: start and exit are the same cell
        testSize(1, 8, false);      // edge case: only one row
        testSize(8, 1, false);      // edge case: only one column
    }

    // makes a maze, solves it and checks the path
    private static void testSize(int rows, int cols, boolean print) {
        Grid grid = new Grid(rows, cols);
        new MazeGenerator(grid).generate();

        MazeSolver solver = new MazeSolver(grid);
        solver.solve();
        Cell[] path = solver.getPath();

        if (print) {
            printWithPath(grid, path);
        }

        boolean ok = isValidPath(grid, path);
        System.out.println(rows + "x" + cols
                + ": path length = " + path.length + " cells"
                + " -> " + (ok ? "OK" : "FAIL"));
    }

    // the path must go from the start to the exit without walking through walls
    private static boolean isValidPath(Grid grid, Cell[] path) {
        if (path.length == 0) {
            return false; // no path found
        }
        // first cell must be the start
        if (path[0] != grid.getCell(0, 0)) {
            return false;
        }
        // last cell must be the exit
        Cell exit = grid.getCell(grid.getRows() - 1, grid.getCols() - 1);
        if (path[path.length - 1] != exit) {
            return false;
        }
        // each step must go to a next-door cell with no wall in between
        for (int i = 0; i < path.length - 1; i++) {
            boolean connected = false;
            for (Direction dir : Direction.values()) {
                if (grid.getNeighbour(path[i], dir) == path[i + 1]
                        && !path[i].hasWall(dir)) {
                    connected = true;
                }
            }
            if (!connected) {
                return false;
            }
        }
        return true;
    }

    // draws the maze in the console with a * on every cell of the path
    private static void printWithPath(Grid grid, Cell[] path) {
        // mark the cells that are on the path
        boolean[][] onPath = new boolean[grid.getRows()][grid.getCols()];
        for (Cell c : path) {
            onPath[c.getRow()][c.getCol()] = true;
        }

        StringBuilder sb = new StringBuilder();
        for (int c = 0; c < grid.getCols(); c++) {
            sb.append("+---");
        }
        sb.append("+\n");

        for (int r = 0; r < grid.getRows(); r++) {
            sb.append("|");
            for (int c = 0; c < grid.getCols(); c++) {
                sb.append(onPath[r][c] ? " * " : "   ");
                sb.append(grid.getCell(r, c).hasWall(Direction.RIGHT) ? "|" : " ");
            }
            sb.append("\n+");
            for (int c = 0; c < grid.getCols(); c++) {
                sb.append(grid.getCell(r, c).hasWall(Direction.BOTTOM) ? "---" : "   ");
                sb.append("+");
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }
}