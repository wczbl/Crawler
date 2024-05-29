package game.level.tile;

import game.level.Level;

public class GroundTile extends Tile {

    public GroundTile(int id) { super(id); }

    public int getTexture(Level level, int x, int y) { return 1; }

}