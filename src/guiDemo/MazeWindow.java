package guiDemo;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import view.View;

public class MazeWindow extends Window implements View, Observer{

	private MazeDisplay mazeDisplay;
	
	@Override
	protected void initWidgets() {
	
		GridLayout grid = new GridLayout(1, false);
		shell.setLayout(grid);

		MenuBar menu = new MenuBar(shell);		
		menu.addObserver(this);
		
		shell.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseScrolled(MouseEvent g) {
			if((g.stateMask & SWT.CONTROL) == SWT.CONTROL) {
			performZoom(g.count);
			}		
			}
			});
		
		mazeDisplay = new MazeDisplay(shell, SWT.BORDER);	
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		mazeDisplay.setFocus();
	}

	@Override
	public void displayMessage(String msg) {
		// TODO Auto-generated method stub
		
	}
	
	////////////////////////////////////////////////////////////////////////////////
	//Perfom Zoom Method
	////////////////////////////////////////////////////////////////////////////////
	public void performZoom(int count) {
		if(count > 0){
			int width = shell.getSize().x;
			int height = shell.getSize().y;
			shell.setSize((int)(width * 1.05), (int)(height * 1.05));
		}
	else {
		int width = shell.getSize().x;
		int height = shell.getSize().y;
		shell.setSize((int)(width * 0.95), (int)(height * 0.95));
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers(arg);
		
	}


}


