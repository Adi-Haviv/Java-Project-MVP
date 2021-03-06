package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import model.Model;
import view.View;
import presenter.CommandManager;

public class Presenter implements Observer {
		@SuppressWarnings("unused")
		private Model model;
		private View view;
		private CommandManager commandManager;
		private HashMap<String, Command> commands;
		
		public Presenter(Model model, View view) {
			this.model = model;
			this.view = view;
				
			commandManager = new CommandManager(model, view);
			commands = commandManager.getCommandsMap();
		}
		
		
		@Override
		public void update(Observable o, Object arg) {
			String commandLine = (String)arg;
			String arr[] = commandLine.split(" ");
			String command = arr[0];			
			
			if(!commands.containsKey(command)) {
				view.warningMessage("Command doesn't exist");			
			}
			else {
				String[] args = null;
				if (arr.length > 1) {
					String commandArgs = commandLine.substring(
							commandLine.indexOf(" ") + 1);
					args = commandArgs.split(" ");							
				}
				Command cmd = commands.get(command);
				cmd.doCommand(args);	
			}
		}
	}