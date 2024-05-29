package game.level.tile;

import game.level.Level;

public class Tile {

    public static final Tile[] TILES = new Tile[256];
    public static final Tile ground = new GroundTile(0);
    public static final Tile grass = new GrassTile(1);
    public static final Tile water = new WaterTile(2);
    public int id;

    public Tile(int id) {
        if (TILES[id] != null) throw new IllegalStateException("Tile IDs must be unique");
        this.id = id;
        TILES[id] = this;

    }

    public int getTexture(Level level, int x, int y) { return 0; }

}