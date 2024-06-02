package game.entity.projectile;

import game.entity.Entity;
import game.math.Vec3;

public class Projectile extends Entity {

    public int lifeTime;
    public Entity owner;

    public Projectile(Entity owner, Vec3 pos, Vec3 posA) {
        super(pos);
        this.owner = owner;
        this.posA = posA;
        r = 0.04;
        lifeTime = 120;
    }

    public void tick() {
        if (lifeTime-- <= 0) {
            die();
            return;
        }

        move();
    }

    public void touchedBy(Entity e) {
        if (e == owner) return;
        if (e.hitBy(this)) die();
    }
}