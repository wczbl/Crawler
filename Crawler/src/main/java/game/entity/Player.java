package game.entity;

import game.InputHandler;
import game.entity.projectile.Fireball;
import game.gfx.Camera;
import game.gfx.ViewPort;
import game.math.Vec3;

public class Player extends Entity {

    public double forwardSpeed = 0.03;
    public double turnSpeed = 0.01;
    public double rCos;
    public double rSin;
    public int chargeTime;
    public int health;
    public int maxHealth = 100;
    public int maxChargeTime = 50;

    public Player(Vec3 pos) {
        super(pos);
        health = maxHealth;
        chargeTime = maxChargeTime;
    }

    public void tickInput(InputHandler input) {
        double xm = 0.0;
        double zm = 0.0;

        if (input.up.down) zm--;
        if (input.down.down) zm++;
        if (input.left.down) xm--;
        if (input.right.down) xm++;

        double d = xm * xm + zm * zm;
        d = d > 0.001 ? Math.sqrt(d) : 1;

        xm /= d;
        zm /= d;

        posA.x -= (xm * rCos + zm * rSin) * forwardSpeed;
        posA.z -= (zm * rCos - xm * rSin) * forwardSpeed;

        if (input.turnLeft.down) rotA.x += turnSpeed;
        if (input.turnRight.down) rotA.x -= turnSpeed;

        if ((input.ability.down || input.rightPressed) && chargeTime >= maxChargeTime) {
            Vec3 pa = new Vec3(0, 0, 1).rotY(rot.x).normalize().scale(0.2);
            level.add(new Fireball(this, pos.add(new Vec3(0, 0.2, 0)), pa));
            chargeTime = 0;
        }
    }

    public void tick() {
        if (chargeTime < maxChargeTime) chargeTime++;

        rot = rot.add(rotA);
        rotA = rotA.scale(0.77);

        rCos = Math.cos(rot.x);
        rSin = Math.sin(rot.x);

        move();

        posA = posA.scale(friction);
    }

    public void render(ViewPort viewPort, Camera cam) {}

}