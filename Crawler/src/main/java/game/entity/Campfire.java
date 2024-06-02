package game.entity;

import game.entity.particle.FireParticle;
import game.gfx.Art;
import game.gfx.Camera;
import game.gfx.ViewPort;
import game.math.Vec3;

public class Campfire extends Entity {

    public Campfire(Vec3 pos) {
        super(pos);
        sprite = 2;
    }

    public void tick() {
        if (random.nextDouble() < 0.51) {
            double xo = (random.nextDouble() * 2.0 - 1.0) * 0.2;
            double zo = (random.nextDouble() * 2.0 - 1.0) * 0.2;

            FireParticle p = new FireParticle(pos.add(new Vec3(xo, 0.3, zo)));
            p.posA = p.posA.scale(0.01);
            level.add(p);
        }
    }

    public void render(ViewPort viewPort, Camera cam) {
        viewPort.renderSprite(pos, cam, Art.sprites[sprite]);
    }
}