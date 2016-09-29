package guiDemo;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;

public class Character {
	private Position pos;
	private Image img;
	
	public Character() {
		try {
			img = new Image(null, "Images/Splash.png");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}
	public void draw(int cellWidth, int cellHeight, GC gc) {
		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 
				cellWidth * pos.getCoords()[0], cellHeight * pos.getCoords()[1], cellWidth, cellHeight);
	}
	
	public void setImage(String s){
		try{
			img = new Image(null, s);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	public void moveRight() {
		int[] coords = pos.getCoords();
		pos.setCoords(coords[0] + 1, coords[1], coords[2]);
	}
	
	public void moveLeft() {
		int[] coords = pos.getCoords();
		pos.setCoords(coords[0] - 1, coords[1], coords[2]);
	}
	
	public void moveUp() {
		int[] coords = pos.getCoords();
		pos.setCoords(coords[0], coords[1], coords[2] + 2);
	}
	public void moveDown() {
		int[] coords = pos.getCoords();
		pos.setCoords(coords[0], coords[1], coords[2] - 2);
	}
		
	public void moveFwd() {
		int[] coords = pos.getCoords();
		pos.setCoords(coords[0], coords[1] + 1, coords[2]);
	}
	public void moveBwd() {
		int[] coords = pos.getCoords();
		pos.setCoords(coords[0], coords[1] - 1, coords[2]);
	}
	
}
