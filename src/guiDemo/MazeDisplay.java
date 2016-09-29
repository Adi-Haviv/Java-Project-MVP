package guiDemo;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;


public class MazeDisplay extends Canvas {
	private Maze3d maze;
	private int[][] crossSection; 
	private Character character;
	
	public void setMazeData(Maze3d maze) {		
		this.maze = maze;
		crossSection = maze.getCrossSectionByZ(character.getPos().getCoords()[2]);
	}
	
	private void refreshCrossSection(){
		crossSection = maze.getCrossSectionByZ(character.getPos().getCoords()[2]);
	}
	
	public MazeDisplay(Composite fc, int style,Maze3d firstMaze) {
		super(fc, style);
		this.maze = firstMaze;
		character = new Character();
		character.setPos(new Position(maze.getStartPosition()));
		this.crossSection = this.maze.getCrossSectionByZ(character.getPos().getCoords()[2]);
		
		System.out.println(Arrays.deepToString(maze.getMaze()));
		System.out.println(Arrays.deepToString(crossSection));
		System.out.println(Arrays.toString(character.getPos().getCoords()));
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
						redraw();
					}
					break;
				
				case SWT.ARROW_LEFT:
					if(maze.goLeft(movePos.getCoords())[0] != -1){
						character.moveLeft();
						redraw();	
					}
					break;
				
				case SWT.ARROW_UP:
					if(maze.goFwd(movePos.getCoords())[0] != -1){
						character.moveFwd();
						redraw();
					}
					break;
				
				case SWT.ARROW_DOWN:
					if(maze.goBack(movePos.getCoords())[0] != -1){
						character.moveBwd();
						redraw();
					}
					break;
				
				case SWT.PAGE_UP:
					if(maze.goUp(movePos.getCoords())[0] != -1){
						character.moveUp();
						refreshCrossSection();
						redraw();
					}
					break;
				
				case SWT.PAGE_DOWN:
					if(maze.goDown(movePos.getCoords())[0] != -1){
						character.moveDown();
						refreshCrossSection();
						redraw();
					}
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
				   character.draw(w, h, e.gc);
			}
		});
	}
}
