package guiDemo;

import java.util.Observable;
import java.util.Observer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MazeWindow extends Window implements Observer{
	
	private MazeDisplay mazeDisplay;
	
	@Override
	protected void initWidgets() {
		GridLayout grid = new GridLayout(1, false);
		shell.setLayout(grid);
		
		MenuBar menu = new MenuBar(shell);
		menu.addObserver(this);
		menu.showGenerateMazeOptions(shell);

		mazeDisplay = new MazeDisplay(shell, SWT.BORDER);	
		
		shell.addListener(SWT.Close, new Listener()
	    {
	        public void handleEvent(Event event)
	        {
	            int style = SWT.APPLICATION_MODAL | SWT.YES | SWT.NO;
	            MessageBox messageBox = new MessageBox(shell, style);
	            messageBox.setText("Don't You Dare To Give Up!");
	            messageBox.setMessage("Close the shell?");
	            event.doit = messageBox.open() == SWT.YES;
	            setChanged();
	            notifyObservers("exit");
	    		shell.dispose();
	        }
	    });
		mazeDisplay.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseScrolled(MouseEvent g) {
			if((g.stateMask & SWT.CONTROL) == SWT.CONTROL) {
			performZoom(g.count, mazeDisplay);
				}
			}
		});
		
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		mazeDisplay.setFocus();
	}
	

	public void playingTheme (){
		String bip = "Images/Pinky and the Brain.mp3";
		Media hit = new Media(bip);
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();
	}
	
	////////////////////////////////////////////////////////////////////////////////
	//Perfom Zoom Method
	////////////////////////////////////////////////////////////////////////////////
	public void performZoom(int count, Control c) {
		if(count > 0){
			int width = c.getSize().x;
			int height = c.getSize().y;
			c.setSize((int)(width * 1.05), (int)(height * 1.05));
			c.redraw();
		}
	else {
			int width = c.getSize().x;
			int height = c.getSize().y;
			c.setSize((int)(width * 0.95), (int)(height * 0.95));
			c.redraw();
		}
	}
	
	public void setMaze(Maze3d maze){
		mazeDisplay.setMazeData(maze);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers(arg);
		if(arg instanceof String){
			if(arg.equals("exit")){
				shell.dispose();
			}
		}
		
	}


	public void displaySolution(Solution<Position> sol) {
		mazeDisplay.solve(sol);
	}
}


