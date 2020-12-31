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
                for (String c : ruleElement.getElement().split(" ")) {
                    if (StringUtils.isNumeric(c) ) {
                        return false;
                    }
                }
            }
            return true;
        }

        public void parse(Map<Integer, Rule> rules) {
            for (int j = ruleElements.size() - 1; j >= 0; j--) {
                RuleElement ruleElement = ruleElements.get(j);
                String originalRuleElementText = ruleElement.getElement();
                for (String c : originalRuleElementText.split(" ")) {
                    if (StringUtils.isNumeric(c)) {
                        Rule otherRule = rules.get(Integer.parseInt(c));
                        String someotherOriginalRuleElementText = ruleElement.getElement();
                        for (int i = 0; i < otherRule.ruleElements.size(); i++) {
                            if (i == 0) {
                                String replacement = StringUtils.replace(someotherOriginalRuleElementText,
                                    " " + c + " ",
                                    " " + otherRule.ruleElements.get(i).getElement()) + " ";
                                ruleElement.setElement(replacement);
                            } else {
                                ruleElements.add(new RuleElement(StringUtils.replace(someotherOriginalRuleElementText,
                                    " " + c + " ",
                                    " " + otherRule.ruleElements.get(i).getElement() + " ")));
                            }
                        }
                    }
                }
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

        @Override
        public String toString() {
            return "RuleElement{" +
                "element='" + element + '\'' +
                '}';
        }
    }
}
