package year2020.puzzle5;

import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Puzzle5 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        SortedSet<Seat> seats = new TreeSet<>();
        Files.readAllLines(Utils.getInputPath("2020/input5.txt")).forEach(
                line -> {
                    seats.add(new Seat(line));
                }
        );

        seats.last().print();

        List<Seat> seats2 = new ArrayList<>(seats);
        for (int i = 0; i < seats2.size() - 1; i++) {
            if (seats2.get(i).getSeatId() != (seats2.get(i + 1).getSeatId() - 1)) {
                System.out.println(seats2.get(i).getSeatId() + 1);
            }
        }
    }
}
