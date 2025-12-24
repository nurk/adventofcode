package year2023.puzzle24;

import util.Utils;

import java.util.List;

/**
 * Part A: 15593
 */
public class Puzzle24 {
    public static void main(String[] args) {
        List<Hail> hails = Utils.getInput("2023/input24.txt", Hail::new);
        long count = 0;
        for (int i = 0; i < hails.size() - 1; i++) {
            for (int j = i + 1; j < hails.size(); j++) {
                Hail hail1 = hails.get(i);
                Hail hail2 = hails.get(j);
                if (hail1.crossesInBoundsAndFuture(hail2, 200_000_000_000_000.0, 400_000_000_000_000.0)) {
                    count++;
                }
            }
        }
        System.out.println("Part A: " + count);

        // Part B is going to need linear algebra ...
        // I dont think I need any of the hail 3d stuff
        // https://www.ericburden.work/blog/2024/01/02/advent-of-code-day-24/
        List<Hail3D> hail3DS = Utils.getInput("2023/input24-test.txt", Hail3D::new);
        Hail3D rock = new Hail3D("24, 13, 10 @ -3, 1, 2");

        for (Hail3D hail3D : hail3DS) {
            hail3D.crossesInFuture(rock);
        }
    }
}
