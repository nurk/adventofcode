package year2019.puzzle12;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;

public class Puzzle12B {
    // this is stupid :(
    public static void main(String[] args) {
//        Moon moon1 = new Moon(-1, 0, 2);
//        Moon moon2 = new Moon(2, -10, -7);
//        Moon moon3 = new Moon(4, -8, 8);
//        Moon moon4 = new Moon(3, 5, -1);

        Moon moon1 = new Moon(-4, -9, -3);
        Moon moon2 = new Moon(-13, -11, 0);
        Moon moon3 = new Moon(-17, -7, 15);
        Moon moon4 = new Moon(-16, 4, 2);

        String startPosition = moon1 + "" + moon2 + "" + moon3 + "" + moon4;
        String currentPosition = "";
        BigInteger count = BigInteger.ZERO;
        while (!StringUtils.equals(startPosition, currentPosition)) {
            moon1.applyGravity(moon2, moon3, moon4);
            moon2.applyGravity(moon1, moon3, moon4);
            moon3.applyGravity(moon2, moon1, moon4);
            moon4.applyGravity(moon2, moon3, moon1);
            moon1.applyVelocity();
            moon2.applyVelocity();
            moon3.applyVelocity();
            moon4.applyVelocity();
            currentPosition = moon1 + "" + moon2 + "" + moon3 + "" + moon4;
            count = count.add(BigInteger.ONE);
            System.out.println(count);
        }

        System.out.println("Result : " + count);
    }
}
