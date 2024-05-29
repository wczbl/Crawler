package game.gfx;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Art {

    public static Bitmap[] sprites = loadAndCut("/sprites.png", 16);
    public static Bitmap[] tiles = loadAndCut("/tiles.png", 16);
    public static Bitmap[] gui = loadAndCut("/gui.png", 16);
    public static Bitmap[] walls = loadAndCut("/walls.png", 16);
    public static Bitmap[] font = loadAndCut("/font.png", 8);
    public static Bitmap[] particles = loadAndCut("/particles.png", 4);
    public static Bitmap moon = load("/moon.png");
    public static Bitmap sky = load("/sky.png");

    public static Bitmap load(String path) {
        BufferedImage image;
        try {
            image = ImageIO.read(Art.class.getResourceAsStream(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + path);
        }

        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = result.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return new Bitmap(result);
    }

    public static Bitmap[] loadAndCut(String path, int size) {
        BufferedImage sheet;
        try {
            sheet = ImageIO.read(Art.class.getResourceAsStream(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + path);
        }

        int sw = sheet.getWidth() / size;
        int sh = sheet.getHeight() / size;
        Bitmap[] result = new Bitmap[sw * sh];
        for (int y = 0; y < sh; y++) {
            for (int x = 0; x < sw; x++) {
                BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
                Graphics g = image.getGraphics();
                g.drawImage(sheet, -x * size, -y * size, null);
                g.dispose();
                result[x + y * sw] = new Bitmap(image);
            }
        }

        return result;
    }

}