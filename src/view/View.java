package view;

import algorithms.mazeGenerators.Maze3d;

public interface View {
	
	void start();
	
	void displayMessage(String msg);

	void warningMessage(String msg);

	void displayMaze(Maze3d maze);

}
