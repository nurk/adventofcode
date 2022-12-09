package year2022.puzzle9;

import util.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Puzzle9 {

    public static void main(String[] args) {
        List<Position> knots = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            knots.add(new Position(0, 0));

        }
        Set<Position> visited = new HashSet<>();
        visited.add(knots.get(knots.size() - 1));

        for (String line : Utils.getInput("2022/input9.txt")) {
            String[] split = line.split(" ");
            for (int i = 0; i < Integer.parseInt(split[1]); i++) {
                knots.set(0, knots.get(0).moveAsHead(split[0]));
                for (int j = 1; j < knots.size(); j++) {
                    knots.set(j, knots.get(j - 1).moveTail(knots.get(j)));
                }
                visited.add(knots.get(knots.size() - 1));
            }
        }

        System.out.println(visited.size());
    }


    record Position(int x, int y) {
        public boolean needsDiagonalMove(Position other) {
            return Math.abs(x - other.x) > 1 && y != other.y || Math.abs(y - other.y) > 1 && x != other.x;
        }

        public Position moveAsHead(String m) {
            return switch (m) {
                case "L" -> new Position(x, y - 1);
                case "R" -> new Position(x, y + 1);
                case "U" -> new Position(x - 1, y);
                case "D" -> new Position(x + 1, y);
                default -> throw new IllegalArgumentException();
            };
        }

        public Position moveTail(Position tail) {
            int x = tail.x;
            int y = tail.y;
            if (this.needsDiagonalMove(tail)) {
                return new Position(getXMove(tail), getYMove(tail));
            }

            if (Math.abs(this.x - tail.x) > 1) {
                x = getXMove(tail);
            }
            if (Math.abs(this.y - tail.y) > 1) {
                y = getYMove(tail);
            }
            return new Position(x, y);
        }

        private int getXMove(Position other) {
            if (x > other.x) {
                return other.x + 1;
            }
            return other.x - 1;
        }

        private int getYMove(Position other) {
            if (y > other.y) {
                return other.y + 1;
            }
            return other.y - 1;
        }
    }
}
