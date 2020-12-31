package year2020.puzzle19;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Rules {
    private Map<Integer, Rule> rules = new HashMap<>();
    private Rule ruleZero;

    public Rules(List<String> rulesString) {
        for (String s : rulesString) {
            rules.put(Integer.parseInt(s.split(": ")[0]), new Rule(s.split(": ")[1]));
        }

        ruleZero = rules.get(0);

        while (!ruleZero.isFullyParsed()) {
            ruleZero.parse(rules);
        }
    }

    public List<String> getValidMessages() {
        return Arrays.stream(ruleZero.getRule().split(" \\| ")).map(s -> StringUtils.remove(s, " ")).collect(Collectors.toList());
    }

    private static class Rule {
        private String rule;

        public Rule(String rule) {
            this.rule = StringUtils.remove(rule, "\"");
        }

        public boolean isFullyParsed() {
            for (char c : rule.toCharArray()) {
                if (Character.isDigit(c)) {
                    return false;
                }
            }
            return true;
        }

        public void parse(Map<Integer, Rule> rules) {
            String newRule = rule;
            for (char c : rule.toCharArray()) {
                if (Character.isDigit(c) && StringUtils.contains(newRule, String.valueOf(c))) {
                    String subRule = "";
                    Rule otherRule = rules.get(Integer.parseInt(String.valueOf(c)));
                    for (String orPart : otherRule.getOrParts()) {
                        subRule = subRule + StringUtils.replace(newRule, String.valueOf(c), orPart) + " | ";
                    }
                    newRule = StringUtils.removeEnd(subRule, " | ");
                }
            }
            this.rule = newRule;
        }

        public String getRule() {
            return rule;
        }

        public boolean hasOr() {
            return rule.contains("|");
        }

        public String[] getOrParts() {
            return rule.split(" \\| ");
        }

        @Override
        public String toString() {
            return "Rule{" +
                "rule='" + rule + '\'' +
                '}';
        }
    }
}
