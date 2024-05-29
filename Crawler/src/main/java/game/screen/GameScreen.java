package game.screen;

import game.GameComponent;
import game.InputHandler;
import game.entity.*;
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

    // UI Shenanigans
    private int lowHealthCol = 0x7F0000;
    private int highHealthCol = 0x00FF00;

    private int lowTimeCol = 0xFF6A00;
    private int highTimeCol = 0xFFD800;

    public GameScreen() {
        cam = new Camera(player);
        viewPort = new ViewPort(GAME_VIEW_WIDTH, GAME_VIEW_HEIGHT);
        level = new Level(32, 32);
        level.add(player);

        for (int i = 0; i < level.w * level.h; i++) {
            if (Math.random() < 0.02) level.add(new Slime(new Vec3(i % level.w, 0, i / level.w)));
            if (Math.random() < 0.05) level.add(new Campfire(new Vec3(i % level.w, 0, i / level.w)));
            if (Math.random() < 0.1) level.add(new Tree(new Vec3(i % level.w, 0, i / level.w)));
            if (Math.random() < 0.3) level.add(new BigTree(new Vec3(i % level.w, 0, i / level.w)));

        }
    }

    public void tick(InputHandler input) {
        cam.tick();
        level.tick();
        player.tickInput(input);
    }

    public void render(Bitmap screen) {
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
    }

}