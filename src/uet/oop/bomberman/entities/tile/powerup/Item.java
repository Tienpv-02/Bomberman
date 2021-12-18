package uet.oop.bomberman.entities.tile.powerup;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.entities.tile.Tile;

public abstract class Item extends Tile {
    public Item(int xUnit, int yUnit) {
        super(xUnit, yUnit);
    }

    public abstract void activeItem(Bomber e);

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber && !_destroyed) {
            activeItem((Bomber) e);
            destroy();
        }
        return true;
    }

    @Override
    public void destroy() {
        _destroyed = true;
    }
}
