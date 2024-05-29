package game.level.tile;

import game.level.Level;

public class GrassTile extends Tile {

    public GrassTile(int id) { super(id); }

    public int getTexture(Level level, int x, int y) { return 2; }

}
