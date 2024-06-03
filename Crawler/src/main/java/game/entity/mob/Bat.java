package game.entity.mob;

import game.gfx.Art;
import game.gfx.Camera;
import game.gfx.ViewPort;
import game.math.Vec3;

public class Bat extends Mob {

    public Bat(Vec3 pos) {
        super(pos);
        sprite = 6 + 2 * 8;
        health = 3;
    }

    public void tick() {
        if (hurtTime > 0) hurtTime--;
        if (health <= 0) die();

        move();

        if (posA.x == 0 || posA.z == 0) {
            rotA.x = (random.nextGaussian() * random.nextGaussian()) * 0.3;
        }

        rotA.x += (random.nextGaussian() * random.nextDouble()) * 0.1;
        rot = rot.add(rotA);
        rotA = rotA.scale(0.77);

        posA = posA.scale(0.77);

        posA.x += Math.sin(rot.x) * moveSpeed;
        posA.z += Math.cos(rot.x) * moveSpeed;
    }

    public void render(ViewPort viewPort, Camera cam) {
        if (hurtTime > 0 && hurtTime / 4 % 2 == 0) return;

        int s = sprite + movement / 20 % 2;
        viewPort.renderSprite(pos, cam, Art.sprites[s]);
    }
}
