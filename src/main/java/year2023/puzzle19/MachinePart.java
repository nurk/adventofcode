package year2023.puzzle19;

import lombok.Getter;

public class MachinePart {
    @Getter
    private final int x, m, a, s;

    public MachinePart(String input) {
        String stripped = input.replace("{", "").replace("}", "");
        String[] parts = stripped.split(",");
        this.x = Integer.parseInt(parts[0].split("=")[1]);
        this.m = Integer.parseInt(parts[1].split("=")[1]);
        this.a = Integer.parseInt(parts[2].split("=")[1]);
        this.s = Integer.parseInt(parts[3].split("=")[1]);
    }

    public MachinePart(int x, int m, int a, int s) {
        this.x = x;
        this.m = m;
        this.a = a;
        this.s = s;
    }

    public long getRating() {
        return x + m + a + s;
    }

    @Override
    public String toString() {
        return "MachinePart{" +
                "x=" + x +
                ", m=" + m +
                ", a=" + a +
                ", s=" + s +
                '}';
    }
}
