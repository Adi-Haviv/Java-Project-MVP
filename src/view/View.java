package view;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


public interface View {
	
	void start();
	
	void displayMessage(String msg);

}
