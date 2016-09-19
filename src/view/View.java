package view;

import java.util.HashMap;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.controller.Command;
import algorithms.controller.Controller;

public interface View {
	
	void notifyMazeIsReady(String name);
	
	void displayMaze(Maze3d maze);
	
	void setCommands(HashMap<String, Command > commands);
	
	void displayDirectoryContents(String dirContents);
	
	void displayCrossSectionBy(int[][] maze2d);
	
	void notifyMazeIsSaved(String name);
	
	void notifyMazeIsLoaded(String name);
	
	void notifyMazeIsSolved(String name);
	
	void displaySolution(Solution<Position> sol);
	
	void setController(Controller controller);
	
	void start();
	
	void write(String str);
}
