package game.math;

public class Vec3 {

    public static final Vec3 zero = new Vec3(0, 0, 0);

    public double x;
    public double y;
    public double z;

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3 copy() { return new Vec3(x, y, z); }

    public Vec3 rotX(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double xx = x;
        double yy = y * cos + z * sin;
        double zz = z * cos - y * sin;
        return new Vec3(xx, yy, zz);
    }

    public Vec3 rotY(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double xx = x * cos + z * sin;
        double yy = y;
        double zz = z * cos - x * sin;
        return new Vec3(xx, yy, zz);
    }

    public Vec3 rotZ(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double xx = x * cos - y * sin;
        double yy = y * cos + z * sin;
        double zz = z;
        return new Vec3(xx, yy, zz);
    }

    public Vec3 rot(Vec3 v) { return rotZ(v.z).rotY(v.y).rotX(v.x); }
    public Vec3 add(Vec3 v) { return new Vec3(x + v.x, y + v.y, z + v.z); }
    public Vec3 sub(Vec3 v) { return new Vec3(x - v.x, y - v.y, z - v.z); }
    public Vec3 mul(Vec3 v) { return new Vec3(x * v.x, y * v.y, z * v.z); }
    public Vec3 scale(double s) { return new Vec3(x * s, y * s, z * s); }
    public double dot(Vec3 v) { return x * v.x + y * v.y; }
    public double length() { return Math.sqrt(x * x + y * y + z * z); }
    public double lengthSqr() { return x * x + y * y + z * z; }

    public Vec3 cross(Vec3 v) {
        // y z z y
        // z x x z
        // x y y x
        double xx = y * v.z - z * v.y;
        double yy = z * v.x - x * v.z;
        double zz = x * v.y - y * v.x;

        return new Vec3(xx, yy, zz);
    }

    public Vec3 normalize() {
        double l = length();
        return new Vec3(
                l > 0.0001 ? x / l : x,
                l > 0.0001 ? y / l : y,
                l > 0.0001 ? z / l : z
        );
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void clear() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vec3 lerp(Vec3 v, double p) {
        double xx = x + (v.x - x) * p;
        double yy = y + (v.y - y) * p;
        double zz = z + (v.z - z) * p;
        return new Vec3(xx, yy, zz);
    }

}