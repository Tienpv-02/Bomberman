package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.Direction;
import uet.oop.bomberman.entities.mob.Mob;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Entity {
    private boolean stillLasted;
    private int animate = 0;
    private double timeLeft = 0.5; // thời gian tồn tại
    Board board;
    private final int direction;

    /**
     * @param xUnit
     * @param yUnit
     * @param direction 0 - right,
     *                  1 - up,
     *                  2 - left,
     *                  3 - down,
     *                  4 - center,
     */
    public Flame(int xUnit, int yUnit, int direction, boolean stillLasted, Board board) {
        x = xUnit * Sprite.SCALED_SIZE;
        y = yUnit * Sprite.SCALED_SIZE;
        this.board = board;
        stillLasted = stillLasted;
        this.direction = direction;
        if (stillLasted)
            switch (this.direction) {
                case Direction.RIGHT: {
                    this.img = Sprite.explosion_horizontal_right_last.getFxImage();
                    break;
                }
                case Direction.UP: {
                    this.img = Sprite.explosion_vertical_top_last.getFxImage();
                    break;
                }
                case Direction.LEFT: {
                    this.img = Sprite.explosion_horizontal_left_last.getFxImage();
                    break;
                }
                case Direction.DOWN: {
                    this.img = Sprite.explosion_vertical_down_last.getFxImage();
                    break;
                }
                case Direction.CENTER: {
                    this.img = Sprite.bomb_exploded.getFxImage();
                    break;
                }

            }
        if (!stillLasted)
            switch (this.direction) {
                case Direction.CENTER: {
                    this.img = Sprite.bomb_exploded.getFxImage();
                    break;
                }
                case Direction.RIGHT:
                case Direction.LEFT: {
                    this.img = Sprite.explosion_horizontal.getFxImage();
                    break;
                }
                case Direction.UP:
                case Direction.DOWN: {
                    this.img = Sprite.explosion_vertical.getFxImage();
                    break;
                }
            }
    }

    @Override
    public void update(double s) {
        animate(s);
        timeLeft -= s;

    }

    public double getTimeLeft() {
        return timeLeft;
    }

    private void animate(double s) {
        int m = 0;// sprite order
        animate += s * 1000;
        if (animate > 10000) animate = 0;
        if (animate % 500 < 500 / 4) m = 0;
        else if (animate % 500 < 500 * 2 / 4) m = 1;
        else if (animate % 500 < 500 * 3 / 4) m = 2;
        else
            m = 3;
        if (m == 0) {
            if (stillLasted)
                switch (direction) {
                    case 0: {
                        this.img = Sprite.explosion_horizontal_right_last.getFxImage();
                        break;
                    }
                    case 1: {
                        this.img = Sprite.explosion_vertical_top_last.getFxImage();
                        break;
                    }
                    case 2: {
                        this.img = Sprite.explosion_horizontal_left_last.getFxImage();
                        break;
                    }
                    case 3: {
                        this.img = Sprite.explosion_vertical_down_last.getFxImage();
                        break;
                    }
                    case 4: {
                        this.img = Sprite.bomb_exploded.getFxImage();
                        break;
                    }

                }
            else {
                switch (direction) {
                    case 0:
                    case 2: {
                        this.img = Sprite.explosion_horizontal.getFxImage();
                        break;
                    }
                    case 1:
                    case 3: {
                        this.img = Sprite.explosion_vertical.getFxImage();
                        break;
                    }
                    case 4: {
                        this.img = Sprite.bomb_exploded.getFxImage();
                        break;
                    }

                }
            }
        }
        if (m == 1 || m == 3) {
            if (stillLasted)
                switch (direction) {
                    case 0: {
                        this.img = Sprite.explosion_horizontal_right_last1.getFxImage();
                        break;
                    }
                    case 1: {
                        this.img = Sprite.explosion_vertical_top_last1.getFxImage();
                        break;
                    }
                    case 2: {
                        this.img = Sprite.explosion_horizontal_left_last1.getFxImage();
                        break;
                    }
                    case 3: {
                        this.img = Sprite.explosion_vertical_down_last1.getFxImage();
                        break;
                    }
                    case 4: {
                        this.img = Sprite.bomb_exploded1.getFxImage();
                        break;
                    }

                }
            if (!stillLasted)
                switch (direction) {
                    case 0:
                    case 2: {
                        this.img = Sprite.explosion_horizontal1.getFxImage();
                        break;
                    }
                    case 1:
                    case 3: {
                        this.img = Sprite.explosion_vertical1.getFxImage();
                        break;
                    }
                    case 4: {
                        this.img = Sprite.bomb_exploded1.getFxImage();
                        break;
                    }

                }
        }
        if (m == 2) {
            if (stillLasted)
                switch (direction) {
                    case 0: {
                        this.img = Sprite.explosion_horizontal_right_last2.getFxImage();
                        break;
                    }
                    case 1: {
                        this.img = Sprite.explosion_vertical_top_last2.getFxImage();
                        break;
                    }
                    case 2: {
                        this.img = Sprite.explosion_horizontal_left_last2.getFxImage();
                        break;
                    }
                    case 3: {
                        this.img = Sprite.explosion_vertical_down_last2.getFxImage();
                        break;
                    }
                    case 4: {
                        this.img = Sprite.bomb_exploded2.getFxImage();
                        break;
                    }

                }
            if (!stillLasted)
                switch (direction) {
                    case 0:
                    case 2: {
                        this.img = Sprite.explosion_horizontal2.getFxImage();
                        break;
                    }
                    case 1:
                    case 3: {
                        this.img = Sprite.explosion_vertical2.getFxImage();
                        break;
                    }
                    case 4: {
                        this.img = Sprite.bomb_exploded2.getFxImage();
                        break;
                    }

                }
        }

    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Mob) ((Mob) e).killed();
        return true;
    }
}
