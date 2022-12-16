package year2022.puzzle16;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Puzzle16 {
    public static void main(String[] args) {
        List<Valve> valves = Utils.getInput("2022/input16test.txt", Valve::new);

        Random random = new Random();

        /*int max = IntStream.rangeClosed(0, 10000000)
                .parallel()
                .map(i -> {
                    List<Valve> clonedValves = valves.stream().map(Valve::clone).toList();
                    int minute = 0;
                    int pressureRelease = 0;
                    Valve currentValve = clonedValves.stream()
                            .filter(v -> v.name.equals("AA"))
                            .findFirst()
                            .orElseThrow();
                    //List<Valve> visitedValved = new ArrayList<>();
                    while (minute <= 30) {
                        //visitedValved.add(currentValve);
                        if (currentValve.canBeOpened()) {
                            currentValve.open();
                            pressureRelease = pressureRelease +
                                    (30 - (minute + 1)) * currentValve.flowRate;
                        } else {
                            String newValve = currentValve.leadsTo.get(random.nextInt(currentValve.leadsTo.size()));
                            currentValve = clonedValves.stream()
                                    .filter(v -> v.name.equals(newValve))
                                    .findFirst()
                                    .orElseThrow();
                        }
                        minute++;
                    }
                    return pressureRelease;
                })
                .max()
                .orElseThrow();
        System.out.println(max);*/

        int maxReleased = 0;
        for (int i = 0; i < 40000000; i++) {
            valves.forEach(v -> v.isOpen = false);
            int minute = 0;
            int pressureRelease = 0;
            Valve currentValve = valves.stream()
                    .filter(v -> v.name.equals("AA"))
                    .findFirst()
                    .orElseThrow();
            List<Valve> visitedValved = new ArrayList<>();
            while (minute <= 30) {
                visitedValved.add(currentValve);
                if (currentValve.canBeOpened()) {
                    currentValve.open();
                    pressureRelease = pressureRelease +
                            (30 - (minute + 1)) * currentValve.flowRate;
                } else {
                    String newValve = currentValve.leadsTo.get(random.nextInt(currentValve.leadsTo.size()));
                    currentValve = valves.stream()
                            .filter(v -> v.name.equals(newValve))
                            .findFirst()
                            .orElseThrow();
                }
                minute++;
            }
            if (pressureRelease > maxReleased) {
                maxReleased = pressureRelease;
                System.out.println(maxReleased);
                visitedValved.forEach(System.out::println);
            }
        }
        System.out.println(maxReleased);
    }

    static class Valve {
        private final Integer flowRate;
        private final String name;
        private final List<String> leadsTo;
        private boolean isOpen;
        private final String original;

        public Valve(String s) {
            this.original = s;
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

        public Valve clone() {
            return new Valve(original);
        }
    }
}
