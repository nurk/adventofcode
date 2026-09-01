package year2016.puzzle22;

import lombok.Getter;

@Getter
public class Node {

    private int x;
    private int y;
    private int size;
    private int used;
    private int available;
    private int usePercent;

    public Node(String line) {
        String[] split = line.split("\\s+");
        String[] xySplit = split[0].split("-");
        this.x = Integer.parseInt(xySplit[1].replace("x", ""));
        this.y = Integer.parseInt(xySplit[2].replace("y", ""));
        this.size = Integer.parseInt(split[1].replace("T", ""));
        this.used = Integer.parseInt(split[2].replace("T", ""));
        this.available = Integer.parseInt(split[3].replace("T", ""));
        this.usePercent = Integer.parseInt(split[4].replace("%", ""));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Node node = (Node) o;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", size=" + size +
                ", used=" + used +
                ", available=" + available +
                ", usePercent=" + usePercent +
                '}';
    }
}
