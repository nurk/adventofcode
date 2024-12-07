package year2024.puzzle4;

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
    }, UP_LEFT {
        @Override
        public int getColumn(int col) {
            return --col;
        }

        @Override
        public int getRow(int row) {
            return --row;
        }
    }, UP_RIGHT {
        @Override
        public int getColumn(int col) {
            return ++col;
        }

        @Override
        public int getRow(int row) {
            return --row;
        }
    }, DOWN_LEFT {
        @Override
        public int getColumn(int col) {
            return --col;
        }

        @Override
        public int getRow(int row) {
            return ++row;
        }
    }, DOWN_RIGHT {
        @Override
        public int getColumn(int col) {
            return ++col;
        }

        @Override
        public int getRow(int row) {
            return ++row;
        }
    };

    public abstract int getColumn(int col);

    public abstract int getRow(int row);
}
