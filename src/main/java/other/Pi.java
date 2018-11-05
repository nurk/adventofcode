package other;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Pi {

    private static Random random = new SecureRandom();

    public static void main(String[] args) {
        float coprime = 0;
        for (int i = 1; i < 100000; i++) {
            if (getRandomBigInteger().gcd(getRandomBigInteger()).equals(BigInteger.ONE)) {
                coprime++;
            }

            System.out.println(Math.sqrt(6 / (coprime / i)));

        }
    }

    private static BigInteger getRandomBigInteger() {
        return new BigInteger(50, random);
    }
}
