package Boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import guiDemo.MazeWindow;
import model.MyModel;
import presenter.Presenter;
import properties.Properties;
import view.MyView;


public class Run {
	
	public static void main(String[] args) {
		Presenter presenter;
		Properties properties = new Properties();
		MyView view;
		MyModel model = new MyModel();	

		if(properties.getUserInterface().equalsIgnoreCase("CLI")){
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter out = new PrintWriter(System.out);		
			view = new MyView(in, out);
		}
		else{
			view = new MyView();
			MazeWindow win = new MazeWindow();	
			win.addObserver(presenter);
			win.start();
		}	
		presenter = new Presenter(model,view);
		model.addObserver(presenter);
		view.addObserver(presenter);				
		view.start();
	}

}
