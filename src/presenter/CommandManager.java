package presenter;

import java.util.HashMap;

import presenter.Command;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import model.Model;
import algorithms.search.Solution;
import view.View;

public class CommandManager {
	
	private Model model;
	private View view;
	
	/**C`TOR
	 * 
	 * @param model
	 * @param view
	 * @author Adi Haviv & Bar Genish
	 */
	public CommandManager(Model model, View view) {
		this.model = model;
		this.view = view;		
	}
	
	/**
	 * This Class creates HashMap for the Commands and their names
	 * @return HashMap<String, Command>
	 */
	public HashMap<String, Command> getCommandsMap() {
		HashMap<String, Command> commands = new HashMap<String, Command>();
		commands.put("generate_maze", new GenerateMazeCommand());
		commands.put("display", new DisplayMazeCommand());
		commands.put("dir", new GetDirectoryContentsCommand());
		commands.put("display_cross_section", new GetCrossSectionByCommand());
		commands.put("save_maze", new SaveMazeToFileCommand());
		commands.put("load_maze", new LoadMazeFromFileCommand());
		commands.put("solve", new SolveMazeCommand());
		commands.put("display_solution", new DisplaySolutionCommand());
		commands.put("exit", new ExitCommand());
		
		return commands;
	}
	
	/**
	 * This Class defines the objects that will be used for `generate Maze` method in class model
	 * This Class`s functionality is implement in the doCommand method 
	 */
	public class GenerateMazeCommand implements Command {
		
		@Override
		public void doCommand(String[] args){
			if(args.length == 4){
				String name = args[0];
				int rows = Integer.parseInt(args[1]);
				int columns = Integer.parseInt(args[2]);
				int floors = Integer.parseInt(args[3]);
				model.generateMaze(name, rows, columns , floors);
			}
			else
				System.out.println("Not enough arguments recieved");
		}
	}
	
	/**
	 * This Class defines the objects that will be used for `Get Directory Contents` method in class model
	 * This Class`s functionality is implement in the doCommand method 
	 */
	public class GetDirectoryContentsCommand implements Command{
		@Override
		public void doCommand(String[] args){
			String path = args[0];
			String dir = model.getDirectoryContents(path);
			view.displayDirectoryContents(dir);
		}
	}
	

	/**
	 * This Class defines the objects that will be used for `Get Cross Section` method in class model
	 * This Class`s functionality is implement in the doCommand method 
	 */
	public class GetCrossSectionByCommand implements Command{
		@Override
		public void doCommand(String[] args){
			try{
			int index = Integer.parseInt(args[0]);
			char axis = args[1].charAt(0);
			String name = args[2];
			int[][] maze2d = model.getCrossSectionBy(index, axis, name);
			view.displayCrossSectionBy(maze2d);
			}
		catch(NumberFormatException e){
			System.out.println("Not a valid index");
			}
		}
	}
	
	/**
	 * This Class defines the objects that will be used for `Save Maze To File` method in class model
	 * This Class`s functionality is implement in the doCommand method 
	 */
	public class SaveMazeToFileCommand implements Command{
		@Override
		public void doCommand(String[] args){
			String name = args[0];
			String filename = args[1];
			model.saveMazeToFile(name, filename);
		}
	}

	/**
	 * This Class defines the objects that will be used for `Load Maze From File` method in class model
	 * This Class`s functionality is implement in the doCommand method 
	 */
	public class LoadMazeFromFileCommand implements Command {
		@Override
		public void doCommand(String[] args){
			String filename = args[0];
			String name = args[1];
			model.loadMazeFromFile(filename, name);
		}
	}
	

	/**
	 * This Class defines the objects that will be used for `Solve Maze` method in class model
	 * This Class`s functionality is implement in the doCommand method 
	 */
	public class SolveMazeCommand implements Command{
		@Override
		public void doCommand(String[] args){
			String name = args[0];
			String algorithm = args[1];
			model.solveMaze(name, algorithm);
		}
	}

	/**
	 * This Class defines the objects that will be used for `Get Maze Solution` method in class model
	 * This Class`s functionality is implement in the doCommand method 
	 */
	public class GetMazeSolutionCommand implements Command{
		@Override
		public void doCommand(String[] args){
			String name = args[0];
			model.getMazeSolution(name);
		}
	}

	/**
	 * This Class defines the objects that will be used for `Exit` method in class model
	 * This Class`s functionality is implement in the doCommand method 
	 */
	public class ExitCommand implements Command{
		@Override
		public void doCommand(String[] args){
			model.exit();
		}
	}

	/**
	 * This Class defines the objects that will be used for `Display Maze` method in class My View
	 * This Class`s functionality is implement in the doCommand method 
	 */
	public class DisplayMazeCommand implements Command {
		@Override
		public void doCommand(String[] args){
			String name = args[0];
			Maze3d maze = model.getMaze(name);
			view.displayMaze(maze);
		}
	}	

	/**
	 * This Class defines the objects that will be used for `Display Solution` method in class My View
	 * This Class`s functionality is implement in the doCommand method 
	 */
	public class DisplaySolutionCommand implements Command{
		@Override
		public void doCommand(String[] args){
			String name = args[0];
			Solution<Position> sol = model.getMazeSolution(name);
			view.displaySolution(sol);
			
		}
	}
}
