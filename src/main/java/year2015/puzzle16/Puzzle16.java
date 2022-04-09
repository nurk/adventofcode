package year2015.puzzle16;

import util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Puzzle16 {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2015/input16.txt", (s) -> s));


        partA(new ArrayList<>(input));
        partB(new ArrayList<>(input));
    }

    private static void partA(List<String> input) {
        for (int i = input.size() - 1; i >= 0; i--) {
            String s = input.get(i);
            Matcher matcher = Pattern.compile(".*(children: )([0-9]+).*", Pattern.DOTALL).matcher(s);
            if (matcher.matches() && !matcher.group(2).equals("3")) {
                input.remove(i);
                continue;
            }
            matcher = Pattern.compile(".*(cats: )([0-9]+).*", Pattern.DOTALL).matcher(s);
            if (matcher.matches() && !matcher.group(2).equals("7")) {
                input.remove(i);
                continue;
            }
            matcher = Pattern.compile(".*(samoyeds: )([0-9]+).*", Pattern.DOTALL).matcher(s);
            if (matcher.matches() && !matcher.group(2).equals("2")) {
                input.remove(i);
                continue;
            }

            matcher = Pattern.compile(".*(pomeranians: )([0-9]+).*", Pattern.DOTALL).matcher(s);
            if (matcher.matches() && !matcher.group(2).equals("3")) {
                input.remove(i);
                continue;
            }

            matcher = Pattern.compile(".*(akitas: )([0-9]+).*", Pattern.DOTALL).matcher(s);
            if (matcher.matches() && !matcher.group(2).equals("0")) {
                input.remove(i);
                continue;
            }

            matcher = Pattern.compile(".*(vizslas: )([0-9]+).*", Pattern.DOTALL).matcher(s);
            if (matcher.matches() && !matcher.group(2).equals("0")) {
                input.remove(i);
                continue;
            }

            matcher = Pattern.compile(".*(goldfish: )([0-9]+).*", Pattern.DOTALL).matcher(s);
            if (matcher.matches() && !matcher.group(2).equals("5")) {
                input.remove(i);
                continue;
            }

            matcher = Pattern.compile(".*(trees: )([0-9]+).*", Pattern.DOTALL).matcher(s);
            if (matcher.matches() && !matcher.group(2).equals("3")) {
                input.remove(i);
                continue;
            }

            matcher = Pattern.compile(".*(cars: )([0-9]+).*", Pattern.DOTALL).matcher(s);
            if (matcher.matches() && !matcher.group(2).equals("2")) {
                input.remove(i);
                continue;
            }

            matcher = Pattern.compile(".*(perfumes: )([0-9]+).*", Pattern.DOTALL).matcher(s);
            if (matcher.matches() && !matcher.group(2).equals("1")) {
                input.remove(i);
            }
        }

        input.forEach(System.out::println);
    }

    private static void partB(List<String> input) {
        for (int i = input.size() - 1; i >= 0; i--) {
            String s = input.get(i);
            Matcher matcher = Pattern.compile(".*(children: )([0-9]+).*", Pattern.DOTALL).matcher(s);
            if (matcher.matches() && !matcher.group(2).equals("3")) {
                input.remove(i);
                continue;
            }
            matcher = Pattern.compile(".*(cats: )([0-9]+).*", Pattern.DOTALL).matcher(s);
            if (matcher.matches() && Integer.parseInt(matcher.group(2)) <= 7) {
                input.remove(i);
                continue;
            }
            matcher = Pattern.compile(".*(samoyeds: )([0-9]+).*", Pattern.DOTALL).matcher(s);
            if (matcher.matches() && !matcher.group(2).equals("2")) {
                input.remove(i);
                continue;
            }

            matcher = Pattern.compile(".*(pomeranians: )([0-9]+).*", Pattern.DOTALL).matcher(s);
            if (matcher.matches() && Integer.parseInt(matcher.group(2)) >= 3) {
                input.remove(i);
                continue;
            }

            matcher = Pattern.compile(".*(akitas: )([0-9]+).*", Pattern.DOTALL).matcher(s);
            if (matcher.matches() && !matcher.group(2).equals("0")) {
                input.remove(i);
                continue;
            }

            matcher = Pattern.compile(".*(vizslas: )([0-9]+).*", Pattern.DOTALL).matcher(s);
            if (matcher.matches() && !matcher.group(2).equals("0")) {
                input.remove(i);
                continue;
            }

            matcher = Pattern.compile(".*(goldfish: )([0-9]+).*", Pattern.DOTALL).matcher(s);
            if (matcher.matches() && Integer.parseInt(matcher.group(2)) >= 3) {
                input.remove(i);
                continue;
            }

            matcher = Pattern.compile(".*(trees: )([0-9]+).*", Pattern.DOTALL).matcher(s);
            if (matcher.matches() && Integer.parseInt(matcher.group(2)) < 3) {
                input.remove(i);
                continue;
            }

            matcher = Pattern.compile(".*(cars: )([0-9]+).*", Pattern.DOTALL).matcher(s);
            if (matcher.matches() && !matcher.group(2).equals("2")) {
                input.remove(i);
                continue;
            }

            matcher = Pattern.compile(".*(perfumes: )([0-9]+).*", Pattern.DOTALL).matcher(s);
            if (matcher.matches() && !matcher.group(2).equals("1")) {
                input.remove(i);
            }
        }

        input.forEach(System.out::println);
    }
}
