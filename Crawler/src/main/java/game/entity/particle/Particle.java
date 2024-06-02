package game.entity.particle;

import game.entity.Entity;
import game.gfx.Art;
import game.gfx.Camera;
import game.gfx.ViewPort;
import game.math.Vec3;

import javax.swing.*;

public abstract class Particle extends Entity {

    public int lifeTime;
    public int maxLifeTime;
    public int sprite;
    public double gravity = 0.01;

    public Particle(Vec3 pos) {
        super(pos);
        do {
            posA = new Vec3(random.nextDouble() * 2.0 - 1.0,
                            random.nextDouble() - 1.0,
                            random.nextDouble() * 2.0 - 1.0);
        } while (posA.lengthSqr() > 1.0);

        double speed = 0.3;
        r = 0.1;
        lifeTime = maxLifeTime = random.nextInt(30) + 60;
        posA = posA.scale(speed);
        checkCollision = false;
    }

    public void tick() {
        if (lifeTime-- <= 0) {
            die();
            return;
        }

        move();
        posA = posA.scale(friction);
        posA.y += gravity;
    }

    public void render(ViewPort viewPort, Camera cam) { viewPort.renderSprite(pos, r, cam, Art.particles[sprite]); }

}