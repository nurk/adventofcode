package year2024.puzzle5;

import java.util.List;

public class Rule {

    private final Integer left;
    private final Integer right;

    public Rule(String line) {
        String[] parts = line.split("\\|");

        left = Integer.parseInt(parts[0]);
        right = Integer.parseInt(parts[1]);
    }

    public boolean satisfies(List<Integer> update) {
        if (update.contains(left) && update.contains(right)) {
            return update.indexOf(left) < update.indexOf(right);
        }
        return true;
    }

    public void swapIfNotSatisfied(List<Integer> update) {
        if(!satisfies(update)) {
            int leftIndex = update.indexOf(left);
            int rightIndex = update.indexOf(right);

            update.remove(leftIndex);
            update.remove(rightIndex);

            update.add(rightIndex, left);
            update.add(leftIndex, right);
        }
    }

    @Override
    public String toString() {
        return "Rule{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
