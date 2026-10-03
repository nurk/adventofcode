package year2018.puzzle13;

public enum Heading {
    NORTH {
        @Override
        public void move(Cart cart) {
            cart.setRow(cart.getRow() - 1);
        }
    },
    EAST {
        @Override
        public void move(Cart cart) {
            cart.setColumn(cart.getColumn() + 1);
        }
    },
    SOUTH {
        @Override
        public void move(Cart cart) {
            cart.setRow(cart.getRow() + 1);
        }
    },
    WEST {
        @Override
        public void move(Cart cart) {
            cart.setColumn(cart.getColumn() - 1);
        }
    };

    public Heading getLeft() {
        return switch (this) {
            case NORTH -> WEST;
            case EAST -> NORTH;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
        };
    }

    public Heading getRight() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }

    public Heading getStraight() {
        return this;
    }

    public abstract void move(Cart cart);
}
