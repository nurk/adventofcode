package year2025.puzzle7;

import util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Part A: 1602
 * Part B: 135656430050438
 */
public class Puzzle7 {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2025/input7.txt"));

        BeamGrid beamGrid = new BeamGrid(input);

        System.out.println("Part A: " + beamGrid.splitBeams());

        TimeSplitBeamGrid timeSplitBeamGrid = new TimeSplitBeamGrid(input);
        System.out.println("Part B: " + timeSplitBeamGrid.splitBeams());
        System.out.println(timeSplitBeamGrid.altToString());
    }
}
