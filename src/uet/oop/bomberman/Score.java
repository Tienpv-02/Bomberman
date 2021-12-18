package uet.oop.bomberman;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Score {
  Game _game;
  int _score = 0;
  public static double timeLeft = 200;

  public Score(Game _game) {
    this._game = _game;
  }

  public void render() {
    _game.getGraphicsContext2D().setFill(Color.BLACK);
    _game.getGraphicsContext2D().fillRect(0, 0, Board.PIXEL_WIDTH, 1 * Sprite.SCALED_SIZE);
    _game.getGraphicsContext2D().setFill(Color.rgb(255, 255, 255));
    _game.getGraphicsContext2D().setFont(Font.font("Arial", FontWeight.BOLD, 14));
    _game.getGraphicsContext2D().fillText("TIME:", Sprite.SCALED_SIZE, 0.6 * Sprite.SCALED_SIZE);
    _game.getGraphicsContext2D().fillText(String.valueOf((int) timeLeft), 4 * Sprite.SCALED_SIZE, 0.6 * Sprite.SCALED_SIZE);
    _game.getGraphicsContext2D().fillText("LEFT:", 8 * Sprite.SCALED_SIZE, 0.6 * Sprite.SCALED_SIZE);
    _game.getGraphicsContext2D().fillText(String.valueOf(Bomber.trialLeft), 10 * Sprite.SCALED_SIZE, 0.6 * Sprite.SCALED_SIZE);
  }

  public void update(double secondsSinceLastFrame) {
    timeLeft -= secondsSinceLastFrame;
  }
}