package year2025.puzzle10.internetz3;

import com.microsoft.z3.*;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.IntStream;

/**
 * https://github.com/welguisz/advent-of-code/blob/main/src/com/dwelguisz/year2025/Factory.java
 *
 * Honest to god, I have not idea how this works.
 */
public class MachineInternetZ3 {
    private final List<List<Integer>> buttonGroups = new ArrayList<>();
    private List<Integer> joltageRequirements;

    public MachineInternetZ3(String line) {
        String[] split = StringUtils.split(line, " ");
        for (String s : split) {
            if (s.startsWith("(")) {
                String groupString = StringUtils.substringBetween(s, "(", ")");
                buttonGroups.add(Arrays.stream(groupString.split(","))
                        .map(Integer::parseInt)
                        .toList());
            }
            if (s.startsWith("{")) {
                String groupString = StringUtils.substringBetween(s, "{", "}");
                joltageRequirements = Arrays.stream(groupString.split(","))
                        .map(Integer::parseInt)
                        .toList();
            }
        }
    }

    public int solvePart2WithZ3() {
        Context context = new Context();
        Optimize optimize = context.mkOptimize();
        IntExpr presses = context.mkIntConst("presses");

        IntExpr[] buttonVariables = IntStream.range(0, buttonGroups.size())
                .mapToObj(i -> context.mkIntConst("button" + i))
                .toArray(IntExpr[]::new);

        Map<Integer, List<IntExpr>> countersToButtons = new HashMap<>();

        for (int i = 0; i < buttonGroups.size(); i++) {
            IntExpr buttonVariable = buttonVariables[i];
            for (int flip : buttonGroups.get(i)) {
                countersToButtons.computeIfAbsent(flip, k -> new ArrayList<>()).add(buttonVariable);
            }
        }

        for (Map.Entry<Integer, List<IntExpr>> entry : countersToButtons.entrySet()) {
            int counterIndex = entry.getKey();
            List<IntExpr> counterButtons = entry.getValue();

            IntExpr targetValue = context.mkInt(joltageRequirements.get(counterIndex));

            IntExpr[] buttonPressesArray = counterButtons.toArray(new IntExpr[0]);

            IntExpr sumOfButtonPresses = (IntExpr) context.mkAdd(buttonPressesArray);

            BoolExpr equation = context.mkEq(targetValue, sumOfButtonPresses);
            optimize.Add(equation);
        }

        IntExpr zero = context.mkInt(0);
        for (IntExpr buttonVar : buttonVariables) {
            BoolExpr nonNegative = context.mkGe(buttonVar, zero);
            optimize.Add(nonNegative);
        }

        IntExpr sumOfAllButtonVars = (IntExpr) context.mkAdd(buttonVariables);
        BoolExpr totalPressesEq = context.mkEq(presses, sumOfAllButtonVars);
        optimize.Add(totalPressesEq);

        optimize.MkMinimize(presses);

        Status status = optimize.Check();

        if (status == Status.SATISFIABLE) {
            Model model = optimize.getModel();
            IntNum outputValue = (IntNum) model.evaluate(presses, false);
            return outputValue.getInt();
        } else if (status == Status.UNSATISFIABLE) {
            System.out.println("Problem is UNSATISFIABLE (no solution exists).");
        } else {
            System.out.println("Optimization could not be determined (" + status + ").");
        }
        return -1000000;
    }
}

