package year2024.puzzle12;

import org.javatuples.Pair;

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

        @Override
        public Pair<Integer, Integer> getPosition(Pair<Integer, Integer> position) {
            return Pair.with(position.getValue0() - 1, position.getValue1());
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

        @Override
        public Pair<Integer, Integer> getPosition(Pair<Integer, Integer> position) {
            return Pair.with(position.getValue0() + 1, position.getValue1());
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

        @Override
        public Pair<Integer, Integer> getPosition(Pair<Integer, Integer> position) {
            return Pair.with(position.getValue0(), position.getValue1() - 1);
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

        @Override
        public Pair<Integer, Integer> getPosition(Pair<Integer, Integer> position) {
            return Pair.with(position.getValue0(), position.getValue1() + 1);
        }
    };

    public abstract int getColumn(int col);

    public abstract int getRow(int row);

    public abstract Pair<Integer, Integer> getPosition(Pair<Integer, Integer> position);
}
