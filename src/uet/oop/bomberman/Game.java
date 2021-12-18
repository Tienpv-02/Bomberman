package uet.oop.bomberman;

import javafx.scene.canvas.Canvas;
import uet.oop.bomberman.graphics.Camera;
import uet.oop.bomberman.utilities.Input;
import uet.oop.bomberman.utilities.GameLoopTimer;

public class Game extends Canvas {
    /**
     * The default size of the window
     * H: 480px W: 800px
     */
    public static final int CAMERA_WIDTH = 25, CAMERA_HEIGHT = 15;
    public static final double PLAYER_SPEED = 1.0;
    Board _board;
    Camera _camera;
    Input _input = Input.getInstance();
    public static GameState gameState = GameState.GAME_MENU;

    public int getLevel() {
        return _board.level;
    }
    public Game(double width, double height) {
        super(width, height);
        _camera = new Camera((int) width, (int) height, this.getGraphicsContext2D());
        _board = new Board(this);
    }

    public void start() {
        GameLoopTimer timer = new GameLoopTimer() {

            @Override
            public void tick(double secondsSinceLastFrame) {
                //_board.render(_camera);
                //_board.update(secondsSinceLastFrame);
                update(secondsSinceLastFrame);
                render();
            }
        };
        timer.start();

    }

    private void render() {
        _board.render(_camera);
    }

    private void update(double secondsSinceLastFrame) {
        _board.update(secondsSinceLastFrame);
    }

}
