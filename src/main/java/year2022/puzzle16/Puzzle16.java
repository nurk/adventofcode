package year2022.puzzle16;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.*;

// https://github.com/ash42/adventofcode/blob/main/adventofcode2022/src/nl/michielgraat/adventofcode2022/day16/Day16.java
// cheated part A and B
public class Puzzle16 {
    static List<Valve> valves;
    static Map<State, Integer> cache = new HashMap<>();

    public static void main(String[] args) {
        valves = Utils.getInput("2022/input16.txt", Valve::new);

        System.out.println("Part A: " + calcPressure(getValve("AA"), 30, new TreeSet<>(), valves, 0));
        System.out.println();
        cache.clear();
        System.out.println("Part B: " + calcPressure(getValve("AA"), 26, new TreeSet<>(), valves, 1));
        System.out.println();
    }

    static int calcPressure(final Valve current,
                            final int minute,
                            final SortedSet<Valve> opened,
                            final List<Valve> valves,
                            final int nrOfOtherPlayers) {
        if (minute == 0) {
            final Valve aa = valves.stream().filter(v -> v.name.equals("AA")).findFirst()
                    .orElseThrow(NoSuchElementException::new);
            return nrOfOtherPlayers > 0 ? calcPressure(aa, 26, opened, valves, nrOfOtherPlayers - 1) : 0;
        }
        final State state = new State(current, minute, new ArrayList<>(opened), nrOfOtherPlayers);
        if (cache.containsKey(state)) {
            return cache.get(state);
        }

        int max = 0;
        if (current.flowRate > 0 && !opened.contains(current)) {
            opened.add(current);
            max = (minute - 1) * current.flowRate
                    + calcPressure(current, minute - 1, opened, valves, nrOfOtherPlayers);
            opened.remove(current);
        }

        for (final String n : current.leadsTo) {
            final Valve neighbour = getValve(n);
            max = Math.max(max, calcPressure(neighbour, minute - 1, opened, valves, nrOfOtherPlayers));
        }
        cache.put(state, max);

        return max;
    }

    static Valve getValve(String n) {
        return valves.stream()
                .filter(v -> v.name.equals(n))
                .findFirst()
                .orElseThrow();
    }

    static class Valve implements Comparable<Valve> {
        private final Integer flowRate;
        private final String name;
        private final List<String> leadsTo;

        public Valve(String s) {
            name = StringUtils.substringBetween(s, "Valve ", " has flow rate");
            flowRate = Integer.parseInt(StringUtils.substringBetween(s, "flow rate=", ";"));
            if (s.contains("valves")) {
                leadsTo = Arrays.stream(StringUtils.substringAfter(s, "to valves ").split(", ")).toList();
            } else {
                leadsTo = Arrays.stream(StringUtils.substringAfter(s, "to valve ").split(", ")).toList();
            }
        }

        @Override
        public String toString() {
            return "Valve{" +
                    "name='" + name + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Valve valve = (Valve) o;

            return name.equals(valve.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public int compareTo(Valve o) {
            return Integer.compare(this.flowRate, o.flowRate);
        }
    }

    record State(Valve valve, int minute, List<Valve> openValves, int nrOfOtherPlayers) {
    }
}
