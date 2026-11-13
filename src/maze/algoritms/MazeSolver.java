package maze.algoritms;

import maze.datastructure.QueueArrayBased;
import maze.datastructure.StackReferenceBased;
import maze.model.Cell;
import maze.model.Direction;
import maze.model.Grid;

/**
 * Solves the maze using Breadth-First Search (BFS).
 *
 * It starts in the top-left corner and looks for the bottom-right corner.
 * BFS checks the closest cells first, so the first time it reaches
 * the exit, it has found the shortest way there.
 *
 * I use my Queue from Lab 4 to keep track of which cells to check next.
 */

public class MazeSolver {
	private final Grid grid;
	
	//cell waiting to be checked(FIFO)
	private final QueueArrayBased queue;
	
	//for each cell, remembers wich cell we came from
	private final Cell[][] parent;
	
	private final Cell start; //top left
	private final Cell exit; //top right
	
	//the cell we are looking at right now(the GUI will colour it
	private Cell current;
	
	private boolean finished = false;
	
	// the final path from start to exit
	private Cell[] path = new Cell[0];
	
	public MazeSolver(Grid grid) {
		this.grid = grid;
		this.start = grid.getCell(0, 0);
		this.exit = grid.getCell(grid.getRows()  - 1, grid.getCols() - 1);
		
		// each cell goes into the queue only once,
        // so making it as big as the grid is always enough
		this.queue = new QueueArrayBased(grid.getRows() * grid.getCols());
		this.parent = new Cell[grid.getRows()][grid.getCols()];
		
		// the generator marked every cell as visited, so clear them first
		grid.resetVisited();
		
		// put the start cell in the queue to begin
		start.setVisited(true);
		queue.enqueue(start);
	}
	//retun false when the serch is done
	
	public boolean step() {
		if(finished) {
			return false;
		}
		if(queue.isEmpty()) {
			//nothing left to check
			finished= true;
			return false;
		}
		
		// take the next cell from the front of the queue
        Cell w = (Cell) queue.dequeue();
        current = w;

        // found the exit, so build the path and stop
        if (w == exit) {
            buildPath();
            finished = true;
            return false;
        }
		
		// check all 4 sides of this cell
        for (Direction dir : Direction.values()) {
            // can't go through a wall
            if (w.hasWall(dir)) {
                continue;
            }
            Cell u = grid.getNeighbour(w, dir);
            // only go to cells we haven't seen yet
            if (u != null && !u.isVisited()) {
                u.setVisited(true);
                parent[u.getRow()][u.getCol()] = w; // we got to u from w
                queue.enqueue(u);
            }
        }
        return true;
    }
	
	//runs the whole search in one go (for testing)
	public void solve() {
		while(step()) {
			//keep going until the exit is found
		}
	}
	/**
     * Builds the final path once the exit is found.
     *
     * Starting at the exit, I follow the parent of each cell back
     * to the start. This gives the path backwards, so I put the cells
     * on my Stack from Lab 3 and pop them off to get the right order.
     */
	private void buildPath() {
		StackReferenceBased stack = new StackReferenceBased();
        int length = 0;
        
     // go back from the exit to the start
        // (the start has no parent, so the loop stops there)
        Cell cell = exit;
        while (cell != null) {
            stack.push(cell);
            length++;
            cell = parent[cell.getRow()][cell.getCol()];
        }
        
     // pop them off so the path goes from start to exit
        path = new Cell[length];
        for (int i = 0; i < length; i++) {
            path[i] = (Cell) stack.pop();
        }
    }
	public boolean isFinished() {
        return finished;
    }

    public Cell getCurrent() {
        return current;
    }

    public Cell[] getPath() {
        return path;
    }
}