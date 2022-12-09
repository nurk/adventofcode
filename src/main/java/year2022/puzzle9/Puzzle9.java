package year2022.puzzle9;

import util.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class Puzzle9 {

    public static void main(String[] args) {
        List<Position> positions = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            positions.add(new Position(0, 0));

        }
        Set<Position> visited = new HashSet<>();
        visited.add(positions.get(positions.size() - 1));

        for (String line : Utils.getInput("2022/input9.txt")) {
            Move move = Move.valueOf(line.split(" ")[0]);
            for (int i = 0; i < Integer.parseInt(line.split(" ")[1]); i++) {
                positions.set(0, move.moveHead.apply(positions.get(0)));
                for (int j = 1; j < positions.size(); j++) {
                    positions.set(j, moveTail(positions.get(j - 1), positions.get(j)));
                }
                visited.add(positions.get(positions.size() - 1));
            }
        }

        System.out.println(visited.size());
    }


    record Position(int x, int y) {
        public boolean needsDiagonalMove(Position other) {
            return Math.abs(x - other.x) > 1 && y != other.y || Math.abs(y - other.y) > 1 && x != other.x;
        }

        public int getXMove(Position other) {
            if (x > other.x) {
                return other.x + 1;
            }
            return other.x - 1;
        }

        public int getYMove(Position other) {
            if (y > other.y) {
                return other.y + 1;
            }
            return other.y - 1;
        }
    }

    enum Move {
        L(position -> new Position(position.x, position.y - 1)),
        R(position -> new Position(position.x, position.y + 1)),
        U(position -> new Position(position.x - 1, position.y)),
        D(position -> new Position(position.x + 1, position.y));

        private final Function<Position, Position> moveHead;

        Move(Function<Position, Position> moveHead) {
            this.moveHead = moveHead;
        }
    }

    public static Position moveTail(Position head, Position tail) {
        int x = tail.x;
        int y = tail.y;
        if (head.needsDiagonalMove(tail)) {
            return new Position(head.getXMove(tail), head.getYMove(tail));
        }

        if (Math.abs(head.x - tail.x) > 1) {
            x = head.getXMove(tail);
        }
        if (Math.abs(head.y - tail.y) > 1) {
            y = head.getYMove(tail);
        }
        return new Position(x, y);
    }
}
