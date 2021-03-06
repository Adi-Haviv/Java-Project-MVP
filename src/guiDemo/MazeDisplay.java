package guiDemo;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;


public class MazeDisplay extends Canvas {
	private Maze3d maze;
	private int[][] crossSection; 
	private Character character;
	boolean keyListenerActive = false;
	KeyListener keylisten;
	
	public void setMazeData(Maze3d maze) {	
		this.maze = maze;
		character.setPos(new Position(maze.getStartPosition()));
		checkGoal();
		refreshCrossSection();
		refreshCharacter();
		redraw();
	}
	
	private void refreshCrossSection(){
		crossSection = maze.getCrossSectionByZ(character.getPos().getCoords()[2]);
	}
	
	private void refreshCharacter(){
		List<String> moves = new ArrayList<String>();
		for (String s : maze.getPossibleMoves(character.getPos())){
			moves.add(s);
		}
		if(moves.contains("Up") && moves.contains("Down")){
			character.setImage("resources/PinkyBoth.png");
		} else if(moves.contains("Up") && !moves.contains("Down")){
			character.setImage("resources/PinkyUp.png");
		} else if(!moves.contains("Up") && moves.contains("Down")){
			character.setImage("resources/PinkyDown.png");
		} else {
			character.setImage("resources/PinkyClear.png");
		}
	}
	public MazeDisplay(Composite fc, int style) {
		super(fc, style);
		character = new Character();
		character.setPos(new Position());
		crossSection = new int[1][1];
		crossSection[0][0] = 0;
		
		keylisten = new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {				
				Position movePos = new Position(character.getPos());
				switch (e.keyCode) {
				case SWT.ARROW_RIGHT:
					if(maze.goRight(movePos.getCoords())[0] != -1){
						character.moveRight();
						checkGoal();
						refreshCharacter();
						redraw();
					}
					break;
				
				case SWT.ARROW_LEFT:
					if(maze.goLeft(movePos.getCoords())[0] != -1){
						character.moveLeft();
						checkGoal();
						refreshCharacter();
						redraw();	
					}
					break;
				
				case SWT.ARROW_DOWN:
					if(maze.goFwd(movePos.getCoords())[0] != -1){
						character.moveFwd();
						checkGoal();
						refreshCharacter();
						redraw();
					}
					break;
				
				case SWT.ARROW_UP:
					if(maze.goBack(movePos.getCoords())[0] != -1){
						character.moveBwd();
						checkGoal();
						refreshCharacter();
						redraw();
					}
					break;
				
				case SWT.PAGE_UP:
					if(maze.goUp(movePos.getCoords())[0] != -1){
						character.moveUp();
						checkGoal();
						refreshCrossSection();
						refreshCharacter();
						redraw();
					}
					break;
				
				case SWT.PAGE_DOWN:
					if(maze.goDown(movePos.getCoords())[0] != -1){
						character.moveDown();
						checkGoal();
						refreshCrossSection();
						refreshCharacter();
						redraw();
					}
					break;
					
				default:
					checkGoal();
					refreshCrossSection();
			        refreshCharacter();
					redraw();
					break;
				}
			}
		};
		
		this.addKeyListener(keylisten	);
		keyListenerActive = true;
		
		this.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(new Color(null,0,0,0));
				   e.gc.setBackground(new Color(null,152,117,186));
				   

				   int width=getSize().x;
				   int height=getSize().y;

				   int w=width/crossSection[0].length;
				   int h=height/crossSection.length;

				   for(int i=0;i<crossSection.length;i++)
				      for(int j=0;j<crossSection[i].length;j++){
				          int x=j*w;
				          int y=i*h;
				          if(crossSection[j][i]!=0)
				              e.gc.fillRectangle(x,y,w,h);
				          else{
				        	  e.gc.setBackground(new Color(null,255,255,255));//paint the background in white
				        	  e.gc.fillRectangle(x,y,w,h);
				        	  e.gc.setBackground(new Color(null,152,117,186)); //set the walls in purple :P
				          }
				      }
				   if(maze != null) 
					   if(character.getPos().equals(maze.getGoalPosition())){
						   character.setImage("resources/Finish.png");
					   } else if(character.getPos().getCoords()[2] == maze.getGoalPosition().getCoords()[2]){
						   Image img = new Image(null, getClass().getClassLoader().getResourceAsStream("resources/Brain.png"));
						   e.gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, w * maze.getGoalPosition().getCoords()[0], h * maze.getGoalPosition().getCoords()[1], w, h);
					   }  

				   character.draw(w, h, e.gc);				   
			}
		});
	}

	public void solve(Solution<Position> sol) {
		int delay = 200;
		for(State<Position> s: sol.getStates()){
			this.getDisplay().timerExec(delay, new Runnable(){
				@Override
				public void run() {		
					character.setPos((Position) s.getValue());
					refreshCrossSection();
					refreshCharacter();
					try {
						Robot robot = new Robot();
						robot.keyPress(java.awt.event.KeyEvent.VK_SPACE);
						robot.keyRelease(java.awt.event.KeyEvent.VK_SPACE);
					} catch (AWTException e) {
						e.printStackTrace();
					}
				}
				
			});
			delay+=200;
		}
		
	
	}
	public void displayHint(Solution<Position> sol){
		//TODO display hint in a helpful way using solution and current position.
	}
	
	private void checkGoal(){
		if (keyListenerActive == true && character.getPos().equals(maze.getGoalPosition())){
			this.removeKeyListener(keylisten);
			keyListenerActive = false;
		} else if (keyListenerActive == false && !character.getPos().equals(maze.getGoalPosition())){
			this.addKeyListener(keylisten);
			keyListenerActive = true;
		}
	}
}
