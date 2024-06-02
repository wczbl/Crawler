package game.entity.mob;

import game.gfx.Art;
import game.gfx.Camera;
import game.gfx.ViewPort;
import game.math.Vec3;

public class Zombie extends Mob {

    public Zombie(Vec3 pos) {
        super(pos);
        sprite = 6 + 1 * 8;
    }

    public void tick() {
        super.tick();
        if (target != null) {
            Vec3 v = target.pos.sub(pos).normalize();
            posA = posA.add(v.scale(moveSpeed));
        }
    }

    public void render(ViewPort viewPort, Camera cam) {
        if (hurtTime > 0 && hurtTime / 4 % 2 == 0) return;
        int s = sprite + movement / 30 % 2;
        viewPort.renderSprite(pos, cam, Art.sprites[s]);
    }

}