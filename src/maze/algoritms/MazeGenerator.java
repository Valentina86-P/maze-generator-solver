package maze.algoritms;

import java.util.Random;

import maze.datastructure.StackReferenceBased;
import maze.model.Cell;
import maze.model.Direction;
import maze.model.Grid;

/**
 * Randomized Depth-First Search with backtracking
 * Stack from Lab 3 (StackReferenceBased) to remember the path,
 * so it can go back (backtrack) when it gets stuck
 * Each call to step() does ONE iteration of the while loop
 */

public class MazeGenerator {
	
	private final Grid grid;
	
	//Stack from Lab 3: holds the path from the start to the current cell
	private final StackReferenceBased stack = new StackReferenceBased();
	
	// Used to pick a random neighbour. 
	private final Random random;
	
	// The cell the algorithm is working on right now (used for drawing)
		private Cell current;
	
	//Create a generator that makes a different maze everu time
	public MazeGenerator(Grid grid) {
		this(grid, new Random());
	}
	/**
	 * Create a generator with a given Random
	 * new Random(42) gives the same maze every time
	 */
	public MazeGenerator(Grid grid, Random random) {
		this.grid = grid;
		this.random = random;
		
		//start at the top-left cell
		Cell start = grid.getCell(0, 0);
		start.setVisited(true);
		stack.push(start);
	    current = start;
	}
	public boolean step() {
		//when the stack is empty, every cell has been visited
		if(stack.isEmpty()) {
			current = null;
			return false;
		}
		//look at the cell on top of the stack
		Cell top = (Cell) stack.peek();
		
		//choose a random neighbour that has not been visited yet
		Direction dir = pickRandomUnvisitedDirection(top);
		
		if (dir == null) {
			//go back track
			stack.pop();
		    current = stack.isEmpty() ? null : (Cell) stack.peek();
		}
		else {
			Cell next = grid.getNeighbour(top, dir);
			grid.removeWallBetween(top, dir);
			next.setVisited(true);
			stack.push(next);
			current= next;
		}
		return true;
	}
	public void generate() {
		while(step()) {
			//keep steping until the maze is finished
		}
	}
	//return true when the maze is complete
	public boolean isFinished() {
		return stack.isEmpty();
	}
	//retun the cell being worked on right now
	public Cell getCurrent() {
		return  current;
	}
	/**
	 * Look at the 4 neghbours of a cell and retun the direction
	 */
	private Direction pickRandomUnvisitedDirection (Cell cell) {
		Direction[] option = new Direction[4];
		int count = 0;
		
		//collect every valid choice
		for(Direction dir: Direction.values()) {
			Cell neighbour = grid.getNeighbour(cell, dir);
			if(neighbour != null && !neighbour.isVisited()) {
				option[count] = dir;
				count++;
			}
		}
		if (count == 0) {
			return null; //stuck- nowhere new to go
		}
		// pick one of the valid choices at random
        return option[random.nextInt(count)];
	}
	

}
