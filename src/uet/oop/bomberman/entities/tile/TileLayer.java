package uet.oop.bomberman.entities.tile;

import java.util.Collections;
import java.util.Stack;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Camera;

public class TileLayer extends Tile {
    Stack<Tile> entities = new Stack<>();

    public TileLayer(int xUnit, int yUnit, Tile... entities) {
        super(xUnit, yUnit);
        this.entities.add(new Grass(xUnit, yUnit));
        Collections.addAll(this.entities, entities);
    }


    @Override
    public void update(double s) {
        Tile t = entities.peek();
        if (t.isDestroyed()) entities.pop();
        t.update(s);
    }

    @Override
    public void render(Camera camera) {
        entities.peek().render(camera);
    }

    @Override
    public Image get_img() {
        return entities.peek().get_img();
    }

    @Override
    public boolean collide(Entity e) {
        return entities.peek().collide(e);
    }

    @Override
    public void destroy() {
        entities.peek().destroy();
    }
}
