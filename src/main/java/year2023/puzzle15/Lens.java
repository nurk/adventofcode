package year2023.puzzle15;

import lombok.Getter;

@Getter
public class Lens {

    private final String id;
    private final int hash;
    private final int focalLength;
    private final String operation;

    public Lens(String input) {
        String[] split = input.split("((?<==)|(?==))|((?<=-)|(?=-))");
        this.id = split[0];
        this.hash = calculateHash(split[0]);
        this.operation = split[1];
        if (split.length == 3) {
            this.focalLength = Integer.parseInt(split[2]);
        } else {
            this.focalLength = -1;
        }
    }

    private int calculateHash(String input) {
        int hash = 0;
        for (char c : input.toCharArray()) {
            hash += c;
            hash *= 17;
            hash %= 256;
        }
        return hash;
    }

    @Override
    public String toString() {
        return "[" + id + " " + focalLength + "]";
    }
}
