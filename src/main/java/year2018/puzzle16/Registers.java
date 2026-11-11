package year2018.puzzle16;

public class Registers {

    private int a, b, c, d;

    public Registers(int a, int b, int c, int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Registers(String input) {
        String[] parts = input.replaceAll("[\\[\\]]", "").split(", ");
        this.a = Integer.parseInt(parts[0]);
        this.b = Integer.parseInt(parts[1]);
        this.c = Integer.parseInt(parts[2]);
        this.d = Integer.parseInt(parts[3]);
    }

    public int getRegister(int index) {
        return switch (index) {
            case 0 -> a;
            case 1 -> b;
            case 2 -> c;
            case 3 -> d;
            default -> throw new IllegalArgumentException("Invalid register index: " + index);
        };
    }

    public void setRegister(int index, int value) {
        switch (index) {
            case 0 -> a = value;
            case 1 -> b = value;
            case 2 -> c = value;
            case 3 -> d = value;
            default -> throw new IllegalArgumentException("Invalid register index: " + index);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Registers registers = (Registers) o;
        return a == registers.a && b == registers.b && c == registers.c && d == registers.d;
    }

    @Override
    public int hashCode() {
        int result = a;
        result = 31 * result + b;
        result = 31 * result + c;
        result = 31 * result + d;
        return result;
    }
}
