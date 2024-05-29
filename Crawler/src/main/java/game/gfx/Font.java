package game.gfx;

public class Font {

    private static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ      0123456789.,!?'\"-+=/\\%()<>[]:;  abcdefghijklmnopqrstuvwxyz      ";

    public static void draw(String text, Bitmap screen, int xp, int yp) { draw(text, screen, xp, yp, 1); }

    public static void draw(String text, Bitmap screen, int xp, int yp, int scale) {
        for (int i = 0; i < text.length(); i++) {
            int ix = chars.indexOf(text.charAt(i));
            int x = ix % 32;
            int y = ix / 32;
            if (ix < 0) return; // In case the index is a blank-space

            screen.scaleDraw(Art.font[x + y * 32], xp + i * 6 * scale, yp, scale);
        }
    }

}