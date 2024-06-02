package game.entity.projectile;

import game.entity.Entity;
import game.entity.particle.FireParticle;
import game.gfx.Camera;
import game.gfx.ViewPort;
import game.math.Vec3;

public class Fireball extends Projectile {

    public Fireball(Entity owner, Vec3 pos, Vec3 posA) { super(owner, pos, posA); }

    public void tick() {
        super.tick();
        for (int i = 0; i < 5; i++) {
            if (random.nextDouble() > 0.4) continue;
            double xo = (random.nextDouble() * 2.0 - 1.0) * 0.01;
            double zo = (random.nextDouble() * 2.0 - 1.0) * 0.01;
            FireParticle p = new FireParticle(pos.add(new Vec3(xo, 0, zo)));
            p.posA = p.posA.scale(0.01);
            level.add(p);
        }
    }

    public void render(ViewPort viewPort, Camera cam) {}


}