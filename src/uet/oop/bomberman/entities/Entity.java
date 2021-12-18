package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.graphics.Camera;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected double x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected double y;

    protected Image img;

    public double getX() {
        return x;
    }

    public int getXUnit() {
        return (int) (x) / (Sprite.SCALED_SIZE);
    }

    public double getY() {
        return y;
    }

    public int getYUnit() {
        return (int) (y) / (Sprite.SCALED_SIZE);
    }

    // id xác định bằng cột + số cột * hàng như trong ma trận
    public int getIdInBoard() {
        return getXUnit() + getYUnit() * Board.getTileWidth();
    }
    public Image get_img() {
        return img;
    }

    public void render(Camera camera) {
        camera.drawImage(get_img(), (int) x, (int) y);
    }

    public abstract void update(double s);

    public abstract boolean collide(Entity e);
}
