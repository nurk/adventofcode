package year2021.puzzle15;

class Node implements Comparable<Node> {
    private final int row, col, cost;
    private int pathCost;

    public Node(int row, int col, int cost) {
        this.row = row;
        this.col = col;
        this.cost = cost;
        this.pathCost = 0;
    }

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return /*"(" + row + "," + col + ")*/"[" + cost + "]";
    }

    @Override
    public int compareTo(Node o) {
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

        Node node = (Node) o;

        if (row != node.row) {
            return false;
        }
        return col == node.col;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        return result;
    }
}
