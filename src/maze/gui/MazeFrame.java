package maze.gui;

import javax.swing.*;

import maze.model.Grid;

/**
 * The window of the program.
 *
 * M and N set the size of the maze. Change them to try
 * different sizes (for example 10 x 10 or 30 x 30).
 */
public class MazeFrame extends JFrame {

    int M = 20; // number of rows
    int N = 20; // number of columns
    Grid grid = new Grid(M, N); // the grid creates all the cells

    public MazeFrame() {
        setTitle("My Perfect Maze!");
        setSize(650, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new MazeCanvas(grid));
    }

    public static void main(String[] args) {
        new MazeFrame().setVisible(true);
    }
    
}