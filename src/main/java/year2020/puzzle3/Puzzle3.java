package year2020.puzzle3;

import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Puzzle3 {
    public static void main(String[] args) throws URISyntaxException, IOException {


        List<String> lines = new ArrayList<>();
        Files.readAllLines(Utils.getInputPath("2020/input3.txt")).forEach(
                line -> {
                    lines.add(line);
                }
        );

        Map map = new Map(lines.size(), lines.get(0).length());

        for (int i = 0; i < lines.size(); i++) {
            map.addRow(i, lines.get(i));
        }

        System.out.println(solve(3, 1, map));

        System.out.println(solve(1, 1, map) * solve(3, 1, map) * solve(5, 1, map) * solve(7, 1, map) * solve(1, 2, map));
    }

    public static long solve(int right, int down, Map map) {
        map.reset();
        long trees = 0;
        while (true) {
            map.move(right, down);
            if (map.isFinish()) {
                return trees;
            }
            if (map.isTree()) {
                trees++;
            }
        }
    }
}
