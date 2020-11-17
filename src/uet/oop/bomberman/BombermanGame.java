package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.bom.Bom;
import uet.oop.bomberman.entities.movingEntity.enemy.Balloon;
import uet.oop.bomberman.entities.movingEntity.Bomber;
import uet.oop.bomberman.entities.movingEntity.enemy.Oneal;
import uet.oop.bomberman.entities.movingEntity.enemy.Enemy;
import uet.oop.bomberman.entities.staticEntity.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.gui.ButtonGame;
import uet.oop.bomberman.gui.LabelGame;
import uet.oop.bomberman.input.Direction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

	public static int WIDTH = 31;
	public static int HEIGHT = 13;

	private GraphicsContext gc;
	private Canvas canvas;

	private LabelGame caption;
	private ButtonGame nextLevel;
	private ButtonGame playAgain;

	private static List<Entity> entities = new ArrayList<>();
	private static List<Entity> fixedEntities = new ArrayList<>();
	private static List<Entity> explosions = new ArrayList<>();
	
	private Bomber bomberman;
	private static Bom bomb;

	private Portal portal;

	private int level = 1;

	public static void main( String[] args ) {
		Application.launch(BombermanGame.class);
	}

	@Override
	public void start( Stage stage ) throws IOException {
		// Tao root container
		VBox root = new VBox();
		// Tao Canvas
		canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH , Sprite.SCALED_SIZE * HEIGHT);
		canvas.setLayoutX(0);
		canvas.setLayoutY(Sprite.SCALED_SIZE * 2);
		gc = canvas.getGraphicsContext2D();

		canvas.requestFocus();
		canvas.setFocusTraversable(true);
		canvas.setOnKeyPressed(( KeyEvent event ) -> {
			if (event.getCode() == KeyCode.RIGHT) {
				bomberman.setDirection(Direction.RIGHT);
			}
			if (event.getCode() == KeyCode.LEFT) {
				bomberman.setDirection(Direction.LEFT);
			}
			if (event.getCode() == KeyCode.UP) {
				bomberman.setDirection(Direction.UP);
			}
			if (event.getCode() == KeyCode.DOWN) {
				bomberman.setDirection(Direction.DOWN);
			}
			if (event.getCode() == KeyCode.SPACE && bomb == null) { //  && bomberman.isAlive()
				bomb = new Bom(bomberman.getX() / Sprite.SCALED_SIZE ,
						bomberman.getY() / Sprite.SCALED_SIZE ,
						Sprite.bomb.getFxImage());

			}
		});

		// Tao caption
		caption = new LabelGame();
		// Tao button
		nextLevel = new ButtonGame("Next Level");
		playAgain = new ButtonGame("Play Again");
		playAgain.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent event ) {
				try {
					createMap();
					playAgain.setDisable(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		nextLevel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent event ) {
				try {
					level++;
					createMap();
					nextLevel.setDisable(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		root.getChildren().addAll(caption , canvas , nextLevel , playAgain);
		// Tao scene
		Scene scene = new Scene(root);

		// Them scene vao stage
		stage.setScene(scene);
		stage.show();


		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle( long l ) {
				render();
				update();
			}
		};
		timer.start();
		createMap();
	}

	public void createMap() throws IOException {
		// reset
		fixedEntities.clear();
		entities.clear();
		canvas.setDisable(false);
		// read file
		FileReader fileReader = new FileReader("res/levels/Level" + level + ".txt");
		BufferedReader buf = new BufferedReader(fileReader);
		int rowCount = 0;
		String line;
		while ((line = buf.readLine()) != null) {
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == '#') {
					fixedEntities.add(new Wall(i , rowCount , Sprite.wall.getFxImage()));
				} else {
					fixedEntities.add(new Grass(i , rowCount , Sprite.grass.getFxImage()));
				}
			}
			for (int i = 0; i < line.length(); i++) {
				switch (line.charAt(i)) {
					case '*':
						entities.add(new Brick(i , rowCount , Sprite.brick.getFxImage()));
						break;
					case 'x':
						portal = new Portal(i , rowCount , Sprite.portal.getFxImage());
						entities.add(new Brick(i , rowCount , Sprite.brick.getFxImage()));
						break;
					case 'p':
						bomberman = new Bomber(i , rowCount , Sprite.player_right.getFxImage());
						entities.add(bomberman);
						break;
					case '1':
						entities.add(new Balloon(i , rowCount , Sprite.balloom_left1.getFxImage()));
						break;
					case '2':
						entities.add(new Oneal(i , rowCount , Sprite.oneal_left1.getFxImage()));
						break;
				}
			}
			rowCount++;
		}
		caption.setText("Level " + level);
	}

	public void update() {
		if (!bomberman.isAlive()) {
			caption.setText("Game Over");
			playAgain.setDisable(false);
			canvas.setDisable(true);
		}
		if (isWinGame()) {
			caption.setText("Win !");
			nextLevel.setDisable(false);
			canvas.setDisable(true);
		}
		// update entities
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			entity.update();
			if (entity.isRemoved()) {
				entities.remove(i);
			}
		}
		// update bom
		if (bomb != null) {
			bomb.update();
		}
		if (!explosions.isEmpty()) {
			for (int i = 0; i < explosions.size(); i++) {
				Entity entity = explosions.get(i);
				entity.update();
				if (entity.isRemoved()) {
					explosions.remove(i);
				}
			}
		}
	}

	public void render() {
		gc.clearRect(0 , 0 , canvas.getWidth() , canvas.getHeight());
		fixedEntities.forEach(g -> g.render(gc));
		portal.render(gc);
		entities.forEach(g -> g.render(gc));
		if (bomb != null) {
			bomb.render(gc);
		}
		if (!explosions.isEmpty()) {
			explosions.forEach(g -> g.render(gc));
		}
	}

	public static Entity getEntity( int x , int y ) {
		for (Entity e : entities) {
			if (e.compareCoordinate(x , y)) return e;
		}
		if (bomb != null) {
			if (bomb.compareCoordinate(x , y)) return bomb;
		}
		for (Entity e : fixedEntities) {
			if (e.compareCoordinate(x , y)) return e;
		}
		return null;
	}

	public static Entity getEnemy( int x , int y ) {
		for (Entity e : entities) {
			if (e.compareCoordinate(x , y) && !(e instanceof Bomber)) return e;
		}
		return null;
	}

	public static void bombExplode( List<Entity> exs ) {
		bomb = null;
		explosions = exs;
	}

	private boolean isWinGame() {
		// extirpated full enemy && found portal
		for (Entity entity : entities) {
			if (entity instanceof Enemy) {
				return false;
			}
		}
		return (bomberman.getX() == portal.getX() && bomberman.getY() == portal.getY());
	}

}
