package uet.oop.bomberman.entities.mob;

public class Direction {
    public static final int INVALID = -1;
    public static final int RIGHT = 0;
    public static final int UP = 1;
    public static final int LEFT = 2;
    public static final int DOWN = 3;
    public static final int CENTER = 4;
    public static final int[] x = {1, 0, -1, 0};
    public static final int[] y = {0, -1, 0, 1};
    public static int getXDirection(int direct) {
        return x[direct];
    }
    public static int getYDirection(int direct) {
        return y[direct];
    }
}
