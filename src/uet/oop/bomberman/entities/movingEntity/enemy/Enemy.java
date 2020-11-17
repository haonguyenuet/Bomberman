package uet.oop.bomberman.entities.movingEntity.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.movingEntity.MovingEntity;

import java.util.Random;

public abstract class Enemy extends MovingEntity {
	protected Random random = new Random();
	protected int direction = -1;
	protected int timeCountDown;

	public Enemy( int x , int y , Image img ) {
		super(x , y , img);
	}

	protected int randomDirection(){
		return random.nextInt(4);
	}
}
