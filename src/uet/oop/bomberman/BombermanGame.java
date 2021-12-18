package uet.oop.bomberman;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Input;

public class BombermanGame extends Application {
    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;
    private Game game;
    private static boolean running = true;
    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }
    @Override
    public void start(Stage stage) {
        // Tao Canvas
        game = new Game(Sprite.SCALED_SIZE * Game.CAMERA_WIDTH,
            Sprite.SCALED_SIZE * (Game.CAMERA_HEIGHT + 1));
        // Tao root container
        Group root = new Group();
        root.getChildren().add(game);
        //Menu.createMenu(root, game);
        // Tao scene
        Scene scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        // khoi tao input
        Input.getInstance().pollScene(scene);
        stage.setTitle("Bomberman");
        stage.getIcons().add(new Image("demo.png"));
        stage.show();
        game.start();

    }

}
