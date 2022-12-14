package year2022.puzzle13;

import org.apache.commons.lang3.StringUtils;
import org.javatuples.Triplet;
import util.Utils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//https://github.com/abnew123/aoc2022/blob/main/src/aoc2022/Day13.java
public class Puzzle13B {

    private static final List<Triplet<Packet, Packet, Integer>> signals = new ArrayList<>();
    private static final String dividerOne = "[[2]]";
    private static final String dividerTwo = "[[6]]";
    private static int pairIndex = 0;

    public static void main(String[] args) {
        List<String> input = Utils.getInput("2022/input13.txt");

        int[] splitIndexes = Utils.getSplitIndexes(input);

        IntStream.range(0, splitIndexes.length - 1)
                .mapToObj(i -> input.subList(splitIndexes[i] + 1, splitIndexes[i + 1]))
                .forEach(signalPair -> signals.add(Triplet.with(new Packet(signalPair.get(0)),
                        new Packet(signalPair.get(1)), ++pairIndex)));

        System.out.println(signals.stream()
                .filter(s -> s.getValue0().compareTo(s.getValue1()) > 0)
                .map(Triplet::getValue2)
                .reduce(0, Integer::sum));

        List<Packet> packets = input.stream()
                .filter(s -> !StringUtils.isEmpty(s))
                .map(Packet::new)
                .collect(Collectors.toList());
        packets.add(new Packet(dividerOne));
        packets.add(new Packet(dividerTwo));

        packets = packets.stream()
                .sorted(Comparator.reverseOrder())
                .toList();

        int answerB = 1;
        for (int i = 0; i < packets.size(); i++) {
            if (packets.get(i).str.equals(dividerOne)) {
                answerB *= (i + 1);
            }
            if (packets.get(i).str.equals(dividerTwo)) {
                answerB *= (i + 1);
            }
        }
        System.out.println(answerB);
    }

    static class Packet implements Comparable<Packet> {
        List<Packet> children;
        int val;
        boolean integer = true;
        String str;

        public Packet(String packet) {
            str = packet;
            children = new ArrayList<>();
            if (packet.equals("[]")) {
                val = -1;
            }
            if (!packet.startsWith("[")) {
                val = Integer.parseInt(packet);
            } else {
                packet = packet.substring(1, packet.length() - 1);
                int level = 0;
                String tmp = "";
                for (char c : packet.toCharArray()) {
                    if (c == ',' && level == 0) {
                        children.add(new Packet(tmp));
                        tmp = "";
                    } else {
                        level += (c == '[') ? 1 : (c == ']') ? -1 : 0;
                        tmp += c;
                    }
                }
                if (!tmp.equals("")) {
                    children.add(new Packet(tmp));
                }
                integer = false;
            }
        }

        public int compareTo(@Nonnull Packet other) {
            if (integer && other.integer) {
                return other.val - val;
            }
            if (!integer && !other.integer) {
                for (int i = 0; i < Math.min(children.size(), other.children.size()); i++) {
                    int val = children.get(i).compareTo(other.children.get(i));
                    if (val != 0) {
                        return val;
                    }
                }
                return other.children.size() - children.size();
            }
            Packet lst1 = integer ? new Packet("[" + val + "]") : this;
            Packet lst2 = other.integer ? new Packet("[" + other.val + "]") : other;
            return lst1.compareTo(lst2);
        }
    }
}
