package year2019.puzzle12;

public class Moon {
    private int x, y, z, vx, vy, vz;

    public Moon(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void applyVelocity() {
        this.x += vx;
        this.y += vy;
        this.z += vz;
    }

    public void applyGravity(Moon moon1, Moon moon2, Moon moon3) {
        applyGravity(moon1);
        applyGravity(moon2);
        applyGravity(moon3);
    }

    private void applyGravity(Moon other) {
        if (this.x < other.getX()) {
            vx++;
        } else if (this.x > other.getX()) {
            vx--;
        }

        if (this.y < other.getY()) {
            vy++;
        } else if (this.y > other.getY()) {
            vy--;
        }

        if (this.z < other.getZ()) {
            vz++;
        } else if (this.z > other.getZ()) {
            vz--;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getEnergy() {
        return (Math.abs(x) + Math.abs(y) + Math.abs(z)) * (Math.abs(vx) + Math.abs(vy) + Math.abs(vz));
    }

    @Override
    public String toString() {
        return "Moon{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", vx=" + vx +
                ", vy=" + vy +
                ", vz=" + vz +
                '}';
    }
}
