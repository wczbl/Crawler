package game.entity.mob;

import game.entity.Entity;
import game.entity.Player;
import game.entity.particle.PoofParticle;
import game.entity.projectile.Projectile;
import game.gfx.Art;
import game.gfx.Camera;
import game.gfx.ViewPort;
import game.math.Vec3;

public class Mob extends Entity {

    protected Entity target;
    protected double moveSpeed = 0.004;
    protected int jumpTime;
    protected double viewRadius = 3.0;
    protected double hurtTime;
    protected int health;

    public Mob(Vec3 pos) {
        super(pos);
        health = 1;
    }

    public void tick() {
        if (jumpTime > 0) jumpTime--;
        if (hurtTime > 0) hurtTime--;
        if (health <= 0) die();

        if (target == null || Math.random() < 0.001) target = level.findTarget(Player.class, pos, viewRadius, 2.0);

        move();
        posA = posA.scale(friction);
        posA.y += gravity;
    }

    public boolean jump() {
        if (jumpTime > 0) return false;
        if (!onGround) return false;

        posA.y -= 0.2;
        jumpTime = 30;
        return true;
    }

    public void render(ViewPort viewPort, Camera cam) {
        if (hurtTime > 0 && hurtTime / 4 % 2 == 0) return;
        viewPort.renderSprite(pos, cam, Art.sprites[sprite]);
    }

    public boolean hitBy(Projectile b) {
        hurtTime = 60;
        health--;
        return true;
    }

    public void die() {
        for (int i = 0; i < 50; i++) {
            double xo = (random.nextDouble() * 2.0 - 1.0) * 0.01;
            double yo = (random.nextDouble() * 2.0 - 1.0) * 0.5;
            double zo = (random.nextDouble() * 2.0 - 1.0) * 0.01;
            PoofParticle p = new PoofParticle(pos.copy().add(new Vec3(xo, yo, zo)));
            p.posA = p.posA.scale(0.5);
            level.add(p);
        }

        super.die();
    }
}
