package year2024.puzzle18;

import org.javatuples.Pair;

public enum Direction {
    UP("^") {
        @Override
        public int getColumn(int col) {
            return col;
        }

        @Override
        public int getRow(int row) {
            return --row;
        }

        @Override
        public Pair<Integer, Integer> getPosition(Pair<Integer, Integer> position) {
            return Pair.with(position.getValue0() - 1, position.getValue1());
        }

    }, DOWN("v") {
        @Override
        public int getColumn(int col) {
            return col;
        }

        @Override
        public int getRow(int row) {
            return ++row;
        }

        @Override
        public Pair<Integer, Integer> getPosition(Pair<Integer, Integer> position) {
            return Pair.with(position.getValue0() + 1, position.getValue1());
        }

    }, LEFT("<") {
        @Override
        public int getColumn(int col) {
            return --col;
        }

        @Override
        public int getRow(int row) {
            return row;
        }

        @Override
        public Pair<Integer, Integer> getPosition(Pair<Integer, Integer> position) {
            return Pair.with(position.getValue0(), position.getValue1() - 1);
        }

    }, RIGHT(">") {
        @Override
        public int getColumn(int col) {
            return ++col;
        }

        @Override
        public int getRow(int row) {
            return row;
        }

        @Override
        public Pair<Integer, Integer> getPosition(Pair<Integer, Integer> position) {
            return Pair.with(position.getValue0(), position.getValue1() + 1);
        }

    };

    private final String symbol;

    Direction(String symbol) {
        this.symbol = symbol;
    }

    public abstract int getColumn(int col);

    public abstract int getRow(int row);

    public abstract Pair<Integer, Integer> getPosition(Pair<Integer, Integer> position);

    public static Direction fromSymbol(String symbol) {
        for (Direction direction : Direction.values()) {
            if (direction.symbol.equals(symbol)) {
                return direction;
            }
        }
        return null;
    }

    public static Direction getClockwise(Direction direction) {
        return switch (direction) {
            case UP -> RIGHT;
            case DOWN -> LEFT;
            case LEFT -> UP;
            case RIGHT -> DOWN;
        };
    }

    public static Direction getCounterClockwise(Direction direction) {
        return switch (direction) {
            case UP -> LEFT;
            case DOWN -> RIGHT;
            case LEFT -> DOWN;
            case RIGHT -> UP;
        };
    }
}
