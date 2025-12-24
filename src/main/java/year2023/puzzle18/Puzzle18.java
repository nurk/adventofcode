package year2023.puzzle18;

import util.Utils;

import java.util.List;

/**
 * Part A: 46359
 * Part B: 59574883048274
 */
public class Puzzle18 {
    public static void main(String[] args) {
        List<String> moves = Utils.getInput("2023/input18.txt");

        DigSite digSite = new DigSite(1000);

        moves.forEach(digSite::move);
        digSite.fillPolygon();
        System.out.println("Part A: " + digSite.getArea());

        DigSite2 digSite2 = new DigSite2();
        moves.forEach(digSite2::moveA);
        System.out.println("Part A: " + digSite2.getAreaIncludingBorder());

         digSite2 = new DigSite2();
        moves.forEach(digSite2::moveB);
        System.out.println("Part B: " + digSite2.getAreaIncludingBorder());
    }
}
