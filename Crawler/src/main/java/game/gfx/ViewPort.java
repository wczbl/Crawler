package game.gfx;

import game.level.Level;
import game.level.tile.Tile;
import game.math.Vec2;
import game.math.Vec3;

import java.util.Arrays;

public class ViewPort extends Bitmap {

    private double[] zBuffer;
    private static final double MAX_Z_BUFFER = 100000.0;
    private int fogColor = 0x000000;

    public ViewPort(int w, int h) {
        super(w, h);
        zBuffer = new double[w * h];
    }


    public void updateZBuffer() { Arrays.fill(zBuffer, MAX_Z_BUFFER); }

    public void renderFloor(Camera cam, Level level) {
        for (int yp = 0; yp < h; yp++) {
            double yd = (yp + 0.5 - cam.yCenter) / cam.fov;
            boolean floor = true;
            double zd = (4 - cam.pos.y) / yd;
            if (yd < 0) {
                floor = false;
                zd = -(4 - cam.pos.y) / yd;
            }

            for (int xp = 0; xp < w; xp++) {
                if (zBuffer[xp + yp * w] <= zd) continue;

                double xd = (cam.xCenter - xp) / cam.fov;
                xd *= zd;

                double xx = xd * cam.rCos + zd * cam.rSin + (cam.pos.x + 0.5) * 8.0;
                double yy = zd * cam.rCos - xd * cam.rSin + (cam.pos.z + 0.5) * 8.0;

                int xTex = (int) (xx * 2.0);
                int yTex = (int) (yy * 2.0);
                int sw = Art.sprites[0].w;

                if (floor) {
                    int xTile = xTex >> 4;
                    int yTile = yTex >> 4;
                    Tile tile = level.getTile(xTile, yTile);

                    zBuffer[xp + yp * w] = zd;

                    pixels[xp + yp * w] = Art.tiles[tile.getTexture(level, xTile, yTile)].pixels[(xTex & sw - 1) + (yTex & sw - 1) * sw];
                } else {
                    if (zBuffer[xp + yp * w] != MAX_Z_BUFFER) continue;
                    zBuffer[xp + yp * w] = -1.0;
                }
            }
        }
    }

    public void renderSprite(Vec3 pos, Camera cam, Bitmap src) { renderSprite(pos, 1.0, cam, Vec2.zero, src); }

    public void renderSprite(Vec3 pos, double r, Camera cam, Bitmap src) { renderSprite(pos, r, cam, Vec2.zero, src); }

    public void renderSprite(Vec3 pos, double r, Camera cam, Vec2 spriteOffset, Bitmap src) {
        Vec3 vc = cam.toCam(pos, 2.0);
        Vec2 s = cam.project(vc);

        if (vc.z < cam.nearClip) return;
        if (vc.z > cam.farClip) return;

        Vec2 offset = new Vec2(r * cam.fov / vc.z, r * cam.fov / vc.z);
        Vec2 s0 = s.sub(offset);
        Vec2 s1 = s.add(offset);

        int xp0 = (int) Math.ceil(s0.x);
        int xp1 = (int) Math.ceil(s1.x);
        int yp0 = (int) Math.ceil(s0.y);
        int yp1 = (int) Math.ceil(s1.y);

        if (xp0 > xp1) return;

        if (xp0 < 0) xp0 = 0;
        if (yp0 < 0) yp0 = 0;
        if (xp1 > w) xp1 = w;
        if (yp1 > h) yp1 = h;

        double iw = 1.0 / (s1.x - s0.x);
        double ih = 1.0 / (s1.y - s0.y);
        int w = src.w;

        for (int yp = yp0; yp < yp1; yp++) {
            int yTex = (int) (((yp - s0.y) * ih + spriteOffset.y) * w) % w;
            for (int xp = xp0; xp < xp1; xp++) {
                int xTex = (int) (((xp - s0.x) * iw + spriteOffset.x) * w) % w;

                int col = src.pixels[(xTex & w - 1) + (yTex & w - 1) * w];
                if (col == 0xFF00FF || col == 0xFFFF00FF) continue;

                if (zBuffer[xp + yp * this.w] > vc.z * 4.0) {
                    zBuffer[xp + yp * this.w] = vc.z * 4.0;
                    setPixel(xp, yp, col);
                }
            }
        }
    }

    public void renderSky(Camera cam) {
        for (int i = 0; i < zBuffer.length; i++) {
            double zl = zBuffer[i];
            if (zl < 0) {
                int xTex = (int) Math.floor((i % w) + cam.xRot / Math.PI * 2 * Art.sky.w) & (Art.sky.w - 1);
                int yTex = i / w & (Art.sky.h - 1);
                int col = Art.sky.pixels[xTex + yTex * Art.sky.w];
                zBuffer[i] = 20;
                if (col == 0xFF00FF || col == 0xFFFF00FF) continue;
                pixels[i] = col;
            }
        }
    }

    public void post() {
        for (int i = 0; i < zBuffer.length; ++i) {
            int x = i % w;
            int y = i / w;
            double zl = zBuffer[i];
            if (zl < 0) continue;
            int col = pixels[i];
            int r = col >> 16 & 0xFF;
            int g = col >> 8 & 0xFF;
            int b = col & 0xFF;
            int fr = fogColor >> 16 & 0x7F;
            int fg = fogColor >> 8 & 0x7F;
            int fb = fogColor & 0x7F;

            double xx = ((i % w) / (double) w * 2.0 - 1.0) * 0.5;
            int br = (int)(300.0 - zBuffer[i] * 6.0 * (xx * xx * 2.0 + 1.0));

            br = br + (x - y * 14 & 3) >> 4 << 4;
            if (br < 0) br = 0;
            if (br > 255) br = 255;

            r = (int) ((r * br) / 255.0 + (1.0 - br / 255.0) * fr);
            g = (int) ((g * br) / 255.0 + (1.0 - br / 255.0) * fg);
            b = (int) ((b * br) / 255.0 + (1.0 - br / 255.0) * fb);

            pixels[i] = r << 16 | g << 8 | b;
        }
    }

}