package uet.oop.bomberman.entities.tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Camera;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Tile {
    double timeLeft = 0.5;
    boolean destroying = false;
    int _animate = 0;

    public Brick(int xUnit, int yUnit) {
        super(xUnit, yUnit);
        img = Sprite.brick.getFxImage();
    }

    @Override
    public void update(double s) {
        if (destroying) {
            timeLeft -= s;
            int m = 0;// sprite order
            _animate += s * 1000;
            if (_animate > 10000) _animate = 0;
            if (_animate % 500 < 500 / 3) m = 0;
            else if (_animate % 500 < 500 * 2 / 3) m = 1;
            else if (_animate % 500 < 500 * 3 / 3) m = 2;
            //else if (_animate % 500 < 500) m = 3;
            switch (m) {
                case 0: {
                    img = Sprite.brick_exploded.getFxImage();
                    break;
                }
                case 1: {
                    img = Sprite.brick_exploded1.getFxImage();
                    break;
                }
                case 2: {
                    img = Sprite.brick_exploded2.getFxImage();
                    break;
                }
            }
        }
        if (timeLeft < 0) _destroyed = true;
    }

    @Override
    public void render(Camera camera) {
        if (destroying) camera.drawImage(Sprite.grass.getFxImage(), (int) x, (int) y);
        super.render(camera);
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }

    @Override
    public void destroy() {
        destroying = true;
    }
}
