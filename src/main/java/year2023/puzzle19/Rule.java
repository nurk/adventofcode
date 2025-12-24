package year2023.puzzle19;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

public class Rule implements Acceptable, Followable {

    private final String result;
    @Getter
    private final boolean hasCondition;
    @Getter
    private String conditionParameter;
    private int conditionValue;
    private String conditionOperator;

    public Rule(String input) {
        if (!input.contains(":")) {
            hasCondition = false;
            result = input;
        } else {
            hasCondition = true;
            String[] split = input.split(":");
            result = split[1];

            conditionParameter = split[0].substring(0, 1);
            conditionOperator = split[0].substring(1, 2);
            conditionValue = Integer.parseInt(split[0].substring(2));
        }
    }

    public boolean applies(MachinePart machinePart) {
        if (!hasCondition) {
            return true;
        }

        int parameterValue;
        switch (conditionParameter) {
            case "x" -> parameterValue = machinePart.getX();
            case "m" -> parameterValue = machinePart.getM();
            case "a" -> parameterValue = machinePart.getA();
            case "s" -> parameterValue = machinePart.getS();
            default -> throw new IllegalArgumentException("Unknown parameter: " + conditionParameter);
        }

        return switch (conditionOperator) {
            case ">" -> parameterValue > conditionValue;
            case "<" -> parameterValue < conditionValue;
            default -> throw new IllegalArgumentException("Unknown operator: " + conditionOperator);
        };
    }

    @Override
    public boolean isAcceptable(MachinePart machinePart, Map<String, Workflow> workflows) {
        if (result.equals("A")) {
            return true;
        } else if (result.equals("R")) {
            return false;
        }

        return workflows.get(result).isAcceptable(machinePart, workflows);
    }

    @Override
    public void follow(Map<String, Workflow> workflows, List<List<Rule>> followedRules, List<Rule> currentRules) {
        if (result.equals("A") || result.equals("R")) {
            followedRules.add(currentRules);
            return;
        }

        Workflow nextWorkflow = workflows.get(result);
        nextWorkflow.follow(workflows, followedRules, currentRules);
    }

    private Pair<Integer, Integer> getRange() {
        if (conditionOperator.equals("<")) {
            return Pair.of(1, conditionValue);
        } else {
            return Pair.of(conditionValue, 4000);
        }
    }

    public Pair<Integer, Integer> mergeRange(Pair<Integer, Integer> existingRange) {
        Pair<Integer, Integer> ruleRange = getRange();

        int newMin = Math.max(existingRange.getLeft(), ruleRange.getLeft());
        int newMax = Math.min(existingRange.getRight(), ruleRange.getRight());

        return Pair.of(newMin, newMax);
    }

    @Override
    public String toString() {
        return "Rule{" +
                "result='" + result + '\'' +
                ", hasCondition=" + hasCondition +
                ", conditionParameter='" + conditionParameter + '\'' +
                ", conditionValue=" + conditionValue +
                ", conditionOperator='" + conditionOperator + '\'' +
                '}';
    }
}
