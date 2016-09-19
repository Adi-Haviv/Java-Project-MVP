package model;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface Model {
	
	void generateMaze(String name, int rows, int columns , int floors); //Realized in Command Manager
	
	Maze3d getMaze(String name); //Realized in Command Manager
	
	String getDirectoryContents(String path); //Realized in Command Manager
	
	int[][] getCrossSectionBy(int index, char axis, String name); //Realized in Command Manager
	
	void saveMazeToFile(String name, String filename); //Realized in Command Manager
	
	void loadMazeFromFile(String filename, String name); //Realized in Command Manager
	
	void solveMaze(String name, String algorithm); //Realized in Command Manager
	
	Solution<Position> getMazeSolution(String name); //Realized in Command Manager 
 	
	void exit();
}
