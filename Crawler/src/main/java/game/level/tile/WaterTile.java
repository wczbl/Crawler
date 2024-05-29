package game.level.tile;

import game.level.Level;

import java.util.Random;

public class WaterTile extends Tile {

    private static final Random random = new Random();

    public WaterTile(int id) { super(id); }

    public int getTexture(Level level, int x, int y) {
        random.setSeed(((level.tickTime + x / 2 * y + 4311) / 10) * 54687121 + x * 3271612 + y * 3412987161L);

        boolean u = level.getTile(x, y - 1) == water;
        boolean d = level.getTile(x, y + 1) == water;
        boolean l = level.getTile(x - 1, y) == water;
        boolean r = level.getTile(x + 1, y) == water;

        int t = 0;

        if (u) t++;
        if (d) t += 4;
        if (l) t += 8;
        if (r) t += 2;

        int a = random.nextInt(2);
        return (16 + (a * 16)) + t;
    }
}