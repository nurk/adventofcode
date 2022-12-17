package year2022.puzzle16;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.text.NumberFormat;
import java.util.*;

public class Puzzle16 {
    static List<Valve> valves;

    public static void main(String[] args) {
        valves = Utils.getInput("2022/input16.txt", Valve::new);

        Random random = new Random();
        NumberFormat formatter = NumberFormat.getInstance(Locale.forLanguageTag("nl-BE"));

        int maxReleased = 0;
        long iterations = 0;
        // 1938 too low
        // 1952 too low
        // 1954
        // 2032
        // 2038
        // 2091
        // 2101
        // 2159 too low
        // 2160 not correct
        while (iterations < 10000000000L) {
            iterations++;
            valves.forEach(v -> v.isOpen = false);

            int minute = 0;
            int pressureRelease = 0;
            Valve currentValve = getValve("AA");
            List<Valve> visitedValved = new ArrayList<>();
            while (minute <= 30) {
                visitedValved.add(currentValve);
                if (currentValve.canBeOpened() && random.nextBoolean()) {
                    currentValve.open();
                    pressureRelease = pressureRelease +
                            (30 - (minute + 1)) * currentValve.flowRate;
                } else {
                    String newValve = currentValve.leadsTo.get(random.nextInt(currentValve.leadsTo.size()));
                    currentValve = getValve(newValve);
                }
                minute++;
            }
            if (pressureRelease > maxReleased) {
                maxReleased = pressureRelease;
                System.out.println(maxReleased);
                System.out.println(formatter.format(iterations));
                visitedValved.forEach(System.out::println);
            }
        }
        System.out.println(maxReleased);
        System.out.println(formatter.format(iterations));
    }

    static Valve getValve(String n) {
        return valves.stream()
                .filter(v -> v.name.equals(n))
                .findFirst()
                .orElseThrow();
    }

    static class Valve {
        private final Integer flowRate;
        private final String name;
        private final List<String> leadsTo;
        private boolean isOpen;

        public Valve(String s) {
            name = StringUtils.substringBetween(s, "Valve ", " has flow rate");
            flowRate = Integer.parseInt(StringUtils.substringBetween(s, "flow rate=", ";"));
            if (s.contains("valves")) {
                leadsTo = Arrays.stream(StringUtils.substringAfter(s, "to valves ").split(", ")).toList();
            } else {
                leadsTo = Arrays.stream(StringUtils.substringAfter(s, "to valve ").split(", ")).toList();
            }
            isOpen = false;
        }

        @Override
        public String toString() {
            return "Valve{" +
                    "flowRate=" + flowRate +
                    "isOpen=" + isOpen +
                    ", name='" + name + '\'' +
                    ", leadsTo=" + String.join(",", leadsTo) +
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

        public boolean canBeOpened() {
            return !isOpen && flowRate > 0;
        }

        public void open() {
            this.isOpen = true;
        }
    }
}
