package year2018.puzzle8;

import util.Utils;

import java.util.*;

/**
 * Part A: 48496
 */
public class Puzzle8 {
    static void main() {
        List<Integer> input = Utils.getInput("2018/input8.txt", (s) -> s.split(" "))
                .stream()
                .flatMap(Arrays::stream)
                .map(Integer::parseInt)
                .toList();

        List<Integer> metaDataList = new ArrayList<>();

        Deque<Integer> numberOfNodes = new LinkedList<>();
        Deque<Integer> numberOfMetaData = new LinkedList<>();

        String nextAction = "numberOfNodes";

        /**
         * 2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2
         * A----------------------------------
         *     B----------- C-----------
         *                      D-----
         */
        for (Integer i : input) {
            switch (nextAction) {
                case "numberOfNodes": {
                    numberOfNodes.push(i);
                    nextAction = "numberOfMetaData";
                    break;
                }
                case "numberOfMetaData": {
                    numberOfMetaData.push(i);
                    if (!numberOfNodes.isEmpty()) {
                        Integer nodes = numberOfNodes.poll();
                        nodes--;
                        if (nodes >= 0) {
                            numberOfNodes.push(nodes);
                            nextAction = "numberOfNodes";
                        } else {
                            nextAction = "readMetaData";
                        }
                    }
                    break;
                }
                case "readMetaData": {
                    metaDataList.add(i);
                    if (!numberOfMetaData.isEmpty()) {
                        Integer metaData = numberOfMetaData.poll();
                        metaData--;
                        if (metaData > 0) {
                            nextAction = "readMetaData";
                            numberOfMetaData.push(metaData);
                        } else {
                            if (!numberOfNodes.isEmpty()) {
                                Integer nodes = numberOfNodes.poll();
                                nodes--;
                                if (nodes >= 0) {
                                    numberOfNodes.push(nodes);
                                    nextAction = "numberOfNodes";
                                } else {
                                    nextAction = "readMetaData";
                                }
                            }
                        }
                    }
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unknown action: " + nextAction);
                }
            }
        }

        System.out.println("Part A: " + metaDataList.stream().reduce(0, Integer::sum));
    }
}
