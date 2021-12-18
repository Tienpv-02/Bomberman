package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Camera;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Tile {
    public static boolean passable = false;
    public static boolean passed = false;

    public Portal(int xUnit, int yUnit) {
        super(xUnit, yUnit);
        img = Sprite.portal.getFxImage();
    }


    @Override
    public void render(Camera camera) {
        camera.drawImage(Sprite.grass.getFxImage(), (int) x, (int) y);
        super.render(camera);
    }

    @Override
    public void update(double s) {
    }

    @Override
    public boolean collide(Entity e) {
        if (passable) passed = true;
        return true;
    }
}
