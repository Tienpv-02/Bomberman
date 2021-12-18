package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Camera {
    GraphicsContext gc;
    private static int WIDTH, HEIGHT;
    private static int xOffset, yOffset;

    public static void setOffset(double xOffset, double yOffset) {
        Camera.xOffset = (int) xOffset;
        Camera.yOffset = (int) yOffset;
    }

    public Camera(int width, int height, GraphicsContext gc) {
        xOffset = 0;
        yOffset = 0;//-1 * Sprite.SCALED_SIZE;
        WIDTH = width;
        HEIGHT = height;
        this.gc = gc;
    }

    public static double getXOffset() {
        return xOffset;
    }

    public static double getYOffset() {
        return yOffset;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static void reset() {
        xOffset = 0;
        yOffset = 0;//-1 * Sprite.SCALED_SIZE;
    }

    public void drawImage(Image img, int x, int y) {
        gc.drawImage(img, x - xOffset, y - yOffset);
    }

    public void prepare() {
        gc.clearRect(0, 0, WIDTH, HEIGHT + 1 * Sprite.SCALED_SIZE);
    }


}
