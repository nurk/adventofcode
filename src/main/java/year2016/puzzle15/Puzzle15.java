package year2016.puzzle15;

import java.math.BigInteger;

/**
 * https://reference.wolfram.com/language/ref/ChineseRemainder.html
 * Part A: 148737
 * Part B: 2353212
 */
public class Puzzle15 {

    static void main() {
        /**
         * Disc #1 has 5 positions; at time=0, it is at position 2.
         * Disc #2 has 13 positions; at time=0, it is at position 7.
         * Disc #3 has 17 positions; at time=0, it is at position 10.
         * Disc #4 has 3 positions; at time=0, it is at position 2.
         * Disc #5 has 19 positions; at time=0, it is at position 9.
         * Disc #6 has 7 positions; at time=0, it is at position 0.
         */
        System.out.println("Part A: " + chineseRemainderTheorem(new long[]{2, 7, 10, 2, 9, 0},
                new long[]{5, 13, 17, 3, 19, 7}));

        //but a new disc with 11 positions and starting at position 0 has
        System.out.println("Part B: " + chineseRemainderTheorem(new long[]{2, 7, 10, 2, 9, 0, 0},
                new long[]{5, 13, 17, 3, 19, 7, 11}));
    }

    public static BigInteger chineseRemainderTheorem(long[] initials, long[] sizes) {
        BigInteger totalProduct = BigInteger.ONE;
        for (long size : sizes) {
            totalProduct = totalProduct.multiply(BigInteger.valueOf(size));
        }

        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < sizes.length; i++) {
            // Equivalent to Mathematica's: -initialValue - (i + 1)
            long remainder = -initials[i] - (i + 1);

            BigInteger m_i = BigInteger.valueOf(sizes[i]);
            BigInteger p_i = totalProduct.divide(m_i);

            // Calculate modular inverse: (p_i^-1) mod m_i
            BigInteger inv = p_i.modInverse(m_i);

            // Result += remainder * partialProduct * modularInverse
            BigInteger term = BigInteger.valueOf(remainder)
                    .multiply(p_i)
                    .multiply(inv);
            result = result.add(term);
        }

        // Return the smallest positive solution
        return result.mod(totalProduct);
    }

    private static int chineseRemainder(int[] num, int[] rem) {
        int size = num.length;

        int x = 1;

        // As per the Chinese remainder theorem,
        // this loop will always break.
        while (true) {
            // Check if remainder of x % num[j] is
            // rem[j] or not (for all j from 0 to k-1)
            int j;
            for (j = 0; j < size; j++) {
                if (x % num[j] != rem[j]) {
                    break;
                }
            }

            // If all remainders matched, we found x
            if (j == size) {
                return x;
            }

            // Else try next number
            x++;
        }

    }
}
