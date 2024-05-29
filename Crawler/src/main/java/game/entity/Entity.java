package game.entity;

import game.gfx.Camera;
import game.gfx.ViewPort;
import game.level.Level;
import game.math.Vec3;

import java.util.Random;

public abstract class Entity {

    public static final Random random = new Random();
    public Vec3 pos;
    public Vec3 posA = Vec3.zero;
    public double r = 0.5;
    public boolean removed;
    public Level level;
    public int sprite;

    public Entity(Vec3 pos) { this.pos = pos; }

    public void init(Level level) { this.level = level; }

    public void tick() {}
    public void render(ViewPort viewPort, Camera cam) {}
    public void die() { removed = true; }
}