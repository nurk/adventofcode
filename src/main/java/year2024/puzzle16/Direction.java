package year2024.puzzle16;

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

        @Override
        public int getCost(Direction direction) {
            return switch (direction) {
                case UP -> 1;
                case DOWN -> Integer.MAX_VALUE;
                case LEFT -> 1001;
                case RIGHT -> 1001;
            };
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

        @Override
        public int getCost(Direction direction) {
            return switch (direction) {
                case UP -> Integer.MAX_VALUE;
                case DOWN -> 1;
                case LEFT -> 1001;
                case RIGHT -> 1001;
            };
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

        @Override
        public int getCost(Direction direction) {
            return switch (direction) {
                case UP -> 1001;
                case DOWN -> 2001;
                case LEFT -> 1;
                case RIGHT -> Integer.MAX_VALUE;
            };
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

        @Override
        public int getCost(Direction direction) {
            return switch (direction) {
                case UP -> 1001;
                case DOWN -> 1001;
                case LEFT -> Integer.MAX_VALUE;
                case RIGHT -> 1;
            };
        }
    };

    private final String symbol;

    Direction(String symbol) {
        this.symbol = symbol;
    }

    public abstract int getColumn(int col);

    public abstract int getRow(int row);

    public abstract Pair<Integer, Integer> getPosition(Pair<Integer, Integer> position);

    public abstract int getCost(Direction direction);

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
