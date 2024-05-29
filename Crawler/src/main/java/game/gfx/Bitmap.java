package game.gfx;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Bitmap {

    public int w;
    public int h;
    public int[] pixels;

    public Bitmap(BufferedImage image) {
        w = image.getWidth();
        h = image.getHeight();
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }

    public Bitmap(int w, int h) {
        this.w = w;
        this.h = h;
        pixels = new int[w * h];
    }

    public Bitmap(int w, int h, int[] pixels) {
        this.w = w;
        this.h = h;
        this.pixels = pixels;
    }

    public void clear(int col) { Arrays.fill(pixels, col); }

    public void draw(Bitmap b) {
        if (b.w > w) b.w = w;
        if (b.h > h) b.h = h;

        for (int y = 0; y < b.h; y++) {
            for (int x = 0; x < b.w; x++) {
                pixels[x + y * w] = b.pixels[x + y * b.w];
            }
        }
    }

    public void draw(Bitmap b, int xp, int yp) {
        int x0 = xp;
        int y0 = yp;
        int x1 = xp + b.w;
        int y1 = yp + b.h;

        if (x0 < 0) x0 = 0;
        if (y0 < 0) y0 = 0;
        if (x1 > w) x1 = w;
        if (y1 > h) y1 = h;

        for (int x = x0; x < x1; x++) {
            for (int y = y0; y < y1; y++) {
                int col = b.pixels[(x - xp) + (y - yp) * b.w];
                if (col == 0xFF00FF || col == 0xFFFF00FF) continue;
                pixels[x + y * w] = b.pixels[(x - xp) + (y - yp) * b.w];
            }
        }
    }

    public void fillRect(int xp, int yp, int w, int h, int col) {
        int x0 = xp;
        int y0 = yp;
        int x1 = xp + w;
        int y1 = yp + h;

        if (x0 < 0) x0 = 0;
        if (y0 < 0) y0 = 0;
        if (x1 > this.w) x1 = this.w;
        if (y1 > this.h) y1 = this.h;

        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                pixels[x + y * this.w] = col;
            }
        }

    }

    public void scaleDraw(Bitmap b, int xp, int yp, int scale) {
        int x0 = xp;
        int y0 = yp;
        int x1 = xp + b.w * scale;
        int y1 = yp + b.h * scale;

        if (x0 < 0) x0 = 0;
        if (y0 < 0) y0 = 0;
        if (x1 > w) x1 = w;
        if (y1 > h) y1 = h;

        for (int x = x0; x < x1; x++) {
            for (int y = y0; y < y1; y++) {
                int col = b.pixels[(x - xp) / scale + (y - yp) / scale * b.w];
                if (col == 0xFF00FF || col == 0xFFFF00FF) continue;
                pixels[x + y * w] = col;
            }
        }
    }

    public void setPixel(int xp, int yp, int col) {
        if (xp < 0 || yp < 0 || xp >= w || yp >= h) return; // Do not draw a pixel if it's out of bounds
        pixels[xp + yp * w] = col;
    }

}
