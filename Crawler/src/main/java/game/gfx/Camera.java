package game.gfx;

import game.GameComponent;
import game.InputHandler;
import game.entity.Player;
import game.math.Vec2;
import game.math.Vec3;
import game.screen.GameScreen;

public class Camera {

    public double xCenter;
    public double yCenter;
    public double fov = 120.0;
    public Vec3 pos = Vec3.zero;
    public double xRot = 0.0;
    public double nearClip = 0.01;
    public double farClip = 10.0;
    public double rCos;
    public double rSin;
    public Player player;

    public Camera(Player player) {
        rCos = Math.cos(xRot);
        rSin = Math.sin(xRot);
        this.player = player;

        xCenter = GameScreen.GAME_VIEW_WIDTH / 2.0;
        yCenter = GameScreen.GAME_VIEW_HEIGHT / 3.0;
    }

    public void tick() {
        xRot = player.xRot;
        rCos = Math.cos(xRot);
        rSin = Math.sin(xRot);
        pos = player.pos.add(new Vec3(player.rSin * 0.3, 0.0, player.rCos * 0.3));
    }

    public Vec2 project(Vec3 v) {
        double x = xCenter - v.x * fov / v.z;
        double y = yCenter + v.y * fov / v.z;
        return new Vec2(x, y);
    }

    public Vec3 toCam(Vec3 v) { return v.sub(pos).rotY(-xRot); }
    public Vec3 toCam(Vec3 v, double s) { return v.sub(pos).scale(s).rotY(-xRot); }

}