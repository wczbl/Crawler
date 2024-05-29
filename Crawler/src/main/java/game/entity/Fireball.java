package game.entity;

import game.entity.particle.FireParticle;
import game.gfx.Camera;
import game.gfx.ViewPort;
import game.math.Vec3;

public class Fireball extends Entity {

    public int lifeTime;

    public Fireball(Vec3 pos, Vec3 posA) {
        super(pos);
        this.posA = posA;
        r *= 0.04;
        lifeTime = 120;
    }

    public void tick() {
        if (lifeTime-- <= 0) {
            die();
            return;
        }

        pos = pos.add(posA);

        for (int i = 0; i < 5; i++) {
            double xo = (random.nextDouble() * 2.0 - 1.0) * 0.01;
            double zo = (random.nextDouble() * 2.0 - 1.0) * 0.01;

            FireParticle p = new FireParticle(pos.add(new Vec3(xo, 0, zo)));
            p.posA = p.posA.scale(0.01);
            level.add(p);
        }
    }

    public void render(ViewPort viewPort, Camera cam) {
        viewPort.renderSprite(pos, r, cam, 2);
    }
}