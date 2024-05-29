package game.entity.particle;

import game.gfx.Camera;
import game.gfx.ViewPort;
import game.math.Vec3;

public class FireParticle extends Particle {

    public FireParticle(Vec3 pos) {
        super(pos);
        gravity = -0.001;
        r *= 0.8;
        lifeTime = maxLifeTime = random.nextInt(35) + 30;
    }

    public void render(ViewPort viewPort, Camera cam) {
        sprite = (int) (lifeTime / (double) maxLifeTime * 5.0);
        super.render(viewPort, cam);
    }

    public void die() {
        super.die();
        if (!random.nextBoolean()) {
            Particle p = new SmokeParticle(pos);
            p.posA = p.posA.scale(0.2).mul(posA.scale(0.2));
            level.add(p);
        }
    }
}