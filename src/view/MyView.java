package view;


import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import guiDemo.MazeWindow;
import properties.Properties;
import view.Cli;


/**
 * <h1> MyView Class </h1>
 * This Class implements the View interface and manages all interactions with the user.
 * The Class manages the CLI object in charge of user input and running commands.
 * 
 * @author Adi Haviv & Bar Genish
 *
 */

public class MyView extends Observable implements View, Observer {
	private Properties properties;	
	Cli cli;
	MazeWindow gui;
	PrintWriter out;
	BufferedReader in;

	/**
	 * C'Tor
	 * @param in BufferedReader to be used for input.
	 * @param out PrintWriter to be used for output.
	 */
	//C'TOR for GUI
	
	public MyView(){
		gui = new MazeWindow();
		gui.addObserver(this);
	}
	
	//C'TOR for CLI
	public MyView(BufferedReader in, PrintWriter out){
		this.in = in;
		this.out = out;
		
		
		this.cli = new Cli(in,out);
		cli.addObserver(this);

	}
		
	/**
	 * Starts the cli and the programs functionality.
	 */
	@Override
	public void start(){
		if(properties.getUserInterface().equalsIgnoreCase("cli")){
		cli.start();
		}
		else{
		gui.start();
		}
	}
	
	@Override
	public void displayMessage(String msg) {
		if(properties.getUserInterface().equalsIgnoreCase("cli")){
		out.println(msg);
		out.flush();
		}
		else{
		MessageBox messageBox = new MessageBox(new Shell(), SWT.OK );
		messageBox.setMessage(msg);
		messageBox.setText("This is a Message Box");
		messageBox.open();	
		}
	}
	
	@Override
	public void displayMaze(Maze3d maze){
		if(properties.getUserInterface().equalsIgnoreCase("gui")){
			gui.setMaze(maze);
		}
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
			setChanged();
			notifyObservers(arg);
	}
	
	@Override
	public void displayHint(Solution<Position> sol){
		gui.displayHint(sol);
	}
	
	@Override
	public void warningMessage(String msg) {
		MessageBox messageBox = new MessageBox(new Shell(),SWT.ICON_WARNING | SWT.OK );
		messageBox.setMessage(msg);
		messageBox.setText("Great, There are warnings thanks to you");
		messageBox.open();	
	}
	
	@Override
	public void mazeSolved(String name){
		if(properties.getUserInterface().equalsIgnoreCase("cli")){
			displayMessage(name + " maze was solved.");
		}
		else{
			setChanged();
			notifyObservers("display_solution " + name);
		}
	}
	public void setProperties(Properties p){
		this.properties = p;
	}

	@Override
	public void displaySolution(Solution<Position> sol) {
		if(properties.getUserInterface().equalsIgnoreCase("cli")){
			displayMessage(sol.toString());
		} else {
			gui.displaySolution(sol);
		}
	}
}
