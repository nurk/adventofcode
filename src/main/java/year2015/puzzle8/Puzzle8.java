package year2015.puzzle8;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import util.Utils;

import java.util.ArrayList;
import java.util.List;

public class Puzzle8 {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2015/input8.txt", (s) -> s));

        var lengthPartA = new Object() {
            int codeLength = 0;
            int memoryLength = 0;
        };

        input.stream()
                .peek(s -> lengthPartA.codeLength += s.length())
                .map(s -> StringUtils.removeStart(s, "\""))
                .map(s -> StringUtils.removeEnd(s, "\""))
                .map(s -> s.replaceAll("\\\\x[0-9a-fA-F][0-9a-fA-F]", "a"))
                .map(StringEscapeUtils::unescapeJava)
                .forEach(s -> lengthPartA.memoryLength += s.length());

        System.out.println(lengthPartA.codeLength - lengthPartA.memoryLength);

        var lengthPartB = new Object() {
            int before = 0;
            int after = 0;
        };

        input.stream()
                .peek(s -> lengthPartB.before += s.length())
                .map(StringEscapeUtils::escapeJava)
                .map(s -> "\"" + s + "\"")
                .forEach(s -> lengthPartB.after += s.length());

        System.out.println(lengthPartB.after - lengthPartB.before);
    }
}
