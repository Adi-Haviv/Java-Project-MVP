package model;

import java.io.File;
import properties.*;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.BFS;
import algorithms.search.DFS;
import algorithms.search.SearchableMazeAdapter;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import dbConnection.SaveObject;


public class MyModel extends Observable implements Model {
	
	private Map<String, Maze3d> mazes = new ConcurrentHashMap<String, Maze3d>();
	private Map<String, Solution<Position>> solutions = new ConcurrentHashMap<String, Solution<Position>>();
	private ExecutorService executor;
	private Properties properties;
	private SaveObject so = new SaveObject();
	
	public MyModel(){
		properties = PropertiesLoader.getInstance().getProperties();
		executor = Executors.newFixedThreadPool(properties.getNumOfThreads());
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
			String msg = "Not a valid maze";
			notifyObservers("warning " + msg);
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
			String msg = "Not a valid directory";
			notifyObservers("warning " + msg);		}
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
			String msg = "Not a valid maze, Sorry honey";
			notifyObservers("warning " + msg);		}
		return null;
	}
//	/**
//	 * Saves a maze from memory to a file.
//	 */
//
//	@Override
//	public void saveMazeToFile(String name, String filename) {
//		MyCompressorOutputStream out;
//		try {
//			out = new MyCompressorOutputStream(new FileOutputStream(filename));
//			try {
//				out.write(mazes.get(name).toByteArray());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		setChanged();
//		notifyObservers("maze_saved " + name);
//	}
//	
//	/**
//	 * Loads a maze from a file to memory.
//	 */
//
//	@Override
//	public void loadMazeFromFile(String filename, String name) {
//		try {
//			MyDecompressorInputStream in = new MyDecompressorInputStream(new FileInputStream(filename));
//			List<Byte> mazeArr = new ArrayList<Byte>();
//			byte[] mazeByte;
//			
//			in.read(mazeArr);
//			
//			mazeByte = new byte[mazeArr.size()];
//			for(int i = 0; i < mazeArr.size(); i++){
//				mazeByte[i] = mazeArr.get(i).byteValue();
//			}
//			mazes.put(name, new Maze3d(mazeByte));
//			in.close();
//		} catch (FileNotFoundException e) {
//			String msg = "File not found";
//			notifyObservers("warning " + msg);
//			} catch (IOException e) {
//			e.printStackTrace();
//		}
//		setChanged();
//		notifyObservers("maze_loaded " + name);
//	}
	
	

	
	/**
	 * Solves a maze and stores the solution in the solutions data member.
	 * 
	 * @param name Name of maze to be solved.
	 * @param algorithm Solution algorithm to be used to solve the maze.
	 */
	@Override
	public void solveMaze(String name) {
		Searcher<Position> searcher;
		SearchableMazeAdapter maze = new SearchableMazeAdapter(mazes.get(name));
		Solution<Position> sol;
		
		switch (properties.getSolveMazeAlgorithm().toUpperCase()) {
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
			String msg = "Not a valid maze";
			notifyObservers("warning " + msg);		}
		return new Solution<Position>();
	}

	
	/**
	 * Stops all maze generation threads and dies.
	 */
	@Override
	public void exit() {
		executor.shutdown();
	}

	@Override
	public void saveMazeToDB(String name) {
		so.setJavaObject(mazes.get(name));
		try {
			so.saveObject("mazes", name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setChanged();
		notifyObservers("maze_saved " + name);
	}

	@Override
	public void loadMazeFromDB(String name) {
		Maze3d maze;
		try {
			maze = (Maze3d) so.getObject("mazes", name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			maze = new Maze3d();
		}
		mazes.put(name, maze);
		setChanged();
		notifyObservers("maze_loaded " + name);
	}
		
}
