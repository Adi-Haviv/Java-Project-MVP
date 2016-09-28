package guiDemo;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;

public class Character {
	private Position pos;
	private Image img;
	
	public Character() {
		try {
			img = new Image(null, "Images/images.png");
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
	
	public void moveRight() {
		pos.getCoords()[0]++;
	}
	
	public void moveLeft() {
		pos.getCoords()[0]--;
	}
	
	public void moveFwd() {
		pos.getCoords()[2]++;
	}
	public void moveBwd() {
		pos.getCoords()[2]--;
	}
		
	public void moveUp() {
		pos.getCoords()[1]++;
	}
	public void moveDown() {
		pos.getCoords()[1]--;
	}
	
}
