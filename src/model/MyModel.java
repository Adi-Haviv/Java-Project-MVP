package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.io.MyCompressorOutputStream;
import algorithms.io.MyDecompressorInputStream;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.BFS;
import algorithms.search.DFS;
import algorithms.search.SearchableMazeAdapter;
import algorithms.search.Searcher;
import algorithms.search.Solution;


public class MyModel extends Observable implements Model {
	
	private Map<String, Maze3d> mazes = new ConcurrentHashMap<String, Maze3d>();
	private Map<String, Solution<Position>> solutions = new ConcurrentHashMap<String, Solution<Position>>();
	private ExecutorService executor;
	
	public MyModel(){
		executor = Executors.newFixedThreadPool(79);
	}
	
	/**
	 * This method generates a maze based on user inputs.
	 * @param name 
	 */
	@Override
	public void generateMaze(String name, int rows, int columns, int floors) {
		executor.submit(new Callable<Maze3d>() {
			@Override
			public Maze3d call() throws Exception{
				
				GrowingTreeGenerator mg = new GrowingTreeGenerator();
				Maze3d maze = mg.generate(rows, columns, floors);
				mazes.put(name, maze);
				setChanged();
				notifyObservers("maze_ready " + name);
				return maze;
			}
		});		
	}

	/**
	 * This method returns a maze object from the list of saved mazes.
	 * 
	 * @param name Name of maze to return.
	 */
	@Override
	public Maze3d getMaze(String name) {
		try{
		return mazes.get(name);
		}
		catch (NullPointerException e){
			System.err.println("Not a valid maze");
		}
		return new Maze3d();
	}
	
	/**
	 * Returns the contents of a directory in the file system.
	 */

	@Override
	public String getDirectoryContents(String path) {
		try{
		StringBuilder sb = new StringBuilder();
		File dir = new File(path);
		File[] filesList = dir.listFiles();
		for (File file : filesList) {
		    if (file.isFile()) {
		        sb.append(file.getName());
		    	}
			}
		return sb.toString();
		}
		catch(NullPointerException e){
			System.err.println("Not a valid directory");
		}
		return null;
	}

	
	/**
	 * Returns a maze's cross section by a given axis.
	 * @param index Index in specified axis at which to take cross section.
	 * @param axis Axis to be used for cross section.
	 * @param name Name of maze from which to take cross section.
	 */
	@Override
	public int[][] getCrossSectionBy(int index, char axis, String name) {
		try{
		switch (Character.toUpperCase(axis)) {
		case 'X':
			return mazes.get(name).getCrossSectionByX(index);
		case 'Y':
			return mazes.get(name).getCrossSectionByY(index);
		case 'Z':
			return mazes.get(name).getCrossSectionByZ(index);
		}
		}
		catch(NullPointerException e){
			System.err.println("Not a valid maze' Sorry honey");
		}
		return null;
	}
	/**
	 * Saves a maze from memory to a file.
	 */

	@Override
	public void saveMazeToFile(String name, String filename) {
		MyCompressorOutputStream out;
		try {
			out = new MyCompressorOutputStream(new FileOutputStream(filename));
			try {
				out.write(mazes.get(name).toByteArray());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		setChanged();
		notifyObservers("maze_saved " + name);
	}
	
	/**
	 * Loads a maze from a file to memory.
	 */

	@Override
	public void loadMazeFromFile(String filename, String name) {
		try {
			MyDecompressorInputStream in = new MyDecompressorInputStream(new FileInputStream(filename));
			List<Byte> mazeArr = new ArrayList<Byte>();
			byte[] mazeByte;
			
			in.read(mazeArr);
			
			mazeByte = new byte[mazeArr.size()];
			for(int i = 0; i < mazeArr.size(); i++){
				mazeByte[i] = mazeArr.get(i).byteValue();
			}
			mazes.put(name, new Maze3d(mazeByte));
			in.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
		setChanged();
		notifyObservers("maze_loaded " + name);
	}

	
	/**
	 * Solves a maze and stores the solution in the solutions data member.
	 * 
	 * @param name Name of maze to be solved.
	 * @param algorithm Solution algorithm to be used to solve the maze.
	 */
	@Override
	public void solveMaze(String name, String algorithm) {
		Searcher<Position> searcher;
		SearchableMazeAdapter maze = new SearchableMazeAdapter(mazes.get(name));
		Solution<Position> sol;
		
		switch (algorithm.toUpperCase()) {
		case "DFS":
			searcher = new DFS<Position>();
			sol = searcher.search(maze);
			
			if (solutions.containsKey(name)){
				solutions.remove(name);
			}
			
			solutions.put(name, sol);
			break;
		
		case "BFS":
			searcher = new BFS<Position>();
			sol = searcher.search(maze);
			
			if (solutions.containsKey(name)){
				solutions.remove(name);
			}
			
			solutions.put(name, sol);
			break;
			
		default:
			break;
		}
		setChanged();
		notifyObservers("maze_solved " + name);
	}
	
	/**
	 * Returns a maze's solution.
	 * 
	 * @param name Name of the maze who's solution should be returned.
	 */

	@Override
	public Solution<Position> getMazeSolution(String name) {
		try{
		return solutions.get(name);
		}
		catch(NullPointerException e){
			System.err.println("Not a valid maze");
		}
		return new Solution<Position>();
	}

	
	/**
	 * Stops all maze generation threads and dies.
	 */
	@Override
	public void exit() {
		executor.shutdown();
	}
		
}
