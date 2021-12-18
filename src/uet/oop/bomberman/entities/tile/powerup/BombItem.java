package uet.oop.bomberman.entities.tile.powerup;

import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class BombItem extends Item {
    public BombItem(int xUnit, int yUnit) {
        super(xUnit, yUnit);
        img = Sprite.powerup_bombs.getFxImage();
    }

    @Override
    public void activeItem(Bomber e) {
        e.increaseBombNumber();
    }

    @Override
    public void update(double s) {
    }


}
