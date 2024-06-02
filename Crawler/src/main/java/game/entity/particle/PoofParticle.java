package game.entity.particle;

import game.math.Vec3;

public class PoofParticle extends Particle {

    public PoofParticle(Vec3 pos) {
        super(pos);
        sprite = 2 * 8;
        gravity = -0.0005;
        r *= 0.8;
        lifeTime = maxLifeTime = random.nextInt(35) + 30;
    }

}
