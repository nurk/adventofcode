package year2016.puzzle22;

import util.Utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Part A: 952
 */
public class Puzzle22 {
    static void main() {
        List<Node> nodes = Utils.getInput("2016/input22.txt", Node::new);

        Set<ViablePair> viablePairs = new HashSet<>();

        for (Node nodeA : nodes) {
            for (Node nodeB : nodes) {
                if (nodeA.equals(nodeB)) {
                    continue;
                }
                if (nodeA.getUsed() == 0) {
                    continue;
                }
                if (nodeA.getUsed() <= nodeB.getAvailable()) {
                    viablePairs.add(new ViablePair(nodeA, nodeB));
                }
            }
        }

        System.out.println("Part A: " + viablePairs.size());
    }
}
