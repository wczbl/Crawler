package game.math;

public class Vec2 {

    public static final Vec2 zero = new Vec2(0, 0);

    public double x;
    public double y;

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2 add(Vec2 v) { return new Vec2(x + v.x, y + v.y); }
    public Vec2 sub(Vec2 v) { return new Vec2(x - v.x, y - v.y); }

}