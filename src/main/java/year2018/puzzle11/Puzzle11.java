package year2018.puzzle11;

/**
 * Part A: FuelCell{x=21, y=72, level=4}
 * Part B: [FuelCell{x=242, y=13, level=4}, 9]
 */
public class Puzzle11 {
    static void main() {
        FuelGrid grid = new FuelGrid(7315);

        System.out.println("Part A: " + grid.getTopLeftCellOfBiggestCluster());
        System.out.println("Part B: " + grid.getMaxFuel());
    }
}
