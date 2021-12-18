package uet.oop.bomberman;

//import static uet.oop.bomberman.utilities.Sound.updateSound;

import static uet.oop.bomberman.utilities.Sound.updateSound;

import java.util.Map.Entry;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.mob.Mob;
import uet.oop.bomberman.entities.mob.enemy.Balloom;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.enemy.Oneal;
import uet.oop.bomberman.entities.tile.*;
import uet.oop.bomberman.entities.tile.powerup.BombItem;
import uet.oop.bomberman.entities.tile.powerup.FlameItem;
import uet.oop.bomberman.entities.tile.powerup.SpeedItem;
import uet.oop.bomberman.graphics.Camera;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.utilities.Input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Board {
    Map<Integer, Bomb> bombs = new HashMap<>(); // key la id cua entity trong board (ma tran)
    Map<Integer, Flame> flames = new HashMap<>();
    List<Entity> stillObjects = new ArrayList<>();
    List<Entity> mobs = new ArrayList<>();
    List<Bomber> bombers = new ArrayList<>();
    Input _input;
    public int level = 1;
    public static int TILE_WIDTH = 25,TILE_HEIGHT = 15,
            PIXEL_WIDTH = Sprite.SCALED_SIZE * TILE_WIDTH,
            PIXEL_HEIGHT = Sprite.SCALED_SIZE * TILE_HEIGHT;

    public Input get_input() {
        return _input;
    }

    public static int getTileWidth() {
        return TILE_WIDTH;
    }

    public static int getTileHeight() {
        return TILE_HEIGHT;
    }

    public Board(Game game) {
        _input = game._input;
        createMap(level);
    }

    public void createMap(int level) {
        // reset map
        stillObjects.clear();
        mobs.clear();
        bombers.clear();
        bombs.clear();
        flames.clear();
        Camera.reset();
        //doc map tu file
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("levels/Level" + level + ".txt");
        assert inputStream != null;
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringTokenizer st = null;
        try {
            st = new StringTokenizer(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert st != null;
        level = Integer.parseInt(st.nextToken());
        TILE_HEIGHT = Integer.parseInt(st.nextToken());
        TILE_WIDTH = Integer.parseInt(st.nextToken());
        PIXEL_WIDTH = Sprite.SCALED_SIZE * TILE_WIDTH;
        PIXEL_HEIGHT = Sprite.SCALED_SIZE * TILE_HEIGHT;
        for (int j = 0; j < TILE_HEIGHT; j++) {
            for (int i = 0; i < TILE_WIDTH; i++) {
                char c = 0;
                try {
                    c = (char) br.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                switch (c) {
                    case '#': {
                        stillObjects.add(new Wall(i, j));
                        break;
                    }
                    case 'p': {
                        bombers.add(new Bomber(i, j, this));
                        stillObjects.add(new Grass(i, j));
                        break;
                    }
                    case '*': {
                        TileLayer le = new TileLayer(i, j, new Brick(i, j));
                        stillObjects.add(le);
                        break;
                    }
                    case '1': {
                        mobs.add(new Balloom(i, j, this
                        ));
                        stillObjects.add(new Grass(i, j));
                        break;
                    }
                    case '2': {
                        mobs.add(new Oneal(i, j, this));
                        stillObjects.add(new Grass(i, j));
                        break;
                    }
                    case 'x': {
                        stillObjects.add(new TileLayer(i, j, new Portal(i, j), new Brick(i, j)));
                        break;
                    }
                    case 'b': {
                        stillObjects.add(new TileLayer(i, j, new BombItem(i, j), new Brick(i, j)));
                        break;
                    }
                    case 'f': {
                        stillObjects.add(new TileLayer(i, j, new FlameItem(i, j), new Brick(i, j)));
                        break;
                    }
                    case 's': {
                        stillObjects.add(new TileLayer(i, j, new SpeedItem(i, j), new Brick(i, j)));
                        break;
                    }
                    default: {
                        stillObjects.add(new Grass(i, j));
                        break;
                    }
                }
            }

            try {
                br.read();
                br.read();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public Entity getTileAt(int xUnit, int yUnit) {
        //if (XTile + YTile * Board.getTileWidth() < 0) return null;
        if (bombs.containsKey(xUnit + yUnit * Board.getTileWidth()))
            return bombs.get(xUnit + yUnit * Board.getTileWidth());
        if (flames.containsKey(xUnit + yUnit * Board.getTileWidth()))
            return flames.get(xUnit + yUnit * Board.getTileWidth());
        return stillObjects.get(xUnit + yUnit * Board.getTileWidth());
    }

   public Entity getTileAt(double x, double y) {
        int xUnit = (int) (x) / (Sprite.SCALED_SIZE);
        int yUnit = (int) (y) / (Sprite.SCALED_SIZE);
        return getTileAt(xUnit, yUnit);
    }

    public void update(double secondsSinceLastFrame) {

        updateTiles(secondsSinceLastFrame);
        updateMobs(secondsSinceLastFrame);
        updateBomb(secondsSinceLastFrame);
        updateFlames(secondsSinceLastFrame);
        updateSound(getPlayer());
    }

    private void updateMobs(double secondsSinceLastFrame) {
        if (mobs.size() == 0) {
            Portal.passable = true;
            if (Portal.passed) levelUp();
        }
        if (!getPlayer().isAlive()) {
            Bomber.trialLeft--;
            if (Bomber.trialLeft <= 0) {
            }
               // Game.gamePhase = GamePhase.GAME_OVER;

        }
        bombers.forEach(g -> g.update(secondsSinceLastFrame));
        //Iterator<Entity> mobsIterator = mobs.listIterator();

        for (Entity mob : mobs) {
            Mob m = (Mob) mob;
            m.update(secondsSinceLastFrame);
            if (!m.isAlive()) {
                mobs.remove(m);
            }
        }
    }


    private void levelUp() {
        createMap(++level);
        //Game.gamePhase = GamePhase.GAME_LEVEL_PRELOAD;
        Game.gameState = GameState.GAME_LEVEL_PRELOAD;
        Score.timeLeft = 200;
    }

    private void updateTiles(double secondsSinceLastFrame) {

        stillObjects.forEach(g -> g.update(secondsSinceLastFrame));
    }

    private void updateFlames(double s) {
        Iterator<Map.Entry<Integer, Flame>> flamesIterator = flames.entrySet().iterator();
        while (flamesIterator.hasNext()) {
            Map.Entry<Integer, Flame> f = flamesIterator.next();
            Flame ff = f.getValue();
            ff.update(s);
            if (ff.getTimeLeft() < 0) {
                flamesIterator.remove();
            }
        }
    }

    private void updateBomb(double s) {

        Iterator<Map.Entry<Integer, Bomb>> bombIterator = bombs.entrySet().iterator();
        while (bombIterator.hasNext()) {
            Entry<Integer, Bomb> b = bombIterator.next();
            Bomb bb = (Bomb) b.getValue();
            bb.update(s);
            if (bb.isExploded()) {
                bombIterator.remove();
            }
        }
    }

    public void render(Camera camera) {
        camera.prepare();
        stillObjects.forEach(g -> g.render(camera));
        flames.forEach((g, v) -> v.render(camera));
        mobs.forEach(g -> g.render(camera));
        bombs.forEach((g, v) -> v.render(camera));
        bombers.forEach(g -> g.render(camera));
    }

    public boolean containsBomb(Bomb e) {
        return bombs.containsKey(e.getIdInBoard());
    }

    public void addBomb(Bomb e) {
        if (!bombs.containsKey(e.getIdInBoard()))
            bombs.put(e.getIdInBoard(), e);
    }

    public Bomb getBomb(int xTile, int yTile) {
        if (bombs.containsKey(xTile + yTile * Board.getTileWidth())) {
            return bombs.get(xTile + yTile * Board.getTileWidth());
        }
        return null;
    }

    public void addFlame(Map<Integer, Flame> me) {
        flames.putAll(me);
    }

    public void clearFlames() {
        flames.clear();
    }

    public Bomber getPlayer() {
        return bombers.get(0);
    }
}
