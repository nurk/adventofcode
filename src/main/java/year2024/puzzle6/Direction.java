package year2024.puzzle6;

public enum Direction {
    UP {
        @Override
        public int getColumn(int col) {
            return col;
        }

        @Override
        public int getRow(int row) {
            return --row;
        }
    }, DOWN {
        @Override
        public int getColumn(int col) {
            return col;
        }

        @Override
        public int getRow(int row) {
            return ++row;
        }
    }, LEFT {
        @Override
        public int getColumn(int col) {
            return --col;
        }

        @Override
        public int getRow(int row) {
            return row;
        }
    }, RIGHT {
        @Override
        public int getColumn(int col) {
            return ++col;
        }

        @Override
        public int getRow(int row) {
            return row;
        }
    };

    public abstract int getColumn(int col);
    public abstract int getRow(int row);

    public static Direction getRightDirection(Direction direction) {
        return switch (direction) {
            case UP -> RIGHT;
            case DOWN -> LEFT;
            case LEFT -> UP;
            case RIGHT -> DOWN;
        };
    }
}
