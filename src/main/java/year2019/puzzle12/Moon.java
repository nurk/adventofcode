package year2019.puzzle12;

public class Moon {
    private int x, y, z, vx, vy, vz;

    public Moon(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void applyVelocity() {
        applyVelocityX();
        applyVelocityY();
        applyVelocityZ();
    }

    public void applyVelocityZ() {
        this.z += vz;
    }

    public void applyVelocityY() {
        this.y += vy;
    }

    public void applyVelocityX() {
        this.x += vx;
    }

    public void applyGravityX(Moon moon1, Moon moon2, Moon moon3) {
        applyGravityX(moon1);
        applyGravityX(moon2);
        applyGravityX(moon3);
    }

    public void applyGravityY(Moon moon1, Moon moon2, Moon moon3) {
        applyGravityY(moon1);
        applyGravityY(moon2);
        applyGravityY(moon3);
    }

    public void applyGravityZ(Moon moon1, Moon moon2, Moon moon3) {
        applyGravityZ(moon1);
        applyGravityZ(moon2);
        applyGravityZ(moon3);
    }

    public void applyGravity(Moon moon1, Moon moon2, Moon moon3) {
        applyGravity(moon1);
        applyGravity(moon2);
        applyGravity(moon3);
    }

    private void applyGravity(Moon other) {
        applyGravityX(other);
        applyGravityY(other);
        applyGravityZ(other);
    }

    private void applyGravityZ(Moon other) {
        if (this.z < other.getZ()) {
            vz++;
        } else if (this.z > other.getZ()) {
            vz--;
        }
    }

    private void applyGravityY(Moon other) {
        if (this.y < other.getY()) {
            vy++;
        } else if (this.y > other.getY()) {
            vy--;
        }
    }

    private void applyGravityX(Moon other) {
        if (this.x < other.getX()) {
            vx++;
        } else if (this.x > other.getX()) {
            vx--;
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

    public Moon clone() {
        return new Moon(x, y, z);
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
