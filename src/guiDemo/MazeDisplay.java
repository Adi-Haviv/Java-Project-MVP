package guiDemo;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

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
		crossSection = maze.getCrossSectionByZ(0);
	}

	public MazeDisplay(Composite fc, int style) {
		super(fc, style);
		character = new Character();
		character.setPos(new Position(maze.getStartPosition()));
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {				
				 
				switch (e.keyCode) {
				case SWT.ARROW_RIGHT:
//					maze.goRight(character.getPos().);
					character.moveRight();
					redraw();
					break;
				
				case SWT.ARROW_LEFT:					
					character.moveLeft();
					redraw();
					break;
				
				case SWT.ARROW_UP:
					character.moveUp();
					redraw();
					break;
				
				case SWT.ARROW_DOWN:
					character.moveDown();
					redraw();
					break;
				
				case SWT.PAGE_UP:
					character.moveFwd();
					redraw();
					break;
				
				case SWT.PAGE_DOWN:
					character.moveBwd();
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
				          if(crossSection[i][j]!=0)
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
		
		/* Irrelevant
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {	
				getDisplay().syncExec(new Runnable() {					

					@Override
					public void run() {
						
						character.moveRight();
						redraw();
					}
				});
				
			}
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 500);*/
	}

}
