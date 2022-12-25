package year2022.puzzle24;

import util.Utils;

import java.util.*;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class Puzzle24 {

    static int rows = 0;
    static int cols = 0;
    static Position startPosition = new Position(0, 1, 0);
    static Position endPosition;
    static Map<Integer, List<String>[][]> blizzardsAtMinute = new HashMap<>();

    //PART A: 245
    //PART B: 798
    public static void main(String[] args) {
        List<String> input = Utils.getInput("2022/input24.txt");
        rows = input.size();
        cols = input.get(0).length();
        endPosition = new Position(rows - 1, cols - 2, 0);

        List<String>[][] rootBlizzards = new ArrayList[rows][cols];
        for (int i = 0; i < rows; i++) {
            String[] line = input.get(i).split("");
            for (int j = 0; j < line.length; j++) {
                List<String> item = new ArrayList<>();
                if (!line[j].equals(".")) {
                    item.add(line[j]);
                }
                rootBlizzards[i][j] = item;
            }
        }
        blizzardsAtMinute.put(0, rootBlizzards);

        int toEnd = shortestPath(startPosition, endPosition);
        System.out.println("Part A: " + toEnd);
        int backToStart = toEnd + shortestPath(new Position(endPosition.row, endPosition.col, toEnd), startPosition);
        System.out.println("Go back to start to pick up the snacks: " + backToStart);
        System.out.println("Go back to end for part B: " + (backToStart + shortestPath(new Position(startPosition.row,
                        startPosition.col,
                        backToStart),
                endPosition)));
    }

    public static int shortestPath(Position start, Position end) {
        //This is LIFO
        Deque<Position> pq = new ArrayDeque<>();
        pq.addLast(start);

        Map<Position, Integer> costSoFar = new HashMap<>();

        costSoFar.put(start, 0);

        while (!pq.isEmpty()) {
            Position current = pq.pop();

            if (current.row == end.row && current.col == end.col) {
                break;
            }

            int currentCost = costSoFar.get(current);
            int newCost = currentCost + 1;

            List<String>[][] blizzards = getBlizzardsAt(current.minute + 1);
            //can stay put?
            if (blizzards[current.row][current.col].isEmpty()) {
                Position newPos = new Position(current.row, current.col, current.minute + 1);
                if (!costSoFar.containsKey(newPos) || newCost < costSoFar.get(newPos)) {
                    costSoFar.put(newPos, newCost);
                    pq.addLast(newPos);
                }
            }
            for (Movement movement : Movement.values()) {
                //can move in a direction
                Position newPos = new Position(movement.rowFunction.apply(current.row),
                        movement.colFunction.apply(current.col),
                        current.minute + 1);
                if (blizzards[newPos.row][newPos.col].isEmpty()) {
                    if (!costSoFar.containsKey(newPos) || newCost < costSoFar.get(newPos)) {
                        costSoFar.put(newPos, newCost);
                        pq.addLast(newPos);
                    }
                }
            }
        }

        return costSoFar.entrySet().stream()
                .filter(entry -> entry.getKey().row == end.row && entry.getKey().col == end.col)
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(Integer.MAX_VALUE);
    }

    static List<String>[][] getBlizzardsAt(int minute) {
        if (blizzardsAtMinute.containsKey(minute)) {
            return blizzardsAtMinute.get(minute);
        }
        List<String>[][] tempBoard = new ArrayList[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                tempBoard[i][j] = new ArrayList<>();
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                List<String> currentStrings = blizzardsAtMinute.get(minute - 1)[i][j];
                if (currentStrings.contains("#")) {
                    tempBoard[i][j].add("#");
                } else {
                    for (String currentString : currentStrings) {
                        Movement m = Movement.fromSymbol(currentString);
                        int newRow = i;
                        int newCol = j;
                        do {
                            newRow = m.rowFunction.apply(newRow);
                            newCol = m.colFunction.apply(newCol);
                        } while (blizzardsAtMinute.get(minute - 1)[newRow][newCol].contains("#"));
                        tempBoard[newRow][newCol].add(currentString);
                    }
                }
            }
        }
        blizzardsAtMinute.put(minute, tempBoard);
        return tempBoard;
    }

    record Position(int row, int col, int minute) {
    }

    enum Movement {
        N(i -> Math.floorMod(i - 1, rows), j -> j, "^"),
        S(i -> Math.floorMod(i + 1, rows), j -> j, "v"),
        E(i -> i, j -> Math.floorMod(j + 1, cols), ">"),
        W(i -> i, j -> Math.floorMod(j - 1, cols), "<");


        final Function<Integer, Integer> rowFunction;
        final Function<Integer, Integer> colFunction;
        final String symbol;

        Movement(Function<Integer, Integer> rowFunction, Function<Integer, Integer> colFunction, String symbol) {
            this.rowFunction = rowFunction;
            this.colFunction = colFunction;
            this.symbol = symbol;
        }

        static Movement fromSymbol(String in) {
            return Arrays.stream(Movement.values())
                    .filter(m -> m.symbol.equals(in))
                    .findFirst()
                    .orElseThrow();
        }
    }
}

