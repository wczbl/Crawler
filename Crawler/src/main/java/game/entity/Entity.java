package game.entity;

import game.entity.projectile.Projectile;
import game.gfx.Camera;
import game.gfx.ViewPort;
import game.level.Level;
import game.math.Vec3;

import java.util.Random;
import java.util.Set;

public abstract class Entity {

    public static final Random random = new Random();
    public Vec3 pos;
    public Vec3 posA = Vec3.zero;
    public Vec3 rot = Vec3.zero;
    public Vec3 rotA = Vec3.zero;
    public double r = 0.5;
    public boolean removed;
    public Level level;
    public int sprite;
    public double gravity = 0.01;
    public double bounce = 0.01;
    public double friction = 0.77;
    public int movement;
    public boolean onGround;
    public boolean checkCollision = true;

    public Entity(Vec3 pos) { this.pos = pos; }

    public void init(Level level) { this.level = level; }

    public void tick() {}
    public void render(ViewPort viewPort, Camera cam) {}

    public void move() {
        onGround = false;

        int xStep = (int)(Math.abs(posA.x * 100.0) + 1.0);
        for (int i = xStep; i > 0; --i) {
            if (isFree(pos.add(new Vec3(posA.x * i / (double)xStep, 0.0, 0.0)))) {
                pos.x += posA.x * i / (double)xStep;
                movement++;
                break;
            }
            posA.x *= -bounce;
        }

        int yStep = (int)(Math.abs(posA.y * 100.0) + 1.0);
        for (int i = yStep; i > 0; --i) {
            if (isFree(pos.add(new Vec3(0.0, posA.y * i / (double) yStep, 0.0)))) {
                pos.y += posA.y * i / (double)yStep;
                break;
            }

            if (posA.y > 0.0) onGround = true;

            posA.y *= -bounce;
        }

        int zStep = (int)(Math.abs(posA.z * 100.0) + 1.0);
        for (int i = zStep; i > 0; --i) {
            if (isFree(this.pos.add(new Vec3(0.0, 0.0, posA.z * i / (double) zStep)))) {
                pos.z += posA.z * i / (double) zStep;
                movement++;
                break;
            }

            posA.z *= -bounce;
        }
    }

    public boolean isFree(Vec3 newPos) {
        if (!checkCollision) return true;

        double y1 = newPos.y + r;

        if (y1 > 0.5) return false;

        int xc = (int)(newPos.x + 0.5);
        int zc = (int)(newPos.z + 0.5);
        int rr = 2;
        for (int z = zc - rr; z <= zc + rr; ++z) {
            for (int x = xc - rr; x <= xc + rr; ++x) {
                Set<Entity> entities = level.getEntities(x, z);
                for (Entity e : entities) {
                    if (e.removed || e == this || e.intersects(this, pos.x, pos.z, r) || !e.intersects(this, newPos.x, newPos.z, r)) continue;
                    e.touchedBy(this);
                    touchedBy(e);
                    if (!e.blocks(this)) continue;
                    return false;
                }
            }
        }

        return true;
    }

    public boolean intersects(Entity e, double x, double z, double r) {
        if (pos.x + r <= x - this.r) return false;
        if (pos.x - r >= x + this.r) return false;
        if (pos.z + r <= z - this.r) return false;
        return !(this.pos.z - this.r >= z + r);
    }

    public void touchedBy(Entity e) {}
    public boolean blocks(Entity e) { return true; }
    public void die() { removed = true; }
    public boolean hitBy(Projectile b) { return false; }
}