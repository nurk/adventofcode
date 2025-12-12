package year2025.puzzle12;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Present {
    private final int index;
    private final String[][] shape = new String[3][3];
    private final List<String[][]> permutations = new ArrayList<>();
    private final long numberOfOccupiedCells;

    public Present(List<String> input) {
        this.index = Integer.parseInt(input.get(0).replace(":", ""));
        shape[0] = input.get(1).split("");
        shape[1] = input.get(2).split("");
        shape[2] = input.get(3).split("");

        long numberOfOcciepiedCells = 0;
        for (String[] row : shape) {
            for (String cell : row) {
                if (cell.equals("#")) {
                    numberOfOcciepiedCells++;
                }
            }
        }
        this.numberOfOccupiedCells = numberOfOcciepiedCells;

        createMatrixPermutations();
        removeDuplicatesFromPermutations();
    }

    private void removeDuplicatesFromPermutations() {
        List<String[][]> uniquePermutations = new ArrayList<>();
        for (String[][] permutation : permutations) {
            boolean isDuplicate = uniquePermutations.stream()
                    .anyMatch(existingPermutation -> {
                        for (int row = 0; row < 3; row++) {
                            boolean equals = Arrays.equals(existingPermutation[row], permutation[row]);
                            if (!equals) {
                                return false;
                            }
                        }
                        return true;
                    });
            if (!isDuplicate) {
                uniquePermutations.add(permutation);
            }
        }

        permutations.clear();
        permutations.addAll(uniquePermutations);
    }

    private void createMatrixPermutations() {
        String[][] currentPermutation = shape;
        for (int i = 0; i < 4; i++) {
            permutations.add(currentPermutation);
            currentPermutation = rotateMatrix(currentPermutation);
        }

        currentPermutation = flipMatrix(shape);
        for (int i = 0; i < 4; i++) {
            permutations.add(currentPermutation);
            currentPermutation = rotateMatrix(currentPermutation);
        }
    }

    private String[][] rotateMatrix(String[][] matrix) {
        String[][] rotated = new String[3][3];
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                rotated[column][2 - row] = matrix[row][column];
            }
        }
        return rotated;
    }

    private String[][] flipMatrix(String[][] matrix) {
        String[][] flipped = new String[3][3];
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                flipped[row][2 - column] = matrix[row][column];
            }
        }
        return flipped;
    }

    @Override
    public String toString() {
        return "Present{" +
                "index=" + index +
                ", shape=\n" + shapeToString() +
                ", numberOfOccupiedCells=" + numberOfOccupiedCells +
                '}';
    }

    public static String shapeToString(String[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (String[] row : matrix) {
            for (String cell : row) {
                sb.append(cell);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String shapeToString() {
        return Present.shapeToString(this.shape);
    }
}
