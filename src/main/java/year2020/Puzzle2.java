package year2020;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Puzzle2 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String tst = "1-4 m: mrfmmbjxr";
        List<String> lines = new ArrayList<>();
        Files.readAllLines(Utils.getInputPath("2020/input2.txt")).forEach(
                line -> {
                    lines.add(line);
                }
        );

        int validPasswords = 0;
        for (String line : lines) {
            if (isLineValid(line)) {
                validPasswords++;
            }
        }

        System.out.println(validPasswords);

        int validPasswords2 = 0;
        for (String line : lines) {
            if (isLineValid2(line)) {
                validPasswords2++;
            }
        }

        System.out.println(validPasswords2);
    }

    public static boolean isLineValid(String line) {
        String[] split = line.split(":");
        String[] rules = split[0].split(" ");
        String[] boundaries = rules[0].split("-");

        int minimun = Integer.valueOf(boundaries[0]);
        int maximum = Integer.valueOf(boundaries[1]);
        String letter = rules[1];
        String password = split[1].trim();

        String letters = StringUtils.removePattern(password, "[^" + letter + "]");

        return letters.length() >= minimun && letters.length() <= maximum;
    }

    public static boolean isLineValid2(String line) {
        String[] split = line.split(":");
        String[] rules = split[0].split(" ");
        String[] boundaries = rules[0].split("-");

        int pos1 = Integer.valueOf(boundaries[0]) - 1;
        int pos2 = Integer.valueOf(boundaries[1]) - 1;
        String letter = rules[1];
        String password = split[1].trim();

        return (String.valueOf(password.charAt(pos1)).equals(letter) && !String.valueOf(password.charAt(pos2)).equals(letter)) ||
                (!String.valueOf(password.charAt(pos1)).equals(letter) && String.valueOf(password.charAt(pos2)).equals(letter));
    }
}
