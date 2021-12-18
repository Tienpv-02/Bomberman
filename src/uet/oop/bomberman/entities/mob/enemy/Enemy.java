package uet.oop.bomberman.entities.mob.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.entities.mob.Direction;
import uet.oop.bomberman.entities.mob.Mob;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends Mob {
    Image _dead;
    double timeLeft = 2.0;
    boolean chasingPlayer = false;
    boolean randomDirection = true;
    boolean randomSpeed = false;
    Sprite left1;
    Sprite left2;
    Sprite left3;
    Sprite right1;
    Sprite right2;
    Sprite right3;
    double _steps;
    final double MAX_STEPS;
    final double rest;

    double movementSpeed = 2.5 * Sprite.SCALED_SIZE;
    double changeDirectionTime = 2.0;

    public Enemy(int xUnit, int yUnit, Image img, Image dead, Board board) {
        super(xUnit, yUnit, img, board);
        _dead = dead;
        MAX_STEPS = Sprite.SCALED_SIZE * Board.TILE_WIDTH / movementSpeed;
        rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
        _steps = MAX_STEPS;
    }

    @Override
    public void update(double s) {
        animate += s * 1000;
        if (dying) {
            timeLeft -= s;
            if (timeLeft < 0) alive = false;
        }
        if (!dying) calculateMove(s);
        move(x, y);
        chooseSprite(s);
        Bomber b = board.getPlayer();
        if (!dying)
            if (Math.abs(b.getX() - x) < Sprite.SCALED_SIZE * 12 / 16.0 && Math.abs(b.getY() - y) < Sprite.SCALED_SIZE * 15 / 16.0) {
                b.killed();
            }
        ;

    }

    private void calculateMove(double s) {
        Bomber _player = board.getPlayer();
        Enemy _e = this;
        int xa = 0, ya = 0;
        if (chasingPlayer) {
            {
                if (_player == null)
                    direction = getRandom(0, 3);

                int vertical = getRandom(1, 2);

                if (vertical == 1) {
                    int v;
                    {
                        if (_player.getXUnit() < _e.getXUnit())
                            v = 2;
                        else if (_player.getXUnit() > _e.getXUnit())
                            v = 0;
                        else
                            v = -1;
                    }
                    if (v != -1)
                        direction = v;
                    else {
                        if (_player.getYUnit() < _e.getYUnit())
                            direction = 1;
                        else if (_player.getYUnit() > _e.getYUnit())
                            direction = 3;
                        else
                            direction = -1;
                    }

                } else {
                    int h;

                    if (_player.getYUnit() < _e.getYUnit())
                        h = 1;
                    else if (_player.getYUnit() > _e.getYUnit())
                        h = 3;
                    else
                        h = -1;
                    if (h != -1)
                        direction = h;
                    else {
                        if (_player.getXUnit() < _e.getXUnit())
                            direction = 2;
                        else if (_player.getXUnit() > _e.getXUnit())
                            direction = 0;
                        else
                            direction = -1;
                    }
                }
            }

        } else {
            if (_steps <= 0) {
                direction = getRandom(0, 3);
                _steps = MAX_STEPS;
            }
        }

        if (direction == 0) xa++;
        if (direction == 2) xa--;
        if (direction == 3) ya++;
        if (direction == 1) ya--;


        if (move(x + xa * movementSpeed * s, y + ya * movementSpeed * s)) {
            _steps -= 0.1 + rest;
            setCoordinate(xa * movementSpeed * s, ya * movementSpeed * s);
            moving = true;
        } else {
            _steps = 0;
            moving = false;
        }


    }

    private void setCoordinate(double xStep, double yStep) {

        x += xStep;
        y += yStep;

    }

    public boolean move(double xx, double yy) {
        double xStep = xx - x;
        double yStep = yy - y;
        goTo(xStep, yStep, xx, yy);

        return movable(xx, yy);
    }

    protected void calculateDirection(double s) {
        changeDirectionTime -= s;
        if (changeDirectionTime < 0) {
            direction = getRandom(0, 3);
            changeDirectionTime = getRandom(3.0, 6.0);
        }
    }

    private int getRandom(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    private double getRandom(double min, double max) {
        return (Math.random() * (max - min + 1) + min);
    }

    protected void calculateSpeed(double s) {

    }


    public void chooseSprite(double s) {
        if (animate > 10000) animate = 0;
         if (dying) {
            img = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3,
                animate,1420 ).getFxImage();
            return;
        }
        if (direction != Direction.RIGHT) {
                img = Sprite.movingSprite( left1,
                    left2,left3,left2, animate, 660).getFxImage();
        }
        if (direction != Direction.LEFT) {
            img = Sprite.movingSprite( right1,
                right2,right3,right2, animate, 660).getFxImage();
        }
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber) ((Bomber) e).killed();
        return true;
    }
}
