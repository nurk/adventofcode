package year2020.puzzle6;

import java.util.HashMap;
import java.util.Map;

public class Group {
    private int numberOfPersons;
    private Map<Character, Integer> answers = new HashMap<>();

    public void addPerson(String input) {
        numberOfPersons++;

        for (int i = 0; i < input.length(); i++) {
            answers.merge(input.charAt(i), 1, Integer::sum);
        }
    }

    public int getNumberOfUniqueYesAnswers() {
        return answers.keySet().size();
    }

    public int getNumberOfAnswersEveryoneAnsweredYesTo() {
        int count = 0;
        for (Integer value : answers.values()) {
            if (value == numberOfPersons) {
                count++;
            }
        }
        return count;
    }
}
