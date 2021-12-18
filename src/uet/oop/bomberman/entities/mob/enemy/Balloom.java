package uet.oop.bomberman.entities.mob.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.Direction;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends Enemy {
    public Balloom(int xUnit, int yUnit, Board board) {
        super(xUnit, yUnit, Sprite.balloom_left1.getFxImage(), Sprite.balloom_dead.getFxImage(), board);
        direction = Direction.CENTER;
        left1 = Sprite.balloom_left1;
        left2 = Sprite.balloom_left2;
        left3 = Sprite.balloom_left3;
        right1 = Sprite.balloom_right1;
        right2 = Sprite.balloom_right2;
        right3 = Sprite.balloom_right3;

    }

    @Override
    public void update(double s) {
        super.update(s);
    }


    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
