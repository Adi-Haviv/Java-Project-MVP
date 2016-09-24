package guiDemo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import view.View;

public class MazeWindow extends Window implements View{

	private MazeDisplay mazeDisplay;
	private Character character;
	
	@Override
	protected void initWidgets() {
	
		GridLayout grid = new GridLayout(2, false);
		shell.setLayout(grid);
		
		Composite buttons = new Composite(shell, SWT.NONE);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		buttons.setLayout(rowLayout);
	
	////////////////////////////////////////////////////////////////////////////////
	//Generate Maze Button
	////////////////////////////////////////////////////////////////////////////////
		Button btnGenerateMaze = new Button(buttons, SWT.PUSH);
		shell.setDefaultButton(btnGenerateMaze);
		btnGenerateMaze.setText("Generate maze");
		btnGenerateMaze.addSelectionListener(new SelectionListener() {		
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				showGenerateMazeOptions();

			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	
	////////////////////////////////////////////////////////////////////////////////
	//Solve Maze Button
	////////////////////////////////////////////////////////////////////////////////
		Button btnSolveMaze = new Button(buttons, SWT.PUSH);
		shell.setDefaultButton(btnSolveMaze);
		btnSolveMaze.setText("Solve maze");
		btnSolveMaze.addSelectionListener(new SelectionListener() {		
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers("solve");
				shell.close();			
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
	
	////////////////////////////////////////////////////////////////////////////////
	//Directory Content Button
	////////////////////////////////////////////////////////////////////////////////
		Button btnDir = new Button(buttons, SWT.PUSH);
		shell.setDefaultButton(btnDir);
		btnGenerateMaze.setText("Directory Content");
		btnGenerateMaze.addSelectionListener(new SelectionListener() {		
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				showDirectoryContent();

			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		
		mazeDisplay = new MazeDisplay(shell, SWT.BORDER);	
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		mazeDisplay.setFocus();
	}
	
	protected void showGenerateMazeOptions() {
		Shell shell = new Shell();
		shell.setText("Generate Maze");
		shell.setSize(300, 200);
		
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
		
		Label lblRows = new Label(shell, SWT.NONE);
		lblRows.setText("Rows: ");	
		Text txtRows = new Text(shell, SWT.BORDER);
		txtRows.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label lblColumns = new Label(shell, SWT.NONE);
		lblColumns.setText("Columns: ");
		Text txtColumns = new Text(shell, SWT.BORDER);
		txtColumns.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label lblFloors = new Label(shell, SWT.NONE);
		lblFloors.setText("Floors: ");
		Text txtFloors = new Text(shell, SWT.BORDER);
		txtFloors.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Button btnGenerate = new Button(shell, SWT.PUSH);
		btnGenerate.setText("Generate");
		btnGenerate.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers("generate_maze" + txtRows.getText() + " " + txtColumns.getText() + " " + txtFloors.getText() + " ");
				shell.close();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mazeDisplay = new MazeDisplay(shell, SWT.NONE);			
		shell.open();
		
		shell.addMouseWheelListener(new MouseWheelListener() {
		    @Override
		   public void mouseScrolled(MouseEvent g) {
		        if((g.stateMask & SWT.CONTROL) == SWT.CONTROL) {
		        	performZoom(g.count);
		        	}		
	            }
	     });
		
		mazeDisplay = new MazeDisplay(shell, SWT.NONE);			
		shell.open();
		
		shell.addMouseWheelListener(new MouseWheelListener() {
		    @Override
		   public void mouseScrolled(MouseEvent g) {
		        if((g.stateMask & SWT.CONTROL) == SWT.CONTROL) {
		        	performZoom(g.count);
		        	}		
	            }
	     });
	}

	protected void showDirectoryContent() {
		Shell shell = new Shell();
		shell.setText("Directory Content");
		shell.setSize(300, 200);
		
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
		
		Label lblPath = new Label(shell, SWT.NONE);
		lblPath.setText("Please Insert The Directory Path: ");	
		Text txtPath = new Text(shell, SWT.BORDER);
		txtPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Button btnDir = new Button(shell, SWT.PUSH);
		btnDir.setText("Show Content");
		btnDir.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers("dir" + txtPath.getText());
				shell.close();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mazeDisplay = new MazeDisplay(shell, SWT.NONE);			
		shell.open();
		
		shell.addMouseWheelListener(new MouseWheelListener() {
		    @Override
		   public void mouseScrolled(MouseEvent g) {
		        if((g.stateMask & SWT.CONTROL) == SWT.CONTROL) {
		        	performZoom(g.count);
		        	}		
	            }
	     });
		
		mazeDisplay = new MazeDisplay(shell, SWT.NONE);			
		shell.open();
		
		shell.addMouseWheelListener(new MouseWheelListener() {
		    @Override
		   public void mouseScrolled(MouseEvent g) {
		        if((g.stateMask & SWT.CONTROL) == SWT.CONTROL) {
		        	performZoom(g.count);
		        	}		
	            }
	     });
	}

	@Override
	public void displayMessage(String msg) {
		// TODO Auto-generated method stub
		
	}
	////////////////////////////////////////////////////////////////////////////////
	//Perfom zoom
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
}

