package game.entity;

import game.gfx.Art;
import game.gfx.Camera;
import game.gfx.ViewPort;
import game.math.Vec3;

public class Tree extends Entity {

    public Tree(Vec3 pos) { super(pos); }

    private int tickTime;

    public void tick() { tickTime++; }

    public void render(ViewPort viewPort, Camera cam) {
        viewPort.renderSprite(pos, cam, Art.sprites[9 + (tickTime / 60 % 2)]);
    }

}