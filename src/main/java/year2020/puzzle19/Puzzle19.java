package year2020.puzzle19;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Puzzle19 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> input = new ArrayList<>(Files.readAllLines(Utils.getInputPath("2020/input19.txt")));

        List<String> rulesStrings = new ArrayList<>();
        List<String> messages = new ArrayList<>();

        boolean seenEmptyLine = false;
        for (String s : input) {
            if (StringUtils.isEmpty(s)) {
                seenEmptyLine = true;
                continue;
            }
            if (seenEmptyLine) {
                messages.add(s);
            } else {
                rulesStrings.add(s);
            }
        }

        RulesSecondAttempt rules = new RulesSecondAttempt(rulesStrings);
        List<String> validMessages = rules.getValidMessages();

        int sum = 0;
        for (String message : messages) {
            if (validMessages.contains(message)) {
                sum++;
            }
        }
        System.out.println("sum");
        System.out.println(sum);
        System.out.println("");
    }
}
