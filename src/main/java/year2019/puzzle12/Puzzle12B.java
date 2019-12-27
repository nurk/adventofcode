package year2019.puzzle12;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;

public class Puzzle12B {
    // this is stupid :(
    // in the end I stole this from https://github.com/rlmhermans/advent2019/tree/master/src/Day12b
    public static void main(String[] args) {
//        Moon moon1 = new Moon(-1, 0, 2);
//        Moon moon2 = new Moon(2, -10, -7);
//        Moon moon3 = new Moon(4, -8, 8);
//        Moon moon4 = new Moon(3, 5, -1);

        Moon moon1 = new Moon(-4, -9, -3);
        Moon moon2 = new Moon(-13, -11, 0);
        Moon moon3 = new Moon(-17, -7, 15);
        Moon moon4 = new Moon(-16, 4, 2);

        BigInteger xSteps = getXSteps(moon1.clone(), moon2.clone(), moon3.clone(), moon4.clone());
        BigInteger ySteps = getYSteps(moon1.clone(), moon2.clone(), moon3.clone(), moon4.clone());
        BigInteger zSteps = getZSteps(moon1.clone(), moon2.clone(), moon3.clone(), moon4.clone());

        System.out.println(LCM(LCM(xSteps, ySteps), zSteps));
    }

    public static BigInteger getXSteps(Moon moon1, Moon moon2, Moon moon3, Moon moon4) {
        String startPosition = moon1 + "" + moon2 + "" + moon3 + "" + moon4;
        String currentPosition = "";
        BigInteger count = BigInteger.ZERO;
        while (!StringUtils.equals(startPosition, currentPosition)) {
            moon1.applyGravityX(moon2, moon3, moon4);
            moon2.applyGravityX(moon1, moon3, moon4);
            moon3.applyGravityX(moon2, moon1, moon4);
            moon4.applyGravityX(moon2, moon3, moon1);
            moon1.applyVelocityX();
            moon2.applyVelocityX();
            moon3.applyVelocityX();
            moon4.applyVelocityX();
            currentPosition = moon1 + "" + moon2 + "" + moon3 + "" + moon4;
            count = count.add(BigInteger.ONE);
        }

        System.out.println("Result X : " + count);
        return count;
    }

    public static BigInteger getYSteps(Moon moon1, Moon moon2, Moon moon3, Moon moon4) {
        String startPosition = moon1 + "" + moon2 + "" + moon3 + "" + moon4;
        String currentPosition = "";
        BigInteger count = BigInteger.ZERO;
        while (!StringUtils.equals(startPosition, currentPosition)) {
            moon1.applyGravityY(moon2, moon3, moon4);
            moon2.applyGravityY(moon1, moon3, moon4);
            moon3.applyGravityY(moon2, moon1, moon4);
            moon4.applyGravityY(moon2, moon3, moon1);
            moon1.applyVelocityY();
            moon2.applyVelocityY();
            moon3.applyVelocityY();
            moon4.applyVelocityY();
            currentPosition = moon1 + "" + moon2 + "" + moon3 + "" + moon4;
            count = count.add(BigInteger.ONE);
        }

        System.out.println("Result Y : " + count);
        return count;
    }

    public static BigInteger getZSteps(Moon moon1, Moon moon2, Moon moon3, Moon moon4) {
        String startPosition = moon1 + "" + moon2 + "" + moon3 + "" + moon4;
        String currentPosition = "";
        BigInteger count = BigInteger.ZERO;
        while (!StringUtils.equals(startPosition, currentPosition)) {
            moon1.applyGravityZ(moon2, moon3, moon4);
            moon2.applyGravityZ(moon1, moon3, moon4);
            moon3.applyGravityZ(moon2, moon1, moon4);
            moon4.applyGravityZ(moon2, moon3, moon1);
            moon1.applyVelocityZ();
            moon2.applyVelocityZ();
            moon3.applyVelocityZ();
            moon4.applyVelocityZ();
            currentPosition = moon1 + "" + moon2 + "" + moon3 + "" + moon4;
            count = count.add(BigInteger.ONE);
        }

        System.out.println("Result Z : " + count);
        return count;
    }

    public static BigInteger LCM(BigInteger a, BigInteger b) {
        return a.multiply(b).divide(GCD(a, b));
    }

    public static BigInteger GCD(BigInteger a, BigInteger b) {
        if (b.intValue() == 0) {
            return a;
        } else {
            return (GCD(b, a.mod(b)));
        }
    }
}
