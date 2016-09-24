package Boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import guiDemo.MazeWindow;
import model.MyModel;
import presenter.Presenter;
import view.MyView;


public class Run {

	public static void main(String[] args) {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
				
		MyView view = new MyView(in, out);
		MyModel model = new MyModel();
		
		Presenter presenter = new Presenter(model, view);
		model.addObserver(presenter);
		view.addObserver(presenter);
//				
//		view.start();
//		
//		the commands for the gui 
		MazeWindow win = new MazeWindow();
		
//		win.addObserver(presenter);
		win.start();
	}

}
