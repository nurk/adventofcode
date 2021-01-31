package year2020.puzzle20;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Tile {
    private Border top;
    private Border left;
    private Border right;
    private Border bottom;
    private Long id;

    public Tile(List<String> input) {
        id = Long.valueOf(StringUtils.remove(StringUtils.remove(input.get(0), "Tile "), ":"));
        input.remove(0);
        input.remove(input.size() - 1);

        top = new Border(input.get(0));
        bottom = new Border(input.get(input.size() - 1));
        String left = "";
        String right = "";
        for (String s : input) {
            left = left + s.substring(0, 1);
            right = right + s.substring(s.length() - 1, s.length());
        }
        this.left = new Border(left);
        this.right = new Border(right);
    }

    public Long getId() {
        return id;
    }

    public Border getTop() {
        return top;
    }

    public Border getLeft() {
        return left;
    }

    public Border getRight() {
        return right;
    }

    public Border getBottom() {
        return bottom;
    }

    public void compare(Tile tile) {
        top.compare(tile.getTop());
        top.compare(tile.getBottom());
        top.compare(tile.getLeft());
        top.compare(tile.getRight());
        left.compare(tile.getTop());
        left.compare(tile.getBottom());
        left.compare(tile.getLeft());
        left.compare(tile.getRight());
        right.compare(tile.getTop());
        right.compare(tile.getBottom());
        right.compare(tile.getLeft());
        right.compare(tile.getRight());
        bottom.compare(tile.getTop());
        bottom.compare(tile.getBottom());
        bottom.compare(tile.getLeft());
        bottom.compare(tile.getRight());
    }

    public int numberOfMatchingBorders() {
        int sum = 0;
        if (top.isFoundSame()) sum++;
        if (left.isFoundSame()) sum++;
        if (right.isFoundSame()) sum++;
        if (bottom.isFoundSame()) sum++;

        return sum;
    }
}
