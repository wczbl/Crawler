package game.entity;

import game.gfx.Camera;
import game.gfx.ViewPort;
import game.math.Vec3;

public class BigTree extends Entity {

    public BigTree(Vec3 pos) { super(pos); }

    public void render(ViewPort viewPort, Camera cam) {
        viewPort.renderSprite(pos, 0.5, cam, 2 * 8);
        viewPort.renderSprite(pos.sub(new Vec3(0, 0.5, 0)), 0.5, cam, 8);
    }
}