package Boot;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class test {
	
	public static void main(String[] args) {
		System.out.println(getArchFilename("lib/swt"));
	}
	
	public static String getArchFilename(String prefix) 
	{ 
	   return prefix + "_" + getOSName() + "_" + getArchName() + ".jar"; 
	} 

	private static String getOSName() 
	{ 
	   String osNameProperty = System.getProperty("os.name"); 

	   if (osNameProperty == null) 
	   { 
	       throw new RuntimeException("os.name property is not set"); 
	   } 
	   else 
	   { 
	       osNameProperty = osNameProperty.toLowerCase(); 
	   } 

	   if (osNameProperty.contains("win")) 
	   { 
	       return "win"; 
	   } 
	   else if (osNameProperty.contains("mac")) 
	   { 
	       return "osx"; 
	   } 
	   else if (osNameProperty.contains("linux") || osNameProperty.contains("nix")) 
	   { 
	       return "linux"; 
	   } 
	   else 
	   { 
	       throw new RuntimeException("Unknown OS name: " + osNameProperty); 
	   } 
	} 

	private static String getArchName() 
	{ 
	   String osArch = System.getProperty("os.arch"); 

	   if (osArch != null && osArch.contains("64")) 
	   { 
	       return "64"; 
	   } 
	   else 
	   { 
	       return "32"; 
	   } 
	}
	
	public static void addJarToClasspath(File jarFile) 
	{ 
	   try 
	   { 
	       URL url = jarFile.toURI().toURL(); 
	       URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader(); 
	       Class<?> urlClass = URLClassLoader.class; 
	       Method method = urlClass.getDeclaredMethod("addURL", new Class<?>[] { URL.class }); 
	       method.setAccessible(true);         
	       method.invoke(urlClassLoader, new Object[] { url });             
	   } 
	   catch (Throwable t) 
	   { 
	       t.printStackTrace(); 
	   } 
	}
}
//
//public class Run {
//	private static void testMazeGenerator(Maze3dGenerator mg){
//		// prints the time it takes the algorithm to run
//		System.out.println(mg.measureAlgorithmTime(5,5,5));
//		
//		// generate another 3d maze
//		Maze3d maze=mg.generate(5,5,5);
//		
//		// get the maze entrance
//		Position p=maze.getStartPosition();
//		
//		// print the position
//		System.out.println(p); // format "{x,y,z}"
//		
//		// get all the possible moves from a position
//		String[] moves=maze.getPossibleMoves(p);
//		
//		// print the moves
//		for(String move : moves)
//			System.out.println(move);
//		
//		// prints the maze exit position
//		System.out.println(maze.getGoalPosition());
//		
//		try{
//			// get 2d cross sections of the 3d maze
//			int[][] maze2dx=maze.getCrossSectionByX(2);
//			System.out.println(Arrays.deepToString(maze2dx));
//			int[][] maze2dy=maze.getCrossSectionByY(5);
//			System.out.println(Arrays.deepToString(maze2dy));
//			int[][] maze2dz=maze.getCrossSectionByZ(0);
//			System.out.println(Arrays.deepToString(maze2dz));
//			// this should throw an exception!
//			maze.getCrossSectionByX(-1);
//		} catch (IndexOutOfBoundsException e){
//			System.out.println("good!");
//		}
//	}
//	
//	public static void main(String[] args) {
//		testMazeGenerator(new GrowingTreeGenerator());
//		testMazeGenerator(new SimpleMaze3dGenerator());
//	}
//}
//
//
////
////Point origin = new Point(0, 0);
////ScrollBar hBar = shell.getHorizontalBar();
////hBar.setMaximum(2120);
////hBar.setMinimum(0);
////hBar.addListener(SWT.Selection, new Listener() {
////
////    @Override
////    public void handleEvent(Event event) {
////        int hSelection = hBar.getSelection();
////        int destX = -hSelection - origin.x;
////        shell.scroll(140 + destX, 0, 140, 0, 2120, 2000, false);
////        origin.x = -hSelection;
////    }
////
////});
////ScrollBar vBar = shell.getVerticalBar();
////vBar.setMaximum(2000);
////vBar.setMinimum(0);
////vBar.addListener(SWT.Selection, new Listener() {
////
////    @Override
////    public void handleEvent(Event event) {
////        int vSelection = vBar.getSelection();
////        int destY = -vSelection - origin.y;
////        shell.scroll(0, destY, 0, 0, 2120, 2000, false);
////        origin.y = -vSelection;
////    }
////
////});
////
////shell.addListener(SWT.Resize, new Listener() {
////    @Override
////    public void handleEvent(Event e) {
////        Rectangle client = shell.getClientArea();
////        hBar.setMaximum(2120);
////        vBar.setMaximum(2000);
////        hBar.setThumb(Math.min(2120, client.width));
////        hBar.setPageIncrement(Math.min(2120, client.width));
////        vBar.setThumb(Math.min(2000, client.height));
////        vBar.setPageIncrement(Math.min(2000, client.height));
////        hBar.setIncrement(20);
////        vBar.setIncrement(20);
////        int hPage = 2120 - client.width;
////        int vPage = 2000 - client.height;
////        int hSelection = hBar.getSelection();
////        int vSelection = vBar.getSelection();
////        if (hSelection >= hPage) {
////            if (hPage <= 0)
////                hSelection = 0;
////            origin.x = -hSelection;
////        }
////        if (vSelection >= vPage) {
////            if (vPage <= 0)
////                vSelection = 0;
////            origin.y = -vSelection;
////        }
////        shell.redraw();
////    }
////});