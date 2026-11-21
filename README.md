## Maze Generator & Solver
This is my Data Structures project. The program makes a random maze, finds the shortest way through it, and shows everything happening on the screen.

The maze is built with Depth-First Search (DFS) using my Stack from Lab 3.
The maze is solved with Breadth-First Search (BFS) using my Queue from Lab 4.


**How to run it
In Eclipse: open src/maze/gui/MazeFrame.java, right-click it and choose Run As > Java Application.

From the command line (inside the project folder):
-javac -d bin -sourcepath src src/*.java src/maze/gui/MazeFrame.java
-java -cp bin maze.gui.MazeFrame.

The maze is 20 x 20. To try another size (for example 10 x 10 or 30 x 30), change M (rows) and N (columns) at the top of MazeFrame.java and run it again.

When the window opens, the animation starts by itself. You can watch:

the maze being carved out (DFS),
the search spreading from the top-left corner (BFS),
the shortest path appearing in green.

Other colours: dark grey = not carved yet, white = carved, orange = the cell being worked on, light blue = already checked by the search, blue = start, red = exit.

**How I organised the classes

I split the code into four packages:

-model: the maze (Cell, Direction, Grid)
-datastructure: my Stack and Queue from the labs
-algoritms: building the maze (MazeGenerator) and solving it (MazeSolver)
-gui: the window (MazeFrame) and the drawing (MazeCanvas)

The test programs are in the default package.

## My design choices
- Cells: each cell has 4 booleans for its walls (true = wall is there) and one for visited.
- Directions: a Direction enum (TOP, RIGHT, BOTTOM, LEFT) so I can loop over the 4 sides.
- Coordinates: cells are stored in `cells[row][col]`. Row 0 is the top, column 0 is the left. When drawing: left = col * cell_dim, top = row * cell_dim.
- Edges: a cell outside the grid returns null, so nothing crashes.
- Walls: a wall is removed from both cells it sits between.
- Generator (DFS): uses my Stack. It goes to a random unvisited neighbour and goes back when stuck. This makes a perfect maze (no loops).
- Solver (BFS): uses my Queue, so it finds the shortest path. It remembers where it came from (parent) to rebuild the path at the end.
- Queue change: I added a constructor that takes a size, because 50 was too small for big mazes.
- Animation: a Swing Timer calls step() every 30 ms and redraws the maze.

## Testing

I wrote three small test programs that print OK or FAIL in the console:

- GridCheck checks that walls are removed on both sides and that edges return null.
- GeneratorCheck checks that every cell is visited and the maze has no loops (a maze with N cells must have exactly N - 1 openings).
- SolverCheck checks that the path goes from start to exit without going through walls.

I tested 10 x 10, 30 x 30 and 100 x 100, and also the unusual cases 1 x 1, 1 x 8 and 8 x 1. Everything passed with no crashes.















































**