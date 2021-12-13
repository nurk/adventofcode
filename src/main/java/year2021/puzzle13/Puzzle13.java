package year2021.puzzle13;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.List;

public class Puzzle13 {
    public static void main(String[] args) {
        List<String> input = Utils.getInput("2021/input13.txt", (s) -> s);
        List<String> coordinates = input.subList(0, input.indexOf(""));
        List<String> folds = input.subList(input.indexOf("") + 1, input.size());

        partA(coordinates, folds);
        partB(coordinates, folds);
    }

    private static void partA(List<String> coordinates, List<String> folds) {
        Board b = new Board(coordinates);
        doFold(folds.get(0), b);
        System.out.println(b.countHashes());
    }

    private static void partB(List<String> coordinates, List<String> folds) {
        Board b = new Board(coordinates);
        folds.forEach(fold -> doFold(fold, b));
        System.out.println(b);
    }

    private static void doFold(String fold, Board b) {
        String[] split = StringUtils.remove(fold, "fold along ").split("=");
        int val = Integer.parseInt(split[1]);
        if ("y".equals(split[0])) {
            b.foldRow(val);
        } else {
            b.foldCol(val);
        }
    }
}
