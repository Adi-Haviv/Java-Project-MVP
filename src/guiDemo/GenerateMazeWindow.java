package guiDemo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

public class GenerateMazeWindow extends DialogWindow {
	
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
	protected void initWidgets() {
		shell.setText("Generate maze window");
		shell.setSize(300, 200);		
				
		shell.setLayout(new GridLayout(3, false));	
				
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
		
		Button btnGenerateMaze = new Button(shell, SWT.PUSH);
		shell.setDefaultButton(btnGenerateMaze);
		btnGenerateMaze.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		btnGenerateMaze.setText("Generate maze");
		
		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {				
				MessageBox msg = new MessageBox(shell, SWT.OK);
				msg.setText("Title");
				//msg.setMessage("Button was clicked");
				int rows = Integer.parseInt(txtRows.getText());
				int columns = Integer.parseInt(txtColumns.getText());
				int floors = Integer.parseInt(txtFloors.getText());
				
				msg.setMessage("Generating maze with " + rows + " rows, " + columns + " columns and " + floors + " floors. Woo Pi Doo ");
				msg.open();
				shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {			
				
			}
		});	
		
		shell.addMouseWheelListener(new MouseWheelListener() {
    	    @Override
    	   public void mouseScrolled(MouseEvent g) {
    	        if((g.stateMask & SWT.CONTROL) == SWT.CONTROL) {
    	        	performZoom(g.count);
    	        	}		
                }
         });
		
	}

}
