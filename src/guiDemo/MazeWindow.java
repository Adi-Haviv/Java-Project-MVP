package guiDemo;

import java.util.Observable;
import java.util.Observer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;

import algorithms.mazeGenerators.Maze3d;

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
}


