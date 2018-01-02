package year2017.puzzle23;

import java.math.BigInteger;

public class Puzzle23Part2 {
    public static void main(String[] args) {
        int b = 65 * 100 + 100000;
        int c = b + 17000;

        int h = 0;

        for (int i = b; i <= c; i=i+17) {
            if (!BigInteger.valueOf(i).isProbablePrime(100000)) {
                h++;
            }
        }

        System.out.println(h);
    }
}
