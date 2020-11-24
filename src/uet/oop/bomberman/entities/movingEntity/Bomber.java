package uet.oop.bomberman.entities.movingEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Direction;
import uet.oop.bomberman.sounds.SoundEffect;

public class Bomber extends MovingEntity {
	private Direction direction = Direction.NONE;

	public Bomber( int x , int y , Image img ) {
		super(x , y , img);
	}

	@Override
	public void update() {
		if (direction != Direction.NONE) {
			handleMove();
		}
		checkAlive();
	}

	private void handleMove() {
		if (direction == Direction.RIGHT) {
			if (isCanMove(x + speed , y)) {
				x += speed;
				img = Sprite.player_right.getFxImage();
			}
		} else if (direction == Direction.LEFT) {
			if (isCanMove(x - speed , y)) {
				x -= speed;
				img = Sprite.player_left.getFxImage();
			}
		} else if (direction == Direction.UP) {
			if (isCanMove(x , y - speed)) {
				y -= speed;
				img = Sprite.player_up.getFxImage();
			}
		} else if (direction == Direction.DOWN) {
			if (isCanMove(x , y + speed)) {
				y += speed;
				img = Sprite.player_down.getFxImage();
			}
		}
		direction = Direction.NONE;
	}


	private boolean isCanMove( int a , int b ) {
		Entity entity = BombermanGame.getEntity(a , b);
		if (entity == null) return true;
		return this.collide(entity);
	}

	private void checkAlive() {
		Entity enemy = BombermanGame.getEnemy(x , y);
		if (enemy != null) {
			remove();
		}
	}

	@Override
	public void remove() {
		super.remove();
		SoundEffect.play("res/sound/5.wav");
	}

	public void setDirection( Direction direction ) {
		this.direction = direction;
	}
}
