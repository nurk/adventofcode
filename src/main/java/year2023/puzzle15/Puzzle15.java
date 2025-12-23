package year2023.puzzle15;

import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Part A: 512283
 * Part B: 215827
 */
public class Puzzle15 {
    public static void main(String[] args) {
        List<String> input = Utils.getInput("2023/input15.txt").stream()
                .flatMap(s -> Arrays.stream(s.split(",")))
                .toList();

        System.out.println("Part A: " + input.stream()
                .map(Puzzle15::calculateHash)
                .reduce(0L, Long::sum));

        solvePartB(input);
    }

    private static void solvePartB(List<String> input) {
        List<Lens> lenses = input.stream()
                .map(Lens::new)
                .toList();

        List<List<Lens>> boxes = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            boxes.add(new ArrayList<>());
        }

        for (Lens lens : lenses) {
            if (lens.getOperation().equals("=")) {
                addLensToBox(boxes.get(lens.getHash()), lens);
            } else {
                Lens toRemove = findLensById(lenses, lens.getId());
                boxes.get(toRemove.getHash()).removeIf(l -> l.getId().equals(toRemove.getId()));
            }
        }

        long totalFocusPower = 0;
        for (int i = 0; i < boxes.size(); i++) {
            for (int j = 0; j < boxes.get(i).size(); j++) {
                totalFocusPower += (long) (i + 1) * (j + 1) * boxes.get(i).get(j).getFocalLength();

            }
        }

        System.out.println("Part B: " + totalFocusPower);
    }

    private static void addLensToBox(List<Lens> box, Lens lens1) {
        int existingIndex = -1;
        for (int i = 0; i < box.size(); i++) {
            if (box.get(i).getId().equals(lens1.getId())) {
                existingIndex = i;
                break;
            }
        }

        if (existingIndex != -1) {
            box.set(existingIndex, lens1);
        } else {
            box.add(lens1);
        }
    }

    private static Lens findLensById(List<Lens> lenses, String id) {
        for (Lens lens : lenses) {
            if (lens.getId().equals(id)) {
                return lens;
            }
        }
        throw new IllegalArgumentException("Lens with id " + id + " not found");
    }

    private static long calculateHash(String input) {
        long hash = 0;
        for (char c : input.toCharArray()) {
            hash += (int) c;
            hash *= 17;
            hash %= 256;
        }
        return hash;
    }
}
