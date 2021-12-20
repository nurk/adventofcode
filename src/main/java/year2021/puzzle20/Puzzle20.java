package year2021.puzzle20;

import util.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Puzzle20 {
    public static void main(String[] args) throws IOException {
        //5097
        //17987
        List<String> input = new ArrayList<>(Utils.getInput("2021/input20.txt", (s) -> s));

        String decode = input.remove(0);
        input.remove(0);

        Board b = new Board(decode, input);
        b.enhance(50);
        System.out.println(b);

    }
}
