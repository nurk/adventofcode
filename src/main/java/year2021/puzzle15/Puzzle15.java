package year2021.puzzle15;

import util.Utils;

import java.util.ArrayList;
import java.util.List;

public class Puzzle15 {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2021/input15.txt", (s) -> s));

        Board b = new Board(input, 5);
        //System.out.println(b);

        System.out.println(b.shortestPath(b.getNodeAt(0, 0).orElseThrow(),
                b.getNodeAt(b.getRows() - 1, b.getCols() - 1).orElseThrow()));
    }
}
