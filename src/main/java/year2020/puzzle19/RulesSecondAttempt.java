package year2020.puzzle19;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RulesSecondAttempt {
    private Map<Integer, Rule> rules = new HashMap<>();
    private Rule ruleZero;

    public RulesSecondAttempt(List<String> rulesString) {
        for (String s : rulesString) {
            rules.put(Integer.parseInt(s.split(": ")[0]), new Rule(s.split(": ")[1]));
        }

        ruleZero = rules.get(0);

        while (!ruleZero.isFullyParsed()) {
            System.out.println(ruleZero.ruleElements.size());
            ruleZero.parse(rules);
        }
    }

    public List<String> getValidMessages() {
        return ruleZero.ruleElements.stream().map(s -> StringUtils.remove(s.getElement(), " ")).collect(Collectors.toList());
    }

    private static class Rule {
        private List<RuleElement> ruleElements;

        public Rule(String rule) {
            this.ruleElements = Arrays.stream(StringUtils.remove(rule, "\"").split(" \\| ")).map(RuleElement::new).collect(Collectors.toList());

        }

        public boolean isFullyParsed() {
            for (RuleElement ruleElement : ruleElements) {
                for (char c : ruleElement.getElement().toCharArray()) {
                    if (Character.isDigit(c)) {
                        return false;
                    }
                }
            }
            return true;
        }

        public void parse(Map<Integer, Rule> rules) {
            for (int j = ruleElements.size() - 1; j >= 0; j--) {
                RuleElement ruleElement = ruleElements.get(j);
                for (String c : ruleElement.getElement().split(" ")) {
                    if (StringUtils.isNumeric(c) && StringUtils.contains(ruleElement.getElement(), " " + c + " ")) {
                        Rule otherRule = rules.get(Integer.parseInt(c));
                        String originalRuleElementText = ruleElement.getElement();
                        for (int i = 0; i < otherRule.ruleElements.size(); i++) {
                            if (i == 0) {
                                ruleElement.setElement(StringUtils.replace(originalRuleElementText,
                                    " " + c + " ",
                                    " " + otherRule.ruleElements.get(i).getElement()) + " ");
                            } else {
                                ruleElements.add(new RuleElement(StringUtils.replace(originalRuleElementText, c,
                                    otherRule.ruleElements.get(i).getElement())));
                            }
                        }
                    }
                }
                System.out.println(ruleElements.size());
            }
        }
    }

    private static class RuleElement {
        private String element;

        public RuleElement(String element) {
            this.element = " " + element + " ";
        }

        public String getElement() {
            return element;
        }

        public void setElement(String element) {
            this.element = element;
        }
    }
}
