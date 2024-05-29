package game.entity;

import game.InputHandler;
import game.gfx.Camera;
import game.gfx.ViewPort;
import game.math.Vec3;

public class Player extends Entity {

    public double forwardSpeed = 0.03;
    public double turnSpeed = 0.01;
    public double xRot = 0.0;
    public double xRotA = 0.0;
    public double rCos;
    public double rSin;
    public int chargeTime;
    public int health;
    public int maxHealth = 100;
    public int maxChargeTime = 50;

    public Player(Vec3 pos) {
        super(pos);
        rCos = Math.cos(xRot);
        rSin = Math.sin(xRot);
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

        if (input.turnLeft.down) xRotA += turnSpeed;
        if (input.turnRight.down) xRotA -= turnSpeed;

        rCos = Math.cos(xRot);
        rSin = Math.sin(xRot);

        posA.x -= (xm * rCos + zm * rSin) * forwardSpeed;
        posA.z -= (zm * rCos - xm * rSin) * forwardSpeed;

        pos = pos.add(posA);
        posA = posA.scale(0.77);

        xRot += xRotA;
        xRotA *= 0.77;

        if ((input.leftPressed || input.rightPressed || input.ability.down || input.attack.down) && chargeTime >= maxChargeTime) {
            Vec3 p = new Vec3(0, 0, 1).rotY(xRot).normalize().scale(0.06);
            level.add(new Fireball(pos.add(new Vec3(0, 0.2, 0.4)), p));
            chargeTime = 0;
        }
    }

    public void tick() {
        if (chargeTime < maxChargeTime) chargeTime++;
    }

    public void render(ViewPort viewPort, Camera cam) {}

}