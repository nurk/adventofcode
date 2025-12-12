package year2025.puzzle12;

import util.Utils;

import java.util.List;

/**
 * Part A: 579
 */
public class Puzzle12 {
    public static void main(String[] args) {
        List<List<String>> lists = Utils.splitOnBlankLine(Utils.getInput("2025/input12.txt"));

        List<Tree> trees = lists.removeLast().stream().map(Tree::new).toList();
        List<Present> presents = lists.stream().map(Present::new).toList();

        // this should not have worked, but it did
        // the test input does not work with this approach though
        long partAVeryNaive = trees.stream()
                .filter(tree -> tree.canFitAllPresentsVerifyNaive(presents))
                .count();

        System.out.println("Part A: " + partAVeryNaive);
    }
}
