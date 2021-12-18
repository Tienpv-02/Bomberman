package uet.oop.bomberman.graphics;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;

public class Menu {
  public static Text level, time;
  public static int timeLeft = 200;
  public Game g;
  int score = 0;

  public static void createMenu(Group root, Game g) {
    level = new Text("Level: 1");
    level.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    level.setFill(Color.WHITE);
    level.setX(416);
    level.setY(20);
    time = new Text("Times: 200");
    time.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    time.setFill(Color.WHITE);
    time.setX(608);
    time.setY(20);

    Pane pane = new Pane();
    pane.getChildren().addAll(level, time);
    pane.setMinSize(800, 32);
    pane.setMaxSize(800, 480);
    pane.setStyle("-fx-background-color: #353535");

    root.getChildren().add(pane);
  }
}

