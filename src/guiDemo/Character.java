package guiDemo;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

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
				cellWidth * pos.x, cellHeight * pos.y, cellWidth, cellHeight);
	}
	
	public void moveRight() {
		pos.x++;
	}
	
	public void moveLeft() {
		pos.x--;
	}
	
	public void moveFwd() {
		pos.z++;
	}
	public void moveBwd() {
		pos.z--;
	}
		
	public void moveUp() {
		pos.y++;
	}
	public void moveDown() {
		pos.y--;
	}
	
}
