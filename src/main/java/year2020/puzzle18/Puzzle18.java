package year2020.puzzle18;

import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Puzzle18 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> input = new ArrayList<>(Files.readAllLines(Utils.getInputPath("2020/input18.txt")));

        Long reduce = input.stream().map(Formula::new).map(Formula::calculate).reduce(0L, Long::sum);
        System.out.println(reduce);

        Long reducePart2 = input.stream().map(FormulaPart2::new).map(FormulaPart2::calculate).reduce(0L, Long::sum);
        System.out.println(reducePart2);

//        FormulaPart2 f = new FormulaPart2("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))");
//        f.calculate();
//        System.out.println(f.getResult());
    }
}
