package game.level;

import game.entity.Entity;
import game.entity.particle.Particle;
import game.gfx.Camera;
import game.gfx.ViewPort;
import game.level.tile.Tile;
import game.math.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Level {

    private static final List<Entity> entities = new ArrayList<>();
    private static final List<Particle> particles = new ArrayList<>();
    private static final Random random = new Random();
    public final int w;
    public final int h;
    public int[] tiles;
    public List<Entity>[] entitiesInTiles;
    public double friction = 0.9;
    public int tickTime;

    public Level(int w, int h) {
        this.w = w;
        this.h = h;
        tiles = new int[w * h];

        entitiesInTiles = new List[w * h];
        for (int i = 0; i < w * h; i++) {
            entitiesInTiles[i] = new ArrayList<>();
            setTile(random.nextInt(100) < 30 ? Tile.water : Tile.grass, i % w, i / w);
        }

    }

    public void add(Entity e) {
        entities.add(e);
        e.init(this);

        int xt = (int) (e.pos.x + 0.5);
        int yt = (int) (e.pos.z + 0.5);
        insertEntity(e, xt, yt);
    }

    public void add(Particle p) {
        particles.add(p);
        p.init(this);
    }

    public void tick() {
        tickTime++;

        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);

            int oxt = (int) (e.pos.x + 0.5);
            int oyt = (int) (e.pos.z + 0.5);

            if (!e.removed) {
                e.tick();

                if (!e.removed) {
                    int xt = (int) (e.pos.x + 0.5);
                    int yt = (int) (e.pos.z + 0.5);

                    if (xt != oxt || yt != oyt) {
                        removeEntity(e, oxt, oyt);
                        insertEntity(e, xt, yt);
                    }
                }
            }

            if (!e.removed) continue;

            entities.remove(i--);
            removeEntity(e, oxt, oyt);
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

    public Set<Entity> getEntities(int x, int z) {
        Set<Entity> result = EntityListCache.get();
        if (x >= 0 && z >= 0 && x < w && z < h) {
            result.addAll(entitiesInTiles[x + z * w]);
        }

        return result;
    }

    public <T> Entity findTarget(Class<T> target, Vec3 pos, double radius, double rnd) {
        int x0 = (int) (pos.x - radius + (random.nextDouble() * 2.0 - 1.0) * rnd + 0.5);
        int z0 = (int) (pos.z - radius + (random.nextDouble() * 2.0 - 1.0) * rnd + 0.5);
        int x1 = (int) (pos.x + radius + (random.nextDouble() * 2.0 - 1.0) * rnd + 0.5);
        int z1 = (int) (pos.z + radius + (random.nextDouble() * 2.0 - 1.0) * rnd + 0.5);

        for (int z = z0; z < z1; z++) {
            for (int x = x0; x < x1; x++) {
                for (Entity e : getEntities(x, z)) {
                    if (!target.isInstance(e)) continue;
                    return e;
                }
            }
        }

        return null;
    }

    public void setTile(Tile tile, int x, int y) {
        if (x < 0 || y < 0 || x >= w || y >= h) return;
        tiles[x + y * w] = tile.id;
    }

    public Tile getTile(int x, int y) {
        return x >= 0 && y >= 0 && x < w && y < h ? Tile.TILES[tiles[x + y * w]] : Tile.water;
    }

    public void insertEntity(Entity e, int x, int y) {
        if (x < 0 || y < 0 || x >= w || y >= h) return;
        entitiesInTiles[x + y * w].add(e);
    }

    public void removeEntity(Entity e, int x, int y) {
        if (x < 0 || y < 0 || x >= w || y >= h) return;
        entitiesInTiles[x + y * w].remove(e);
    }
}