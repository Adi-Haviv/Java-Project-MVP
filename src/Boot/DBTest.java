package Boot;

import java.util.Arrays;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import dbConnection.SaveObject;

public class DBTest {
	public static void main(String[] args) {
		GrowingTreeGenerator mg = new GrowingTreeGenerator();
		Maze3d maze = mg.generate(5, 5, 5);
		System.out.println(Arrays.deepToString(maze.getCrossSectionByX(2)));
		SaveObject so = new SaveObject();
		
		so.setJavaObject(maze);
		try {
			so.saveObject("itzik1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Maze3d newMaze = (Maze3d) so.getObject("itzik1");
			System.out.println(Arrays.deepToString(newMaze.getCrossSectionByX(2)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
