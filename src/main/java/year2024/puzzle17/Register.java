package year2024.puzzle17;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Register {

    @Getter
    @Setter
    private long a, b, c;
    private final List<Long> output = new ArrayList<>();

    public void addOutput(Long value) {
        output.add(value);
    }

    @Override
    public String toString() {
        return "Register{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", output=" + output.stream().map(Object::toString).collect(Collectors.joining(",")) +
                '}';
    }

    public String getValue() {
        return output.stream().map(Object::toString).collect(Collectors.joining(","));
    }

    public Register clone() {
        Register register = new Register();
        register.a = this.a;
        register.b = this.b;
        register.c = this.c;

        return register;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Register register = (Register) o;
        return a == register.a && b == register.b && c == register.c;
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(a);
        result = 31 * result + Long.hashCode(b);
        result = 31 * result + Long.hashCode(c);
        return result;
    }
}
