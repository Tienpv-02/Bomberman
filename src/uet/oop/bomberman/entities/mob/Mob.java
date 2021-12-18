package uet.oop.bomberman.entities.mob;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Mob extends Entity {
    protected int direction = Direction.CENTER;
    protected boolean moving = false;
    protected boolean alive = true;
    protected boolean dying = false;
    protected Board board;
    protected int animate = 0;

    public boolean isAlive() {
        return alive;
    }

    public Mob(int xUnit, int yUnit, Image img, Board board) {
        x = xUnit * Sprite.SCALED_SIZE;
        y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
        this.board = board;
    }


    @Override
    public abstract boolean collide(Entity e);

    public void killed() {
        dying = true;
        animate = 0;
    }

    //co the di chuyen toi xx unit, yy unit
    public boolean movable(double xx, double yy) {

        double top = yy;
        double bot = yy + Sprite.SCALED_SIZE * 15.0 / 16.0;
        double left = xx;
        double right = xx + Sprite.SCALED_SIZE * 13.0 / 16.0;

        return
                board.getTileAt(left, top).collide(this) &&
                        board.getTileAt(left, bot).collide(this) &&
                        board.getTileAt(right, top).collide(this) &&
                        board.getTileAt(right, bot).collide(this);
    }
    protected void goTo( double xStep, double yStep, double xx, double yy) {
        double top = yy;
        double bot = yy + Sprite.SCALED_SIZE * 15.0 / 16.0;
        double left = xx;
        double right = xx + Sprite.SCALED_SIZE * 13.0 / 16.0;
        double centerX = (left + right) / 2.0;
        double centerY = (top + bot) / 2.0;
        Entity lt = board.getTileAt(left, top);
        Entity lcY =  board.getTileAt(left,centerY);
        Entity lb = board.getTileAt(left, bot);
        Entity rt = board.getTileAt(right, top);
        Entity rcY =   board.getTileAt(right, centerY);
        Entity rb = board.getTileAt(right, bot);
        Entity cXb = board.getTileAt(centerX, bot);
        Entity cXt = board.getTileAt(centerX, top);
        if (xStep < 0) {
            if (lt.collide(this) && lcY.collide(this) && !lb.collide(this)) {
                y += xStep * 0.6 ;
            }
            if (lb.collide(this) && lcY.collide(this) && !lt.collide(this)) {
                y -= xStep * 0.6;
            }
        }
        if (xStep > 0) {
            if (rt.collide(this) && rcY.collide(this) && !rb.collide(this)) {
                y -= xStep * 0.6;
            }
            if (rb.collide(this) && rcY.collide(this) && !rt.collide(this)) {
                y += xStep * 0.6;
            }
        }
        if (yStep > 0) {
            if (rb.collide(this) && cXb.collide(this) && !lb.collide(this)) {
                x += yStep * 0.6;
            }
            if (lb.collide(this) && cXb.collide(this) && !rb.collide(this)) {
                x -= yStep * 0.6;
            }
        }
        if (yStep < 0) {
            if (rb.collide(this) && cXt.collide(this) && !lt.collide(this)) {
                x -= yStep * 0.6;
            }
            if (lt.collide(this) && cXt.collide(this) && !rt.collide(this)) {
                x += yStep * 0.6;
            }
        }

    }
}
