package year2022.puzzle24;

import util.Utils;

import java.util.*;
import java.util.function.Function;

public class Puzzle24 {

    static List<String>[][] rootBoard;
    static int rows = 0;
    static int cols = 0;
    static Position startPosition = new Position(0, 1, 0);
    static Position endPosition;
    static Map<Integer, List<String>[][]> boards = new HashMap<>();

    //PART A: 245
    public static void main(String[] args) {
        List<String> input = Utils.getInput("2022/input24.txt");
        rows = input.size();
        cols = input.get(0).length();
        endPosition = new Position(rows - 1, cols - 2, 0);

        rootBoard = new ArrayList[rows][cols];
        for (int i = 0; i < rows; i++) {
            String[] line = input.get(i).split("");
            for (int j = 0; j < line.length; j++) {
                List<String> item = new ArrayList<>();
                if (!line[j].equals(".")) {
                    item.add(line[j]);
                }
                rootBoard[i][j] = item;
            }
        }
        boards.put(0, rootBoard);
        System.out.println(shortestPath(startPosition, endPosition));
    }

    public static long shortestPath(Position start, Position end) {
        PriorityQueue<Position> pq = new PriorityQueue<>(rows * cols);
        pq.add(start);

        Map<Position, Integer> costSoFar = new HashMap<>();

        costSoFar.put(start, 0);

        while (!pq.isEmpty()) {
            Position current = pq.remove();

            if (current.row == end.row && current.col == end.col) {
                break;
            }

            int currentCost = current.pathCost;

            List<String>[][] thisBoard = simulateBlizzard(currentCost + 1);
            //can stay put
            if (thisBoard[current.row][current.col].isEmpty()) {
                int newCost = currentCost + 1;
                Position newPos = new Position(current.row, current.col, current.blizzards + 1);
                if (!costSoFar.containsKey(newPos) || newCost < costSoFar.get(newPos)) {
                    newPos.pathCost = newCost;
                    costSoFar.put(newPos, newCost);
                    pq.add(newPos);
                }
            }
            for (Movement movement : Movement.values()) {
                int newCost = currentCost + 1;
                Position newPos = new Position(movement.rowFunction.apply(current.row),
                        movement.colFunction.apply(current.col),
                        current.blizzards + 1);
                if (thisBoard[newPos.row][newPos.col].isEmpty()) {
                    if (!costSoFar.containsKey(newPos) || newCost < costSoFar.get(newPos)) {
                        newPos.pathCost = newCost;
                        costSoFar.put(newPos, newCost);
                        pq.add(newPos);
                    }
                }
            }
        }

        return costSoFar.entrySet().stream()
                .filter(entry -> entry.getKey().row == endPosition.row && entry.getKey().col == endPosition.col)
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(Integer.MAX_VALUE);

    }

    static List<String>[][] simulateBlizzard(int times) {
        if (boards.containsKey(times)) {
            return boards.get(times);
        }
        List<String>[][] tempBoard = new ArrayList[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                tempBoard[i][j] = new ArrayList<>();
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                List<String> currentStrings = boards.get(times - 1)[i][j];
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
                        } while (boards.get(times - 1)[newRow][newCol].contains("#"));
                        tempBoard[newRow][newCol].add(currentString);
                    }
                }
            }
        }
        boards.put(times, tempBoard);
        return tempBoard;
    }

    static final class Position implements Comparable<Position> {
        private final int row;
        private final int col;
        private final int blizzards;
        int pathCost;

        Position(int row, int col, int blizzards) {
            this.row = row;
            this.col = col;
            this.blizzards = blizzards;
        }

        public int row() {
            return row;
        }

        public int col() {
            return col;
        }

        @Override
        public int compareTo(Position o) {
            return Integer.compare(this.pathCost, o.pathCost);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Position position = (Position) o;

            if (row != position.row) {
                return false;
            }
            if (col != position.col) {
                return false;
            }
            return blizzards == position.blizzards;
        }

        @Override
        public int hashCode() {
            int result = row;
            result = 31 * result + col;
            result = 31 * result + blizzards;
            return result;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "row=" + row +
                    ", col=" + col +
                    ", pathCost=" + pathCost +
                    '}';
        }
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

