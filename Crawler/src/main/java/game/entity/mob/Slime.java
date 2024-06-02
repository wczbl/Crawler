package game.entity.mob;

import game.entity.projectile.Projectile;
import game.gfx.Art;
import game.gfx.Camera;
import game.gfx.ViewPort;
import game.math.Vec3;

public class Slime extends Mob {

    public Slime(Vec3 pos) {
        super(pos);
        sprite = 6;
    }

    public void tick() {
        super.tick();

        if (target != null && Math.random() < 0.01 && jump()) {
            Vec3 v = target.pos.sub(pos).normalize();
            posA = posA.add(v.scale(moveSpeed * 53));
        }
    }

    public void render(ViewPort viewPort, Camera cam) {
        if (hurtTime > 0 && hurtTime / 4 % 2 == 0) return;
        int s = sprite + (jumpTime > 0 ? 1 : 0);
        viewPort.renderSprite(pos, cam, Art.sprites[s]);
    }

}