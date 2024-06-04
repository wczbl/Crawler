package game.screen;

import game.GameComponent;
import game.InputHandler;
import game.entity.*;
import game.entity.mob.Bat;
import game.entity.mob.Slime;
import game.entity.mob.Zombie;
import game.gfx.*;
import game.level.Level;
import game.math.MathUtil;
import game.math.Vec3;

import java.util.Random;


public class GameScreen extends Screen {

    public static final Random random = new Random();
    public static int GAME_VIEW_WIDTH = GameComponent.WIDTH;
    public static int GAME_VIEW_HEIGHT = GameComponent.HEIGHT - 19;
    private Level level;
    private Player player = new Player(Vec3.zero);
    private Camera cam;
    private ViewPort viewPort;
    private static long[] estimates = new long[120];
    private static int estimateIndex;

    // UI Shenanigans
    private int lowHealthCol = 0x7F0000;
    private int highHealthCol = 0x00FF00;

    private int lowTimeCol = 0xFF6A00;
    private int highTimeCol = 0xFFD800;

    private int enemyCount;

    public GameScreen() {
        cam = new Camera(player);
        viewPort = new ViewPort(GAME_VIEW_WIDTH, GAME_VIEW_HEIGHT);
        level = new Level(32, 32);
        level.add(player);

        level.add(new Slime(new Vec3(1, 0, 1)));
        level.add(new Bat(new Vec3(1, 0, 2)));
        level.add(new Zombie(new Vec3(2, 0, 1)));
        level.add(new Slime(new Vec3(2, 0, 2)));

    }

    public void tick(InputHandler input) {
        cam.tick();
        level.tick();
        player.tickInput(input);

        if (level.tickTime % estimates.length == 0) System.out.println("Render average: " + getAverageEstimate() + " ms");
    }

    public void render(Bitmap screen) {
        long last = System.currentTimeMillis();

        viewPort.updateZBuffer();
        level.render(viewPort, cam);
        viewPort.renderFloor(cam, level);
        viewPort.renderSky(cam);
        viewPort.post();

        screen.draw(viewPort);

        // HEALTH BAR
        int bw = player.maxHealth / 2;
        screen.fillRect(15, 168, bw, 5, 0x222222);

        int hbw = player.health / 2;
        for (int i = 0; i < hbw; i++) {
            double p = i / (double) player.maxHealth;
            int c = MathUtil.lerpRGB(lowHealthCol, highHealthCol, p);
            screen.fillRect(15 + i, 168, 1, 5, c);
        }


        // ABILITY CHARGE
        int acbw = player.maxChargeTime;
        screen.fillRect(180, 168, acbw, 5, 0x222222);

        int cbw = (player.chargeTime * 50) / player.maxChargeTime;
        for (int i = 0; i < cbw; i++) {
            double p = (player.chargeTime - i) / (double) player.maxChargeTime;
            int c = MathUtil.lerpRGB(highTimeCol, lowTimeCol, p);
            screen.fillRect(230 - cbw + i, 168, 1, 5, c);
        }

        // ICON DRAWING
        screen.draw(Art.gui[0], 3, 162);
        screen.draw(Art.gui[1], 220, 161);

        long now = System.currentTimeMillis();
        estimates[estimateIndex++ % estimates.length] = now - last;
    }

    public static double getAverageEstimate() {
        long result = 0;
        for (long estimate : estimates) {
            result += estimate;
        }

        return result / (double) estimates.length;
    }

}