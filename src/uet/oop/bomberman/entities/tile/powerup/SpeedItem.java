package uet.oop.bomberman.entities.tile.powerup;

import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class SpeedItem extends Item {
    public SpeedItem(int xUnit, int yUnit) {
        super(xUnit, yUnit);
        img = Sprite.powerup_speed.getFxImage();
    }

    @Override
    public void activeItem(Bomber bomber) {
       bomber.x2speed();
    }

    @Override
    public void update(double s) {
    }

}
