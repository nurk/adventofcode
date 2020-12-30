package year2020.puzzle17;

import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Puzzle17 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> input = new ArrayList<>(Files.readAllLines(Utils.getInputPath("2020/input17.txt")));

        Board3D board = new Board3D(input);

        board.doCycle();
        board.doCycle();
        board.doCycle();
        board.doCycle();
        board.doCycle();
        board.doCycle();

        System.out.println(board.getActive());

        Board4D board4D = new Board4D(input);

        board4D.doCycle();
        board4D.doCycle();
        board4D.doCycle();
        board4D.doCycle();
        board4D.doCycle();
        board4D.doCycle();

        System.out.println(board4D.getActive());
    }
}
