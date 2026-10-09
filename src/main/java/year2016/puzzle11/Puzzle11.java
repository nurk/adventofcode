package year2016.puzzle11;

import org.jspecify.annotations.NonNull;
import util.Utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Part A: 37
 * Part B: 61
 */
public class Puzzle11 {

    static void main() {
        List<String> lines = Utils.getInput("2016/input11.txt");

        State initialState = getInitialState(lines);

        System.out.println("Part A: " + partA(initialState));
        System.out.println("Part B: " + partA(getInitialStateB(lines)));
    }

    private static int partA(State initialState) {
        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(initialState);
        visited.add(initialState.getStateKey());

        while (!queue.isEmpty()) {
            State current = queue.poll();

            if (current.isGoal()) {
                return current.steps;
            }

            // 1. Gather all items on the current elevator floor
            List<Choice> choices = getChoices(current);

            // Determine which directions the elevator can move
            List<Integer> nextFloors = new ArrayList<>();
            if (current.elevatorFloor < 3) {
                nextFloors.add(current.elevatorFloor + 1); // Move up
            }
            if (current.elevatorFloor > 0) {
                nextFloors.add(current.elevatorFloor - 1); // Move down
            }

            // Try moving 1 or 2 items to the neighboring floors
            for (int nextFloor : nextFloors) {

                // --- LOOP A: Move exactly ONE item ---
                for (int i = 0; i < choices.size(); i++) {
                    Choice choice1 = choices.get(i);

                    State nextState = new State(
                            nextFloor,
                            current.chips,
                            current.generators,
                            current.steps + 1
                    );

                    // Apply the movement
                    if (choice1.isChip) {
                        nextState.chips[choice1.id] = nextFloor;
                    } else {
                        nextState.generators[choice1.id] = nextFloor;
                    }

                    // check if valid and enqueue if unique
                    if (nextState.isValid()) {
                        if (visited.add(nextState.getStateKey())) {
                            queue.add(nextState);
                        }
                    }

                    // --- LOOP B: Move TWO items (combinations of i and j) ---
                    for (int j = i + 1; j < choices.size(); j++) {
                        Choice choice2 = choices.get(j);

                        State nextState2 = new State(
                                nextFloor,
                                current.chips,
                                current.generators,
                                current.steps + 1
                        );

                        // Apply both movements
                        if (choice1.isChip) {
                            nextState2.chips[choice1.id] = nextFloor;
                        } else {
                            nextState2.generators[choice1.id] = nextFloor;
                        }

                        if (choice2.isChip) {
                            nextState2.chips[choice2.id] = nextFloor;
                        } else {
                            nextState2.generators[choice2.id] = nextFloor;
                        }

                        // check if valid and enqueue if unique
                        if (nextState2.isValid()) {
                            if (visited.add(nextState2.getStateKey())) {
                                queue.add(nextState2);
                            }
                        }
                    }
                }
            }

        }
        return -1;
    }

    private static @NonNull List<Choice> getChoices(State current) {
        List<Integer> chipsOnFloor = new ArrayList<>();
        List<Integer> gensOnFloor = new ArrayList<>();

        for (int i = 0; i < current.chips.length; i++) {
            if (current.chips[i] == current.elevatorFloor) {
                chipsOnFloor.add(i);
            }
            if (current.generators[i] == current.elevatorFloor) {
                gensOnFloor.add(i);
            }
        }

        // Create a combined list of individual choices we can move
        List<Choice> choices = new ArrayList<>();
        for (int c : chipsOnFloor) {
            choices.add(new Choice(c, true));
        }
        for (int g : gensOnFloor) {
            choices.add(new Choice(g, false));
        }
        return choices;
    }

    private static State getInitialStateB(List<String> lines) {
        State initialState = getInitialState(lines);

        // Add the extra elements for part B
        int newSize = initialState.chips.length + 2;
        int[] newChips = Arrays.copyOf(initialState.chips, newSize);
        int[] newGenerators = Arrays.copyOf(initialState.generators, newSize);

        // Set the new elements to be on floor 0
        newChips[newSize - 2] = 0; // New chip 1
        newGenerators[newSize - 2] = 0; // New generator 1
        newChips[newSize - 1] = 0; // New chip 2
        newGenerators[newSize - 1] = 0; // New generator 2

        return new State(initialState.elevatorFloor, newChips, newGenerators, initialState.steps);
    }

    private static State getInitialState(List<String> lines) {
        Map<String, Integer> elementIds = new HashMap<>();
        List<Integer> chipFloors = new ArrayList<>();
        List<Integer> genFloors = new ArrayList<>();

        Pattern chipPattern = Pattern.compile("(\\w+)-compatible microchip");
        Pattern genPattern = Pattern.compile("(\\w+) generator");

        for (int floor = 0; floor < lines.size(); floor++) {
            String line = lines.get(floor);

            Matcher chipMatcher = chipPattern.matcher(line);
            while (chipMatcher.find()) {
                String element = chipMatcher.group(1);
                int id = getElementId(element, elementIds, chipFloors, genFloors);
                chipFloors.set(id, floor);
            }

            Matcher genMatcher = genPattern.matcher(line);
            while (genMatcher.find()) {
                String element = genMatcher.group(1);
                int id = getElementId(element, elementIds, chipFloors, genFloors);
                genFloors.set(id, floor);
            }
        }

        int[] finalChips = chipFloors.stream().mapToInt(i -> i).toArray();
        int[] finalGenerators = genFloors.stream().mapToInt(i -> i).toArray();

        return new State(0, finalChips, finalGenerators, 0);
    }

    private static int getElementId(String element, Map<String, Integer> elementIds,
                                    List<Integer> chipFloors, List<Integer> genFloors) {
        if (!elementIds.containsKey(element)) {
            int newId = elementIds.size();
            elementIds.put(element, newId);

            // Add a placeholder (-1) for this new element in both lists
            chipFloors.add(-1);
            genFloors.add(-1);
        }
        return elementIds.get(element);
    }

   record State (
        int elevatorFloor,
        int[] chips, // position in array is the element type, value is the floor (0-indexed)
        int[] generators, // position in array is the element type, value is the floor (0-indexed)
        int steps){

       public State(int elevatorFloor, int[] chips, int[] generators, int steps) {
           this.elevatorFloor = elevatorFloor;
           this.chips = chips.clone();
           this.generators = generators.clone();
           this.steps = steps;
       }

        public boolean isValid() {
            for (int i = 0; i < chips.length; i++) {
                // If the chip is on the same floor as its own generator, it's safe
                if (chips[i] == generators[i]) {
                    continue;
                }

                // If it's separate, check if ANY other generator is on the chip's floor
                for (int g : generators) {
                    if (g == chips[i]) {
                        return false; // Fried!
                    }
                }
            }
            return true;
        }

        public boolean isGoal() {
            if (elevatorFloor != 3) {
                return false; // Floor 4 (0-indexed as 3)
            }
            for (int c : chips) {
                if (c != 3) {
                    return false;
                }
            }
            for (int g : generators) {
                if (g != 3) {
                    return false;
                }
            }
            return true;
        }

        public String getStateKey() {
            StringBuilder sb = new StringBuilder();
            sb.append(elevatorFloor).append("-");

            List<String> pairs = new ArrayList<>();
            for (int i = 0; i < chips.length; i++) {
                pairs.add(chips[i] + "," + generators[i]);
            }
            Collections.sort(pairs);

            sb.append(String.join(";", pairs));
            return sb.toString();
        }
    }

    record Choice(int id, boolean isChip) {
    }

}
