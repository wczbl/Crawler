package game;

import game.gfx.Bitmap;
import game.gfx.Font;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GameComponent extends Canvas implements Runnable {

    public static final String TITLE = "Crawler";
    public static final int WIDTH = 240;
    public static final int HEIGHT = 180;
    public static final int SCALE = 4;
    private boolean running;
    private BufferedImage screenImage;
    private Bitmap screenBitmap;
    private Game game;
    private InputHandler input;
    private int tickTime;
    private boolean paused;

    public synchronized void start() {
        if (running) return;
        running = true;
        new Thread(this).start();
    }

    public void run() {
        init();

        long lastTime = System.nanoTime();
        double delta = 0.0;
        double nsPerTick = 1000000000.0 / 60.0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        int ticks = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            while (delta >= 1) {
                delta--;
                tick();
                ticks++;
            }

            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("frames: " + frames + " ticks: " + ticks);
                frames = 0;
                ticks = 0;
            }

            try {
                Thread.sleep(2L);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    public void init() {
        screenImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        screenBitmap = new Bitmap(screenImage);
        game = new Game();
        input = new InputHandler(this);
    }

    public void tick() {
        tickTime++;
        input.tick();

        // Toggle paused state when pause is clicked
        if (input.pause.clicked && hasFocus()) {
            paused = !paused;
            tickTime = 0;
        }

        if (paused && (input.leftClicked || input.rightClicked)) paused = false;

        // Don't update the game if it's paused or if the window is out of focus
        if (paused || !hasFocus()) return;

        game.tick(input);
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            requestFocus();
            return;
        }

        screenBitmap.clear(0);
        game.render(screenBitmap);

        if (!hasFocus() || paused) {
            String title = "PAUSED";
            Font.draw(title, screenBitmap, (GameComponent.WIDTH - title.length() * 5 * 2) / 2, GameComponent.HEIGHT / 2 - 50, 2);

            String text = "Click to focus!";
            if (tickTime / 40 % 2 == 0) Font.draw(text, screenBitmap, (GameComponent.WIDTH - text.length() * 5) / 2, GameComponent.HEIGHT / 2 + 30);
        }

        Graphics g = bs.getDrawGraphics();

        g.drawImage(screenImage, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Dimension d = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        GameComponent game = new GameComponent();
        frame.setTitle(TITLE);
        game.setMinimumSize(d);
        game.setMaximumSize(d);
        game.setPreferredSize(d);
        frame.setLayout(new BorderLayout());
        frame.add(game, BorderLayout.CENTER);
        frame.pack();

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.setFocusable(true);

        game.start();
    }
}