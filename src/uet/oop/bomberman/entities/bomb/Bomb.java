package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.entities.mob.Direction;
import uet.oop.bomberman.entities.tile.TileLayer;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.HashMap;
import java.util.Map;
import uet.oop.bomberman.utilities.Sound;

public class Bomb extends Entity {
    int animate = 0;
    double timeLeft = 1.5;
    Board board;
    private boolean exploded = false;
    int radius = 1;
    Map<Integer, Flame> flames = new HashMap<>();
    boolean passed = false;

    public Bomb(int xUnit, int yUnit, Board board) {
        x = xUnit * Sprite.SCALED_SIZE;
        y = yUnit * Sprite.SCALED_SIZE;
        img = Sprite.bomb.getFxImage();
        this.board = board;
        radius = board.getPlayer().getBlastRadius() + 1;
    }

    @Override
    public void update(double s) {
        animate(s);
        timeLeft -= s;
        if (timeLeft < 0)
            explode();
    }

    public void forceExplode() {
        exploded = true;
    }

    public void createDirectFlame(int xUnit, int yUnit, int direction) {
        int xTemp = xUnit;
        int yTemp = yUnit;
        for (int i = 1; i < radius; i++) {
            xTemp += Direction.getXDirection(direction);
            yTemp += Direction.getYDirection(direction);
            Entity e = board.getTileAt(xTemp,yTemp);
            if (e instanceof TileLayer) {
                ((TileLayer) e).destroy();
                if (!e.collide(this)) break;
            }
            Bomb b = board.getBomb(xTemp, yTemp);
            if (b != null) break;
            if (board.getTileAt(xTemp, yTemp) instanceof Wall) break;
            addFlame(xTemp, yTemp, direction, (i == radius - 1));
        }

    }

    private void explode() {
        exploded = true;
        int xt = getXUnit();
        int yt = getYUnit();
        // create flame
        if (exploded) {
            new Sound("sound/bomb_explosion.wav", "explosion");
            addFlame(xt, yt, Direction.CENTER, true);
            createDirectFlame(xt, yt, Direction.UP);
            createDirectFlame(xt, yt, Direction.RIGHT);
            createDirectFlame(xt, yt, Direction.DOWN);
            createDirectFlame(xt, yt, Direction.LEFT);
            board.addFlame(flames);
            Bomber b = board.getPlayer();
            b.increaseBombNumber();
        }
    }

    private void addFlame(int xUnit, int yUnit, int direction, boolean last) {
        int id = xUnit + yUnit * Board.getTileWidth();
        if (id < 0 || id >= Board.getTileWidth() * Board.getTileHeight()) return;
        Flame f = new Flame(xUnit, yUnit, direction, last, this.board);
        flames.put(f.getIdInBoard(), f);
    }

    public boolean isExploded() {
        return exploded;
    }

    private void animate(double s) {
        int m;
        animate += s * 1000;
        if (animate > 10000) animate = 0;
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1,
            Sprite.bomb_2, Sprite.bomb_1,animate, 800).getFxImage();
    }

    public void pass() {
        passed = true;
    }

    public boolean isPassed() {
        return passed;
    }

    @Override
    public boolean collide(Entity e) {
        return e instanceof Bomber && !passed;
    }
}
