package view;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface View {
	
	void start();
	
	void displayMessage(String msg);

	void warningMessage(String msg);

	void displayMaze(Maze3d maze);

	void mazeSolved(String name);

	void displaySolution(Solution<Position> sol);

	void displayHint(Solution<Position> sol);

}
