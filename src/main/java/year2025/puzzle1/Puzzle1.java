package year2025.puzzle1;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Part A: 1071
 * Part B: 6700
 */
public class Puzzle1 {

    public static void main(String[] args) {
        int currentPosition = 50;
        int numberOfTimesZeroPartA = 0;
        int numberOfTimesZeroPartB = 0;

        List<String> lines = new ArrayList<>(Utils.getInput("2025/input1.txt", (s) -> s));

        for (String line : lines) {
            String rotation = StringUtils.substring(line, 0, 1);
            int clicks = Integer.parseInt(StringUtils.substring(line, 1));

            if (Strings.CS.equals(rotation, "L") && currentPosition != 0) {
                numberOfTimesZeroPartB += Math.floorDiv((100 - currentPosition + clicks), 100);
            } else {
                numberOfTimesZeroPartB += Math.floorDiv((currentPosition + clicks), 100);
            }

            if (Strings.CS.equals(rotation, "L")) {
                currentPosition = (currentPosition - clicks) % 100;
                if (currentPosition < 0) {
                    currentPosition = currentPosition + 100;
                }
            } else {
                currentPosition = (currentPosition + clicks) % 100;
            }

            if (currentPosition == 0) {
                numberOfTimesZeroPartA++;
            }
        }

        System.out.println("Part A: " + numberOfTimesZeroPartA);
        System.out.println("Part B: " + numberOfTimesZeroPartB);
    }
}
