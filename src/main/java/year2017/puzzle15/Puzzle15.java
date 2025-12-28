package year2017.puzzle15;

import org.apache.commons.lang3.StringUtils;

/**
 * Part A: 609
 * Part B: 253
 */
public class Puzzle15 {
    public static void main(String[] args) {
        boolean realInput = true;

        long generatorA = 65;
        long generatorB = 8921;

        if (realInput) {
            generatorA = 883;
            generatorB = 879;
        }


        int matchesPartA = 0;
        long a = generatorA;
        long b = generatorB;
        for (int i = 0; i < 40_000_000; i++) {
            a = (a * 16807) % 2147483647;
            b = (b * 48271) % 2147483647;

            String aString = StringUtils.leftPad(Long.toBinaryString(a), 17, "O");
            String bString = StringUtils.leftPad(Long.toBinaryString(b), 17, "O");
            if (aString.substring(aString.length() - 16).equals(bString.substring(bString.length() - 16))) {
                matchesPartA++;
            }
        }
        System.out.println("Part A: " + matchesPartA);

        int matchesPartB = 0;
        a = generatorA;
        b = generatorB;
        for (int i = 0; i < 5_000_000; i++) {
            do {
                a = (a * 16807) % 2147483647;
            } while (a % 4 != 0);

            do {
                b = (b * 48271) % 2147483647;
            } while (b % 8 != 0);

            String aString = StringUtils.leftPad(Long.toBinaryString(a), 17, "O");
            String bString = StringUtils.leftPad(Long.toBinaryString(b), 17, "O");
            if (aString.substring(aString.length() - 16).equals(bString.substring(bString.length() - 16))) {
                matchesPartB++;
            }
        }

        System.out.println("Part B: " + matchesPartB);
    }
}
