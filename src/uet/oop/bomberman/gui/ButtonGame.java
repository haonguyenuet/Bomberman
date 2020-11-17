package uet.oop.bomberman.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class ButtonGame extends Button {
	public ButtonGame( String text ) {
		setText(text);
		setDisable(true);
		setPrefHeight(40);
		setPrefWidth(BombermanGame.WIDTH * Sprite.SCALED_SIZE);
		setAlignment(Pos.CENTER);
		setStyle("-fx-font: 24 arial;" +
				" -fx-background-color: #50a000;" +
				" -fx-text-fill: #ffbe76;");
	}
}
