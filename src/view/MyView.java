package view;

import javafx.beans.InvalidationListener;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import view.CLI;
import presenter.Command;
import algorithms.controller.Controller;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * <h1> MyView Class </h1>
 * This Class implements the View interface and manages all interactions with the user.
 * The Class manages the CLI object in charge of user input and running commands.
 * 
 * @author Adi Haviv & Bar Genish
 *
 */
import javafx.beans.Observable;

public class MyView implements Observable, View {

	@Override
	public void addListener(InvalidationListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeListener(InvalidationListener listener) {
		// TODO Auto-generated method stub

	}
	HashMap<String, Command> commands;
	CLI cli;
	PrintWriter out;
	BufferedReader in;
	Controller controller;
	
	/**
	 * C'Tor
	 * @param in BufferedReader to be used for input.
	 * @param out PrintWriter to be used for output.
	 */
	public MyView(BufferedReader in, PrintWriter out){
		this.in = in;
		this.out = out;
		
		this.cli = new CLI(in,out);
	}
	
	/**
	 * Sets MyView's controller.
	 * @param controller
	 */
	@Override
	public void setController(Controller controller){
		this.controller = controller;
	}
	
	/**
	 * Starts the cli and the programs functionality.
	 */
	@Override
	public void start(){
		cli.start();
	}
	
	/**
	 * Notify user maze generation is complete.
	 */
	@Override
	public void notifyMazeIsReady(String name) {
		String output = name + " maze is ready, Have Fun!";
		cli.write(output);

	}

	/**
	 * Prints a maze to user output stream.
	 */
	@Override
	public void displayMaze(Maze3d maze) {
		try{
			cli.write(maze.toString());
		}catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the commands hashmap for MyView and CLI.
	 */
	@Override
	public void setCommands(HashMap<String, Command> commands) {
		this.commands = commands;
		cli.setCommands(commands);
	}

	/**
	 * Displays a directory's contents.
	 * @param dirContents
	 */
	@Override
	public void displayDirectoryContents(String dirContents) {
		cli.write(dirContents);
		
	}

	/**
	 * Displays a maze's cross section.
	 */
	@Override
	public void displayCrossSectionBy(int[][] maze2d) {
		cli.write(maze2d.toString());
		
	}

	/**
	 * Notify user maze is saved to file.
	 */
	@Override
	public void notifyMazeIsSaved(String name) {
		String output = name + " maze was saved succesfully";
		cli.write(output);
		
	}

	/**
	 * Notify user maze is loaded to memory.
	 */
	@Override
	public void notifyMazeIsLoaded(String name) {
		String output = name + " maze was loaded succesfully";
		cli.write(output);
		
	}

	/**
	 * Notify user a solution is available for the given maze.
	 */
	@Override
	public void notifyMazeIsSolved(String name) {
		String output = name + " maze was solved";
		cli.write(output);		
	}

	/**
	 * Display a maze's solution to user.
	 */
	@Override
	public void displaySolution(Solution<Position> sol){
		cli.write(sol.toString());	
	}
	
	@Override
	public void write(String str){
		cli.write(str);
	}
	
	
}
