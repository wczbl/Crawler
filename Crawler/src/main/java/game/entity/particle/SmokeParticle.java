package game.entity.particle;

import game.math.Vec3;

public class SmokeParticle extends Particle {

    public SmokeParticle(Vec3 pos) {
        super(pos);
        gravity = -0.001;
        r *= 0.5;
        lifeTime = random.nextInt(5) + 20;
        sprite = random.nextInt(3) + 8;
    }

}
