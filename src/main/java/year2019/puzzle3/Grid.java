package year2019.puzzle3;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Grid {

    private int[][] grid = new int[55000][55000];

    public Point doAction(String actionString, Point startPoint, int wire) {
        String action = StringUtils.substring(actionString, 0, 1);
        int moves = Integer.parseInt(StringUtils.substring(actionString, 1));
        //System.out.println(actionString);

        Point movePoint = startPoint.clone();
        for (int i = 1; i <= moves; i++) {
            movePoint = getMovePoint(movePoint, action);

            if (grid[movePoint.getX()][movePoint.getY()] == 0) {
                grid[movePoint.getX()][movePoint.getY()] = wire;
            } else if (grid[movePoint.getX()][movePoint.getY()] == wire) {
                //do nothing
            } else {
                //crossing
                grid[movePoint.getX()][movePoint.getY()] = 10;
            }
            //System.out.println(movePoint.getX() + " - " + movePoint.getY() + " = " + grid[movePoint.getX()][movePoint.getY()]);
        }
        return movePoint;
    }

    public int stepsTillPoint(String[] actions, Point startPoint, Point goal) {
        int stepsDone = 0;
        if (startPoint.equals(goal)) {
            return stepsDone;
        }

        Point movePoint = startPoint.clone();
        for (String actionString : actions) {
            String action = StringUtils.substring(actionString, 0, 1);
            int moves = Integer.parseInt(StringUtils.substring(actionString, 1));
            for (int i = 1; i <= moves; i++) {
                stepsDone++;
                movePoint = getMovePoint(movePoint, action);

                if (movePoint.equals(goal)) {
                    return stepsDone;
                }

            }
        }
        throw new IllegalArgumentException("uh oh");
    }

    private Point getMovePoint(Point movePoint, String action) {
        switch (action) {
            case "L":
                movePoint = new Point(movePoint.getX() - 1, movePoint.getY());
                break;
            case "R":
                movePoint = new Point(movePoint.getX() + 1, movePoint.getY());
                break;
            case "U":
                movePoint = new Point(movePoint.getX(), movePoint.getY() - 1);
                break;
            case "D":
                movePoint = new Point(movePoint.getX(), movePoint.getY() + 1);
                break;
            default:
                break;
        }
        return movePoint;
    }

    public List<Point> getIntersections() {
        List<Point> intersections = new ArrayList<>();
        for (int i = 0; i < 55000; i++) {
            for (int j = 0; j < 55000; j++) {
                if (grid[i][j] == 10) {
                    intersections.add(new Point(i, j));
                }
            }

        }
        return intersections;
    }
}
