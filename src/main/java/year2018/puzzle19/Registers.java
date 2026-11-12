package year2018.puzzle19;

public class Registers {

    private int instructionPointerRegister;
    private int a, b, c, d, e, f;

    public Registers(int a, int b, int c, int d, int e, int f, int instructionPointerRegister) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.instructionPointerRegister = instructionPointerRegister;
    }

    public Registers(String input) {
        String[] parts = input.replaceAll("[\\[\\]]", "").split(", ");
        this.a = Integer.parseInt(parts[0]);
        this.b = Integer.parseInt(parts[1]);
        this.c = Integer.parseInt(parts[2]);
        this.d = Integer.parseInt(parts[3]);
        this.e = Integer.parseInt(parts[4]);
        this.f = Integer.parseInt(parts[5]);
    }

    public int getRegister(int index) {
        return switch (index) {
            case 0 -> a;
            case 1 -> b;
            case 2 -> c;
            case 3 -> d;
            case 4 -> e;
            case 5 -> f;
            default -> throw new IllegalArgumentException("Invalid register index: " + index);
        };
    }

    public void setRegister(int index, int value) {
        switch (index) {
            case 0 -> a = value;
            case 1 -> b = value;
            case 2 -> c = value;
            case 3 -> d = value;
            case 4 -> e = value;
            case 5 -> f = value;
            default -> throw new IllegalArgumentException("Invalid register index: " + index);
        }
    }

    public void setInstructionPointerRegister(int instructionPointerRegister) {
        this.instructionPointerRegister = instructionPointerRegister;
    }

    public int getInstructionPointer() {
        return getRegister(instructionPointerRegister);
    }

    public void incrementInstructionPointer() {
        setRegister(instructionPointerRegister, getInstructionPointer() + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Registers registers = (Registers) o;
        return a == registers.a && b == registers.b && c == registers.c && d == registers.d && e == registers.e && f == registers.f;
    }

    @Override
    public int hashCode() {
        int result = a;
        result = 31 * result + b;
        result = 31 * result + c;
        result = 31 * result + d;
        result = 31 * result + e;
        result = 31 * result + f;
        return result;
    }
}
