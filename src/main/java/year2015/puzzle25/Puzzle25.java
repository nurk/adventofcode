package year2015.puzzle25;

public class Puzzle25 {

    public static void main(String[] args) {
        System.out.println(calculateElementAt(3010, 3019));
    }

    private static long calculateNextValue(long previous) {
        return (previous * 252533) % 33554393;
    }

    // https://www.geeksforgeeks.org/zigzag-or-diagonal-traversal-of-matrix/
    static long calculateElementAt(int targetRow, int targetCol) {
        int rows = Math.max(targetRow, targetCol) * 2;
        int cols = Math.max(targetRow, targetCol) * 2;
        long previous = 0;
        for (int diagonal = 1; diagonal <= (rows + cols - 1); diagonal++) {

            int start_col = Math.max(0, diagonal - rows);

            int elementsOnDiagonal = Math.min(Math.min(diagonal, (cols - start_col)), rows);

            for (int j = 0; j < elementsOnDiagonal; j++) {
                int currentRow = Math.min(rows, diagonal) - j - 1;
                int currentCol = start_col + j;
                if (currentRow == 0 && currentCol == 0) {
                    previous = 20151125L;
                } else {
                    previous = calculateNextValue(previous);
                }

                if (currentRow == targetRow - 1 && currentCol == targetCol - 1) {
                    return previous;
                }
            }
        }
        return -1;
    }
}
