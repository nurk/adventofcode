package year2016.puzzle18;

/**
 * Part A: 1987
 * Part B: 19984714
 */
public class Puzzle18 {
    static void main() {
        Tiles tiles = new Tiles(
                ".^.^..^......^^^^^...^^^...^...^....^^.^...^.^^^^....^...^^.^^^...^^^^.^^.^.^^..^.^^^..^^^^^^.^^^..^",
                40);
        tiles.fillRows();
        System.out.println("Part A: " + tiles.getSafeTileCount());

        Tiles tiles2 = new Tiles(
                ".^.^..^......^^^^^...^^^...^...^....^^.^...^.^^^^....^...^^.^^^...^^^^.^^.^.^^..^.^^^..^^^^^^.^^^..^",
                400000);
        tiles2.fillRows();
        System.out.println("Part B: " + tiles2.getSafeTileCount());
    }
}
