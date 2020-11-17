package uet.oop.bomberman.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.WIDTH;

public class LabelGame extends Label {
	public LabelGame(){
		setPrefWidth(Sprite.SCALED_SIZE * WIDTH);
		setPrefHeight(Sprite.SCALED_SIZE * 2);
		setAlignment(Pos.CENTER);
		setStyle("-fx-font: 30 arial;" +
				" -fx-font-weight: bold;" +
				" -fx-background-color: #96C781;" +
				" -fx-text-fill: green;");
	}
}
