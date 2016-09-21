package guiDemo;

import java.awt.Graphics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import com.sun.xml.internal.bind.v2.TODO;


public class Character {
	private Position pos;
	private Image img;
	
	public Character() {
		try {
			img = new Image(null, "/Java-Project-MVP/Images/images.png");
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
	//TODO check with haviv if we`re going to use cube or something else to draw our maze
	public void draw(int cellWidth, int cellHeight, GC gc) {
		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 
				cellWidth * pos.x, cellHeight * pos.y, cellWidth, cellHeight);
	}
	
	public void moveRight() {
		pos.x++;
	}
	
	/*
	 * ???????????????????????????????
	 * public void moveLeft() {
		pos.x--;
	}
	
		public void moveUp() {
		pos.y++;
	}
		public void moveDown() {
		pos.y--;
	}
		
		public void moveFwd() {
		pos.z++;
	}
		public void moveBwd() {
		pos.z--;
	}
	*/
}
