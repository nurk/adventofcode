package year2023.puzzle19;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Workflow implements Acceptable, Followable {

    @Getter
    private final String id;
    private final List<Rule> rules = new ArrayList<>();

    public Workflow(String input) {
        id = input.substring(0, input.indexOf("{"));
        String rulesString = input.substring(input.indexOf("{") + 1, input.indexOf("}"));
        String[] rulesSplit = rulesString.split(",");
        for (String ruleString : rulesSplit) {
            rules.add(new Rule(ruleString));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Workflow workflow = (Workflow) o;
        return id.equals(workflow.id);
    }

    @Override
    public boolean isAcceptable(MachinePart machinePart, Map<String, Workflow> workflows) {
        return rules.stream()
                .filter(r -> r.applies(machinePart))
                .findFirst().orElseThrow().isAcceptable(machinePart, workflows);
    }

    @Override
    public void follow(Map<String, Workflow> workflows, List<List<Rule>> followedRules, List<Rule> currentRules) {
        for (Rule rule : rules) {
            List<Rule> newCurrentRules = new ArrayList<>(currentRules);
            newCurrentRules.add(rule);
            rule.follow(workflows, followedRules, newCurrentRules);
        }
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Workflow{" +
                "id='" + id + '\'' +
                ", rules=" + rules +
                '}';
    }
}
