package game.level;

import game.entity.Entity;
import game.entity.particle.Particle;
import game.gfx.Camera;
import game.gfx.ViewPort;
import game.level.tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {

    private static final List<Entity> entities = new ArrayList<>();
    private static final List<Particle> particles = new ArrayList<>();
    private static final Random random = new Random();
    public final int w;
    public final int h;
    public int[] tiles;
    public double friction = 0.9;
    public int tickTime;

    public Level(int w, int h) {
        this.w = w;
        this.h = h;
        tiles = new int[w * h];

        for (int i = 0; i < w * h; i++) {
            setTile(random.nextInt(100) < 30 ? Tile.water : Tile.grass, i % w, i / w);
        }
    }

    public void add(Entity e) {
        entities.add(e);
        e.init(this);
    }

    public void add(Particle p) {
        particles.add(p);
        p.init(this);
    }

    public void tick() {
        tickTime++;

        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (!e.removed) {
                e.tick();
                continue;
            }

            entities.remove(i--);
        }

        for (int i = 0; i < particles.size(); i++) {
            Particle p = particles.get(i);
            if (!p.removed) {
                p.tick();
                continue;
            }

            particles.remove(i--);
        }
    }

    public void render(ViewPort viewPort, Camera cam) {
        for (Entity e : entities) {
            e.render(viewPort, cam);
        }

        for (Particle p : particles) {
            p.render(viewPort, cam);
        }
    }

    public void setTile(Tile tile, int x, int y) {
        if (x < 0 || y < 0 || x >= w || y >= h) return;
        tiles[x + y * w] = tile.id;
    }

    public Tile getTile(int x, int y) {
        return x >= 0 && y >= 0 && x < w && y < h ? Tile.TILES[tiles[x + y * w]] : Tile.water;
    }

}