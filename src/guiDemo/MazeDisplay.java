package guiDemo;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;

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
	
	public void setMazeData(Maze3d maze) {	
		this.maze = maze;
		character.setPos(new Position(maze.getStartPosition()));
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
			character.setImage("images/PinkyBoth.png");
		} else if(moves.contains("Up") && !moves.contains("Down")){
			character.setImage("images/PinkyUp.png");
		} else if(!moves.contains("Up") && moves.contains("Down")){
			character.setImage("images/PinkyDown.png");
		} else {
			character.setImage("images/PinkyClear.png");
		}
	}
	public MazeDisplay(Composite fc, int style) {
		super(fc, style);
		character = new Character();
		character.setPos(new Position());
		crossSection = new int[1][1];
		crossSection[0][0] = 0;
		
		this.addListener(MyEvent, event ->{
			
		});
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {				
				Position movePos = new Position(character.getPos());
				switch (e.keyCode) {
				case SWT.ARROW_RIGHT:
					if(maze.goRight(movePos.getCoords())[0] != -1){
						character.moveRight();
						refreshCharacter();
						redraw();
					}
					break;
				
				case SWT.ARROW_LEFT:
					if(maze.goLeft(movePos.getCoords())[0] != -1){
						character.moveLeft();
						refreshCharacter();
						redraw();	
					}
					break;
				
				case SWT.ARROW_DOWN:
					if(maze.goFwd(movePos.getCoords())[0] != -1){
						character.moveFwd();
						refreshCharacter();
						redraw();
					}
					break;
				
				case SWT.ARROW_UP:
					if(maze.goBack(movePos.getCoords())[0] != -1){
						character.moveBwd();
						refreshCharacter();
						redraw();
					}
					break;
				
				case SWT.PAGE_UP:
					if(maze.goUp(movePos.getCoords())[0] != -1){
						character.moveUp();
						refreshCrossSection();
						refreshCharacter();
						redraw();
					}
					break;
				
				case SWT.PAGE_DOWN:
					if(maze.goDown(movePos.getCoords())[0] != -1){
						character.moveDown();
						refreshCrossSection();
						refreshCharacter();
						redraw();
					}
					break;
					
				default:
					redraw();
					break;
				}
			}
		});
		
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
						   character.setImage("Images/Finish.png");
					   } else if(character.getPos().getCoords()[2] == maze.getGoalPosition().getCoords()[2]){
						   Image img = new Image(null, "Images/Brain.png");
						   e.gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, w * maze.getGoalPosition().getCoords()[0], h * maze.getGoalPosition().getCoords()[1], w, h);
					   }

				   character.draw(w, h, e.gc);				   
			}
		});
	}

	public void solve(Solution<Position> sol) {
		for(State<Position> s: sol.getStates()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			character.setPos((Position) s.getValue());
			refreshCrossSection();
			refreshCharacter();
			redraw();
		}
	}
	
	
}
