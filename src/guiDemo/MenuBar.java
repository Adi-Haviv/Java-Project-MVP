package guiDemo;

import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Observable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import Boot.Run;
import properties.Properties;
import properties.PropertiesLoader;

public class MenuBar extends Observable{
	private String currentMaze;
	
	public MenuBar(Shell shell){
		initUI(shell);
	}
       
	protected void initUI(Shell shell){
		Menu menuBar = new Menu(shell, SWT.BAR);
		
	  	MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
	    cascadeFileMenu.setText("&File");
	
	    Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
	    cascadeFileMenu.setMenu(fileMenu);
	
	    MenuItem subGenerateItem = new MenuItem(fileMenu, SWT.PUSH);
	    subGenerateItem.setText("Generate Maze");
	    subGenerateItem.addListener(SWT.Selection, event-> {
	    	showGenerateMazeOptions(shell);
	     });
	    
	    MenuItem subSaveItem = new MenuItem(fileMenu, SWT.PUSH);
	    subSaveItem.setText("Save Maze");
	    subSaveItem.addListener(SWT.Selection, event-> {
	    	setChanged();
	    	notifyObservers("save_maze " + currentMaze);
	    	
	     });
	    
	    MenuItem subLoadItem = new MenuItem(fileMenu, SWT.PUSH);
	    subLoadItem.setText("Load Maze From DB");
	    subLoadItem.addListener(SWT.Selection, event-> {
	    	loadMaze();
	     });
	    
	    MenuItem subDirItem = new MenuItem(fileMenu, SWT.PUSH);
	    subDirItem.setText("Show Directory Content");
	    subDirItem.addListener(SWT.Selection, event-> {
	    	showDirectoryContent();
	     });
	    
	    MenuItem cascadeDisplayMenu = new MenuItem(menuBar, SWT.CASCADE);
	    cascadeDisplayMenu.setText("&Game");
	
	    Menu gameMenu = new Menu(shell, SWT.DROP_DOWN);
	    cascadeDisplayMenu.setMenu(gameMenu);

	    MenuItem subSolutionItem = new MenuItem(gameMenu, SWT.PUSH);
	    subSolutionItem.setText("Display Solution");
	    subSolutionItem.addListener(SWT.Selection, event->{
	    	setChanged();
	    	notifyObservers("solve " + currentMaze);
	    });
	    
	    MenuItem subHintItem = new MenuItem(gameMenu, SWT.PUSH);
	    subHintItem.setText("Hit Me With a Hint");
	    
	    subHintItem.addListener(SWT.Selection, event->{
	    	setChanged();
	    	notifyObservers("hint " + currentMaze);
	    });
	    MenuItem cascadePropertiesMenu = new MenuItem(menuBar, SWT.CASCADE);
	    cascadePropertiesMenu.setText("&Properties");
	    
	    Menu propertiesMenu = new Menu(shell, SWT.DROP_DOWN);
	    cascadePropertiesMenu.setMenu(propertiesMenu);
	    
	    MenuItem subImportItem = new MenuItem(propertiesMenu, SWT.PUSH);
	    subImportItem.setText("Import Properties from XML");
	    subImportItem.addListener(SWT.Selection, event->{
	    	importProperties();
	    });
	    
	    MenuItem subGeneratePropItem = new MenuItem(propertiesMenu, SWT.PUSH);
	    subGeneratePropItem.setText("Generate Properties");
	    subImportItem.addListener(SWT.Selection, event->{
	    	generateProperties();
	    });
	    
	    MenuItem cascadeExitMenu = new MenuItem(menuBar, SWT.CASCADE);
	    cascadeExitMenu.setText("&Exit");
	    
	    Menu exitMenu = new Menu(shell, SWT.DROP_DOWN);
	    cascadeExitMenu.setMenu(exitMenu);
	    
	    MenuItem exitItem = new MenuItem(exitMenu, SWT.PUSH);
	    exitItem.setText("&Exit");
	    exitItem.addListener(SWT.Selection, event->{
	    	setChanged();
        	notifyObservers("exit");
        	shell.dispose();
	    });
	  
	    shell.setMenuBar(menuBar);
		        
	}
	
	protected void showGenerateMazeOptions(Shell s) {
		Shell shell = new Shell(s);
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
				currentMaze = txtName.getText();
				setChanged();
				notifyObservers("generate_maze " + currentMaze + " " + txtRows.getText() + " " + txtColumns.getText() + " " + txtFloors.getText() + " ");
				shell.close();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		shell.open();
		
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
		
		shell.open();
	}
	
	protected void loadMaze() {
		Shell shell = new Shell();
		shell.setText("Maze Loader");
		shell.setSize(300, 200);
		
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
		
		Label lblMazeName = new Label(shell, SWT.NONE);
		lblMazeName.setText("Name: ");	
		Text txtMazeName = new Text(shell, SWT.BORDER);
		txtMazeName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Button btnload = new Button(shell, SWT.PUSH);
		btnload.setText("Load Maze");
		btnload.addSelectionListener(new SelectionListener() {
		
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				currentMaze = txtMazeName.getText();
				setChanged();
				notifyObservers("load_maze " + currentMaze);
				shell.close();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub
			
			}
		});
		
		shell.open();
	}
	
	protected void importProperties(){
		String[] FILTER_NAMES = {"eXtensible Markup Language Files (*.XML)"};
		String[] FILTER_EXTS = { "*.XML"};
		Shell shell = new Shell();
		shell.setText("File Dialog");
		 
		shell.setLayout(new GridLayout(5, true));
		 
		new Label(shell, SWT.NONE).setText("Properties File Path: ");

	    final Text fileName = new Text(shell, SWT.BORDER);
	    GridData data = new GridData(GridData.FILL_HORIZONTAL);
	    data.horizontalSpan = 4;
	    
	    fileName.setLayoutData(data);
	    
	    Button open = new Button(shell, SWT.PUSH);
	    open.setText("open");
		open.addSelectionListener(new SelectionListener() {
		
			@Override
			public void widgetSelected(SelectionEvent event) {
		        FileDialog dlg = new FileDialog(shell, SWT.OPEN);
		        dlg.setFilterNames(FILTER_NAMES);
		        dlg.setFilterExtensions(FILTER_EXTS);
		        String fn = dlg.open();
		        if (fn != null) {
		          fileName.setText(fn);
		        }
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
	    });
	    
	    Button importProperties = new Button(shell, SWT.PUSH);
	    importProperties.setText("Import");
	    importProperties.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent event) {
				PropertiesLoader loader= new PropertiesLoader(fileName.getText());	
				Properties properties = loader.getProperties();
				try {
					XMLEncoder xml = new XMLEncoder(new FileOutputStream("resources/Properties.xml"));
					xml.writeObject(properties);
					xml.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
				File currentJar;
				try {
					currentJar = new File(Run.class.getProtectionDomain().getCodeSource().getLocation().toURI());
					/* is it a jar file? */
					if(!currentJar.getName().endsWith(".jar"))
						return;
					
					/* Build command: java -jar application.jar */
					final ArrayList<String> command = new ArrayList<String>();
					command.add(javaBin);
					command.add("-jar");
					command.add(currentJar.getPath());
					
					final ProcessBuilder builder = new ProcessBuilder(command);
					try {
						builder.start();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.exit(0);
					shell.dispose();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
	    });
	    
		shell.open();
	}
	
	//TODO add loader to properties
	protected void generateProperties(){};
}