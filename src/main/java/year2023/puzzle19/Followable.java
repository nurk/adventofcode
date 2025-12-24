package year2023.puzzle19;


import java.util.List;
import java.util.Map;

public interface Followable {
    void follow(Map<String, Workflow> workflows, List<List<Rule>> followedRules, List<Rule> currentRules);
}
