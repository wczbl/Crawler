package game.entity;

import game.gfx.Camera;
import game.gfx.ViewPort;
import game.math.Vec3;

public class Slime extends Entity {

    public Slime(Vec3 pos) { super(pos); }

    public void render(ViewPort viewPort, Camera cam) {
        viewPort.renderSprite(pos, 0.5, cam, 1);
    }

}