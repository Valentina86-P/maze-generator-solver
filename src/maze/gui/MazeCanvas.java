package maze.gui;

import java.awt.*;
import javax.swing.*;

import maze.algoritms.MazeGenerator;
import maze.algoritms.MazeSolver;
import maze.model.Cell;
import maze.model.Direction;
import maze.model.Grid;

/**
 * The panel where the maze is drawn and animated.
 *
 * paintComponent() works out the size of a cell and calls drawCell()
 * for every cell. A Swing Timer moves the animation forward: first the
 * generator (DFS) carves the maze, then the solver (BFS) looks for the exit.
 */
public class MazeCanvas extends JPanel {

    // colours for each state of a cell
    Color UNVISITED = new Color(45, 45, 55);    // not carved yet (dark grey)
    Color CARVED    = Color.WHITE;              // carved by the generator
    Color EXPLORED  = new Color(170, 210, 255); // checked by the solver (light blue)
    Color CURRENT   = new Color(255, 140, 0);   // cell being worked on now (orange)
    Color PATH      = new Color(60, 190, 90);   // the shortest path (green)
    Color START     = new Color(30, 110, 230);  // top-left (blue)
    Color EXIT      = new Color(220, 50, 50);   // bottom-right (red)

    Grid grid;
    MazeGenerator generator;
    MazeSolver solver;          // null until the maze is finished
    Timer timer;

    Cell current;               // cell to colour orange
    boolean[][] onPath;         // true for cells on the shortest path
    boolean solving = false;    // false = generating, true = solving
    int stepsPerTick;           // bigger mazes do more steps per tick

    public MazeCanvas(Grid grid_arg) {
        grid = grid_arg;
        onPath = new boolean[grid.getRows()][grid.getCols()];
        generator = new MazeGenerator(grid);

        // about 200 ticks for the whole maze, whatever the size
        stepsPerTick = Math.max(1, grid.getRows() * grid.getCols() / 100);

        timer = new Timer(30, e -> tick()); // tick every 30 milliseconds
        timer.start();
    }

    /** Called by the timer again and again. Moves the animation forward. */
    private void tick() {
        if (!generator.isFinished()) {
            // 1) still carving the maze
            for (int i = 0; i < stepsPerTick && !generator.isFinished(); i++) {
                generator.step();
            }
            current = generator.getCurrent();
        } else if (solver == null) {
            // 2) maze is done: start the solver
            solver = new MazeSolver(grid);
            solving = true;
        } else if (!solver.isFinished()) {
            // 3) still searching for the exit
            for (int i = 0; i < stepsPerTick && !solver.isFinished(); i++) {
                solver.step();
            }
            current = solver.getCurrent();
        } else {
            // 4) exit found: mark the path and stop
            for (Cell c : solver.getPath()) {
                onPath[c.getRow()][c.getCol()] = true;
            }
            current = null;
            timer.stop();
        }
        repaint();
    }

    /** Draws one cell: its colour first, then the walls that are still standing. */
    private void drawCell(Graphics g, Cell c, int left, int top, int cell_dim) {
        g.setColor(colourFor(c));
        g.fillRect(left, top, cell_dim, cell_dim);

        g.setColor(Color.BLACK);
        if (c.hasWall(Direction.TOP))
            g.drawLine(left, top, left + cell_dim, top);                                   // draw top line
        if (c.hasWall(Direction.LEFT))
            g.drawLine(left, top, left, top + cell_dim);                                   // draw left line
        if (c.hasWall(Direction.BOTTOM))
            g.drawLine(left, top + cell_dim, left + cell_dim, top + cell_dim);             // draw bottom line
        if (c.hasWall(Direction.RIGHT))
            g.drawLine(left + cell_dim, top, left + cell_dim, top + cell_dim);             // draw right line
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = getWidth() - 2;  // leave 1 pixel so the last walls are visible
        int h = getHeight() - 2;

        // the size of a cell in pixels, so the whole maze fits
        int cell_dim = Math.min(w / grid.getCols(), h / grid.getRows());

        for (int i = 0; i < grid.getRows(); i++) {        // i = row
            for (int j = 0; j < grid.getCols(); j++) {    // j = column
                // column -> left (x), row -> top (y)
                drawCell(g, grid.getCell(i, j), j * cell_dim, i * cell_dim, cell_dim);
            }
        }
    }

    /** Picks the colour of one cell. The first rule that matches wins. */
    private Color colourFor(Cell c) {
        int r = c.getRow();
        int col = c.getCol();
        if (c == current)                                         return CURRENT;
        if (r == 0 && col == 0)                                   return START;
        if (r == grid.getRows() - 1 && col == grid.getCols() - 1) return EXIT;
        if (onPath[r][col])                                       return PATH;
        if (!c.isVisited())                                       return solving ? CARVED : UNVISITED;
        return solving ? EXPLORED : CARVED;
    }
}