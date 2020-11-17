package uet.oop.bomberman.entities;

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public abstract class Entity  {
	//Tọa độ X tính từ góc trái trên trong Canvas
	protected int x;

	//Tọa độ Y tính từ góc trái trên trong Canvas
	protected int y;

	protected Image img;
	protected boolean isRemoved = false;

	//Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
	public Entity( int xUnit , int yUnit , Image img ) {
		this.x = xUnit * Sprite.SCALED_SIZE;
		this.y = yUnit * Sprite.SCALED_SIZE;
		this.img = img;
	}

	protected Entity() {

	}
    public boolean compareCoordinate(int x, int y){
		return this.x == x && this.y == y;
    }
	public void render( GraphicsContext gc ) {
		gc.drawImage(img , x , y);
	}

	public abstract void update();

	public void remove() {
		isRemoved = true;
	}

	public boolean isRemoved() {
		return isRemoved;
	}

	public void setX( int x ) {
		this.x = x;
	}

	public void setY( int y ) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setImg( Image img ) {
		this.img = img;
	}
}