package year2019.puzzle3;

import org.apache.commons.lang3.StringUtils;
import util.Utils;
import year2019.puzzle2.IntCode;
import year2019.puzzle2.IntCode2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

public class Puzzle3 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        Grid grid = new Grid();
        List<String> lines = new ArrayList<>();
        //lines.add("R8,U5,L5,D3");
        //lines.add("U7,R6,D4,L4");

        //lines.add("R75,D30,R83,U83,L12,D49,R71,U7,L72");
        //lines.add("U62,R66,U55,R34,D71,R55,D58,R83");

        //lines.add("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51");
        //lines.add("U98,R91,D20,R16,D67,R40,U7,R15,U6,R7");

        lines.addAll(Files.readAllLines(Utils.getInputPath("2019/input3.txt")));

        Point startPoint = new Point(22500, 22500);

        for (int i = 0; i < lines.size(); i++) {
            Point workingPoint = startPoint.clone();
            int wire = i + 1;
            for (String action : StringUtils.split(lines.get(i), ",")) {
                workingPoint = grid.doAction(action, workingPoint.clone(), wire);
            }

        }
        List<Point> intersections = grid.getIntersections();

        SortedSet<Integer> steps = new TreeSet<>();
        for (Point intersection : intersections) {
            int stepsTaken = 0;
            for (String line : lines) {
                stepsTaken += grid.stepsTillPoint(line.split(","), startPoint.clone(), intersection.clone());
            }
            steps.add(stepsTaken);
        }


        SortedSet<Integer> distances = new TreeSet<>();
        for (Point intersection : intersections) {
            distances.add(startPoint.getManhattenDistance(intersection));
        }


        System.out.println("solution A = " + distances.first());
        System.out.println("solution B = " + steps.first());
    }
}
