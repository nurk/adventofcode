package year2016.puzzle7;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class Puzzle7 {
    public static void main(String[] args) {
        System.out.println(Utils.getInput("2016/input7.txt").stream()
                .filter(Puzzle7::supportsTls)
                .count());
        System.out.println(Utils.getInput("2016/input7.txt").stream()
                .filter(Puzzle7::supportsSsl)
                .count());
    }

    private static boolean supportsTls(String input) {
        String[] hypernets = getHypernets(input);
        if (Arrays.stream(hypernets)
                .anyMatch(Puzzle7::containsAbba)) {
            return false;
        }

        String[] supernets = getSupernets(input, hypernets);
        return Arrays.stream(supernets).anyMatch(Puzzle7::containsAbba);
    }

    private static boolean supportsSsl(String input) {
        String[] hypernets = getHypernets(input);
        String[] supernets = getSupernets(input, hypernets);

        return Arrays.stream(supernets)
                .anyMatch(supernet -> getInvertedAbas(supernet).stream()
                        .anyMatch(invertedAba -> Arrays.stream(hypernets)
                                .anyMatch(hypernet -> hypernet.contains(invertedAba))));

    }

    private static String[] getSupernets(String input, String[] hypernets) {
        AtomicReference<String> superNet = new AtomicReference<>(input);
        Arrays.stream(hypernets).forEach(
                hypernet -> superNet.set(StringUtils.replace(superNet.get(), "[" + hypernet + "]", "|"))
        );
        return superNet.get().split("\\|");
    }

    private static String[] getHypernets(String input) {
        return StringUtils.substringsBetween(input, "[", "]");
    }

    private static boolean containsAbba(String input) {
        if (input.length() < 4) {
            return false;
        }
        for (int i = 0; i < input.length() - 3; i++) {
            if (isAbba(input.substring(i, i + 4))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAbba(String input) {
        String[] split = input.split("");
        return split[0].equals(split[3]) && split[1].equals(split[2]) && !split[0].equals(split[1]);
    }

    private static Set<String> getInvertedAbas(String input) {
        if (input.length() < 3) {
            return Set.of();
        }
        Set<String> invertedAbas = new HashSet<>();
        for (int i = 0; i < input.length() - 2; i++) {
            String test = input.substring(i, i + 3);
            if (isAba(test)) {
                String[] split = test.split("");
                invertedAbas.add(split[1] + split[0] + split[1]);
            }
        }
        return invertedAbas;
    }

    private static boolean isAba(String input) {
        String[] split = input.split("");
        return split[0].equals(split[2]) && !split[0].equals(split[1]);
    }
}
