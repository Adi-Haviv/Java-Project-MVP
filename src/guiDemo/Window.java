package guiDemo;

import java.util.Observable;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class Window extends Observable {
	
		protected Display display;
		protected Shell shell;	
		
		protected abstract void initWidgets();
		
		public void start() {
			display = new Display();
			shell = new Shell(display);
	        
			initWidgets();
			shell.open();		
			
			// main event loop
			while(!shell.isDisposed()){ 
			   if(!display.readAndDispatch()){ 	
			      display.sleep();  
			   }
			
			}

			display.dispose(); 
		}
	}