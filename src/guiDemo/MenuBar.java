package guiDemo;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class MenuBar extends Observable{

	public MenuBar(Shell shell){
		initUI(shell);
	}
       
	protected void initUI(Shell shell){
		Menu menuBar = new Menu(shell, SWT.BAR);
	   
	  	MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
	    cascadeFileMenu.setText("&File");
	
	    Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
	    cascadeFileMenu.setMenu(fileMenu);
	
	    MenuItem subGenerateItem = new MenuItem(fileMenu, SWT.CASCADE);
	    subGenerateItem.setText("Generate Maze");
	    subGenerateItem.addListener(SWT.Selection, event-> {
	    	showGenerateMazeOptions();
	     });
	    
	    MenuItem subSaveItem = new MenuItem(fileMenu, SWT.CASCADE);
	    subSaveItem.setText("Save Maze");
	    subSaveItem.addListener(SWT.Selection, event-> {
	    	setChanged();
	    	notifyObservers("save_maze");
	     });
	    
	    MenuItem subLoadItem = new MenuItem(fileMenu, SWT.CASCADE);
	    subLoadItem.setText("Load Maze From File");
	    subLoadItem.addListener(SWT.Selection, event-> {
	    	loadMaze();
	     });
	    
	    MenuItem subDirItem = new MenuItem(fileMenu, SWT.CASCADE);
	    subDirItem.setText("Show Directory Content");
	    subDirItem.addListener(SWT.Selection, event-> {
	    	showDirectoryContent();
	     });
	    
	    MenuItem cascadeDisplayMenu = new MenuItem(menuBar, SWT.CASCADE);
	    cascadeDisplayMenu.setText("&Display");
	
	    Menu displayMenu = new Menu(shell, SWT.DROP_DOWN);
	    cascadeDisplayMenu.setMenu(displayMenu);
	    
	    MenuItem subMazeItem = new MenuItem(displayMenu, SWT.CASCADE);
	    subMazeItem.setText("Display Maze");
	    
	    MenuItem subCrossSectionItem = new MenuItem(displayMenu, SWT.CASCADE);
	    subCrossSectionItem.setText("Display Cross Section By...");
	    
	    MenuItem subSolutionItem = new MenuItem(displayMenu, SWT.CASCADE);
	    subSolutionItem.setText("Display Solution");
	    
	    MenuItem cascadeGameMenu = new MenuItem(menuBar, SWT.CASCADE);
	    cascadeGameMenu.setText("&Game");
	
	    Menu gameMenu = new Menu(shell, SWT.DROP_DOWN);
	    cascadeGameMenu.setMenu(gameMenu);
	    
	    MenuItem subHintItem = new MenuItem(gameMenu, SWT.CASCADE);
	    subHintItem.setText("Hit Me With a Hint");
	    
	    MenuItem subSolveItem = new MenuItem(gameMenu, SWT.CASCADE);
	    subSolveItem.setText("Please Master, Solve Me The Maze");
	    subSolveItem.addListener(SWT.Selection, event-> {
	    	setChanged();
	    	notifyObservers("solve");
	     });
	    
	    MenuItem propertiesItem = new MenuItem(menuBar, SWT.PUSH);
	    propertiesItem.setText("&Properties");
	    propertiesItem.addListener(SWT.SELECTED, event->{
	    	openProperties();
	    });
	    
	    MenuItem exitItem = new MenuItem(menuBar, SWT.PUSH);
	    exitItem.setText("&Exit");
	    exitItem.addListener(SWT.Selection, event-> {
        	setChanged();
        	notifyObservers("exit");
	    });
	  
	    shell.setMenuBar(menuBar);
		        
	}
	protected void showGenerateMazeOptions() {
		Shell shell = new Shell();
		shell.setText("Generate Maze");
		shell.setSize(300, 200);
		
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
		
		Label lblName = new Label(shell, SWT.NONE);
		lblName.setText("Name: ");	
		Text txtName = new Text(shell, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
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
				notifyObservers("generate_maze " + txtName.getText() + " " + txtRows.getText() + " " + txtColumns.getText() + " " + txtFloors.getText() + " ");
				shell.close();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
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
				notifyObservers("dir " + txtPath.getText());
				shell.close();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});	
	}
	
	protected void loadMaze() {
		Shell shell = new Shell();
		shell.setText("Maze Loader");
		shell.setSize(300, 200);
		
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
		
		Label lblPath = new Label(shell, SWT.NONE);
		lblPath.setText("Path: ");	
		Text txtPath = new Text(shell, SWT.BORDER);
		txtPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label lblMazeName = new Label(shell, SWT.NONE);
		lblMazeName.setText("Name: ");	
		Text txtMazeName = new Text(shell, SWT.BORDER);
		txtMazeName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Button btnload = new Button(shell, SWT.PUSH);
		btnload.setText("Load Maze");
		btnload.addSelectionListener(new SelectionListener() {
		
		@Override
		public void widgetSelected(SelectionEvent arg0) {
		setChanged();
		notifyObservers("load_maze " + txtMazeName.getText() + " " + txtPath.getText());
		shell.close();
		}
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
		
		}
		});
		}

	protected void displaymaze(){
		
	}

	protected void openProperties(){};
}