package view;


import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import guiDemo.MazeWindow;
import properties.Properties;
import properties.PropertiesLoader;
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
	public void update(Observable o, Object arg) {
			setChanged();
			notifyObservers(arg);
	}

	@Override
	public void warningMessage(String msg) {
		MessageBox messageBox = new MessageBox(new Shell(),SWT.ICON_WARNING | SWT.OK );
		messageBox.setMessage(msg);
		messageBox.setText("Great, There are warnings thanks to you");
		messageBox.open();	
	}
	
	
}
