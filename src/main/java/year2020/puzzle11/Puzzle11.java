package year2020.puzzle11;

import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Puzzle11 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> plan = new ArrayList<>(Files.readAllLines(Utils.getInputPath("2020/input11.txt")));

        Seating seating = new Seating(plan);

        int currentOccupiedSeats = -1;

        while (seating.getNumberOfOccupiedSeats() != currentOccupiedSeats) {
            currentOccupiedSeats = seating.getNumberOfOccupiedSeats();
            seating.doRound();
        }
        System.out.println("PuzzleA: " + seating.getNumberOfOccupiedSeats());


        Seating2 seating2 = new Seating2(plan);

         currentOccupiedSeats = -1;

        while (seating2.getNumberOfOccupiedSeats() != currentOccupiedSeats) {
            currentOccupiedSeats = seating2.getNumberOfOccupiedSeats();
            seating2.doRound();
        }
        System.out.println("PuzzleB: " + seating2.getNumberOfOccupiedSeats());
    }
}
