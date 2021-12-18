package uet.oop.bomberman.entities.tile.powerup;

import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class FlameItem extends Item {
    public FlameItem(int xUnit, int yUnit) {
        super(xUnit, yUnit);
        img = Sprite.powerup_flames.getFxImage();
    }

    @Override
    public void activeItem(Bomber e) {
        Bomber.blastRadius++;
    }

    @Override
    public void update(double s) {

    }

}
