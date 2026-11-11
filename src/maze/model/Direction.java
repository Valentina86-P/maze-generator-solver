package maze.model;

/**
 * TOP, RIGHT, BOTTOM and LEFT-four sides of a cell in the maze
 * row 0 is the TOP row and row is increase going Down
 * col 0 is the LEFT column and columns increase going RIGHT
 * 
 */

public enum Direction {
	
	// name(dRow, dCol)
	TOP(-1, 0),  // move up one row, same column
	RIGHT(0, 1),  // same row, move one column to the right
	BOTTOM(1, 0), //move down one row, same column
	LEFT(0, -1);   // same row, move one column to the left
	
	/** How much the row number changes when moving in this direction. */
	private final int dRow;
	
	/** How much the column number changes when moving in this direction. */
    private final int dCol;
    
    //Constructor
    Direction(int dRow, int dCol){
    	this.dRow = dRow;
    	this.dCol = dCol;
    	
    }
    public int getDRow() {
    	return dRow;
    }
    public int getDCol() {
    	return dCol;
    }
    
    public Direction opposite() {
    	switch(this) {
    	case TOP: return BOTTOM;
    	case BOTTOM: return TOP;
    	case LEFT: return RIGHT;
    	default: return LEFT;    // the only one left is RIGHT
    	}
    }

}
