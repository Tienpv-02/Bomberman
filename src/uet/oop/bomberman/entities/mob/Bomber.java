package uet.oop.bomberman.entities.mob;

import javafx.scene.input.KeyCode;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Camera;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Input;

public class Bomber extends Mob {
    Input _input;
    Bomb currentBomb = null;
    public static int trialLeft = 2;
    public int bombNumber = 1;
    public static int blastRadius = 1;
    public double speed = 1.0;
    double timeLeft = 1.0;

    public Bomber(int x, int y, Board board) {
        super(x, y, Sprite.player_right.getFxImage(), board);
        _input = this.board.get_input();
    }

    public void clearItem() {
        resetBombNumber();
        resetBlastRadius();
        resetSpeed();
    }

    public void increaseBombNumber() {
        bombNumber++;
    }

    public void resetBombNumber() {
        bombNumber = 1;
    }
    public void resetSpeed() {
        speed = 1;
    }
    public void resetBlastRadius() {
        blastRadius = 1;
    }
    public int getBlastRadius() {
        return blastRadius;
    }

    public void x2speed() {
        speed *= 2;
    }

    public void setBlastRadius(int radius) {
        Bomber.blastRadius = radius;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }


    @Override
    public void update(double s) {
        animate += s * 1000;
        calculateMove(s);
        chooseSprite(s);
        calculateCameraOffset();
        placeBomb();
        if (currentBomb != null)
            if (passThroughCurrentBomb())
                currentBomb.pass();
        if (dying) {
            timeLeft -= s;
            if (timeLeft < 0) alive = false;
        }
    }

    private boolean passThroughCurrentBomb() {
        double top = y;
        double bot = y + Sprite.SCALED_SIZE * 15 / 16.0;
        double left = x;
        double right = x + Sprite.SCALED_SIZE * 13.0 / 16.0;

        return
                board.getTileAt(left, top) != currentBomb &&
                        board.getTileAt(left, bot) != currentBomb &&
                        board.getTileAt(right, top) != currentBomb &&
                        board.getTileAt(right, bot) != currentBomb;
    }

    private void placeBomb() {
        if (_input.isDown(KeyCode.SPACE) && bombNumber > 0 && !dying) {
            // thay the cho convert ....
            double deltaX = x + Sprite.SCALED_SIZE / 2.0 * 14.0 / 16.0;
            int xt = (int) (deltaX) / (Sprite.SCALED_SIZE);
            double deltaY = y + Sprite.SCALED_SIZE / 2.0 * 14.0 / 16.0;
            int yt = (int) (deltaY) / (Sprite.SCALED_SIZE);
            {
                currentBomb = new Bomb(xt, yt, this.board);
                if (!board.containsBomb(currentBomb)) {
                    board.addBomb(currentBomb);
                    bombNumber--;
                }
            }
        }
    }

    private void chooseSprite(double s) {
        if (animate > 10000) animate = 0;
        if (dying) {
            img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3,
                animate,520 ).getFxImage();
            return;
        }
        switch (direction) {
            case Direction.RIGHT: {
                if (moving) {
                    img = Sprite.movingSprite( Sprite.player_right_1,
                        Sprite.player_right_2, animate, 660).getFxImage();
                } else img = Sprite.player_right.getFxImage();
                break;
            }
            case Direction.UP : {
                if (moving) {
                    img = Sprite.movingSprite( Sprite.player_up_1,
                        Sprite.player_up_2, animate, 660).getFxImage();
                } else
                    img = Sprite.player_up.getFxImage();
                break;
            }
            case Direction.LEFT: {
                if (moving) {
                    img = Sprite.movingSprite( Sprite.player_left_1,
                        Sprite.player_left_2, animate, 660).getFxImage();
                } else
                    img = Sprite.player_left.getFxImage();
                break;
            }
            case Direction.DOWN: {
                if (moving) {
                    img = Sprite.movingSprite( Sprite.player_down_1,
                        Sprite.player_down_2, animate, 660).getFxImage();
                } else
                    img = Sprite.player_left.getFxImage();
                break;
            }
        }

    }

    @Override
    public boolean collide(Entity e) {
        return true;
    }

    private void calculateCameraOffset() {
        double xOffset = Camera.getXOffset();
        if (x - Camera.getWIDTH() / 2.0 > 0 && x + Camera.getWIDTH() / 2.0 < Board.PIXEL_WIDTH)
            xOffset = x - Camera.getWIDTH() / 2.0;
        double yOffset = Camera.getYOffset();
        if (y - Camera.getHEIGHT() / 2.0 > 0 && y + Camera.getHEIGHT() / 2.0 < Board.PIXEL_HEIGHT)
            yOffset = y - Camera.getHEIGHT() / 2.0;
        Camera.setOffset(xOffset, yOffset);
    }

    private void calculateMove(double s) {
        //bomcheck
        movable(x, y);
        double movementSpeed = (speed + 2.5) * Sprite.SCALED_SIZE;
        if (dying) {
            return;
        }
        if (_input.isDown(KeyCode.DOWN)) {
            move(0, s * movementSpeed);
        } else if (_input.isDown(KeyCode.UP)) {
            move(0, -s * movementSpeed);
        } else if (_input.isDown(KeyCode.LEFT)) {
            move(-s * movementSpeed, 0);
        } else if (_input.isDown(KeyCode.RIGHT)) {
            move(s * movementSpeed, 0);
        } else moving = false;
    }

    private void move(double xStep, double yStep) {
        moving = true;
        if (xStep > 0) direction = Direction.RIGHT; //right
        if (xStep < 0) direction = Direction.LEFT; //left
        if (yStep > 0) direction = Direction.UP; //top
        if (yStep < 0) direction = Direction.DOWN; //bot
        double xx = x + xStep;
        double yy = y + yStep;
        goTo(xStep, yStep, xx, yy);
        if (movable(xx, yy)) {
            x = xx;
            y = yy;
        }
    }

}
