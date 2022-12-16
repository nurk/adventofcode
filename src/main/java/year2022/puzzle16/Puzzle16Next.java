package year2022.puzzle16;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class Puzzle16Next {
    static List<Valve> valves;
    static Long openableValves = 0L;


    //first visit of valve C it does not get opened GRRRRRRRRR.
    //so valves do not necessarily get opened on visit.
    public static void main(String[] args) {
        valves = Utils.getInput("2022/input16test.txt", Valve::new);
        openableValves = valves.stream().filter(v -> v.flowRate > 0).count();
        Set<Path> paths = new HashSet<>();
        Path e = new Path();
        e.addValve(getValve("AA"));
        paths.add(e);

        boolean foundAllValvesOpen = false;
        for (int i = 29; i >= 3; i--) {
            System.out.println(i + " " + paths.size());
            Set<Path> newPaths = new HashSet<>();
            for (Path path : paths) {
                //if (path.openValves.size() == openableValves) {
                if (false) {
                    foundAllValvesOpen = true;
                    break;
                } else {
                    Valve currentValve = path.currentValve();
                    if (!path.isValveOpen(currentValve) && currentValve.flowRate > 0) {
                        path.openValve(currentValve, i);
                        newPaths.add(path);

                    } else {
                        currentValve.leadsTo
                                .forEach(v -> {
                                    Path newPath = path.duplicate();
                                    newPath.addValve(getValve(v));
                                    if (!newPath.isLooped()) {
                                    //if (true) {
                                        newPaths.add(newPath);
                                    }
                                });
                    }
                }
            }
            if (foundAllValvesOpen) {
                break;
            }
            paths = newPaths;
        }

        Long max = paths.stream()
                .map(p -> p.pressures)
                .max(Long::compareTo)
                .orElseThrow();
        System.out.println(max);
        paths.stream()
                .filter(p -> p.pressures == max)
                .forEach(p -> System.out.println(p.visitedValves.stream()
                        .map(v -> v.name)
                        .collect(Collectors.joining(","))));
    }


    static Valve getValve(String n) {
        return valves.stream()
                .filter(v -> v.name.equals(n))
                .findFirst()
                .orElseThrow();
    }

    static class Path {
        private ArrayDeque<Valve> visitedValves = new ArrayDeque<>();
        private List<String> openValves = new ArrayList<>();
        private Long pressures = 0L;

        public Path(ArrayDeque<Valve> visitedValves, List<String> openValves, Long pressures) {
            this.visitedValves = visitedValves;
            this.openValves = openValves;
            this.pressures = pressures;
        }

        public Path() {
        }

        void addValve(Valve v) {
            visitedValves.push(v);
        }

        boolean isValveOpen(Valve v) {
            return openValves.contains(v.name);
        }

        boolean isLooped() {
            List<Valve> tmp = visitedValves.stream().toList();
            if (tmp.size() > 3) {
                if (tmp.get(0).name.equals(tmp.get(2).name) && tmp.get(1).name.equals(tmp.get(3).name)) {
                    return true;
                }
            }
            return false;
        }

        void openValve(Valve v, int minutes) {
            openValves.add(v.name);
            pressures = pressures + (long) v.flowRate * minutes;
        }

        Valve currentValve() {
            return visitedValves.peek();
        }

        Path duplicate() {
            return new Path(visitedValves.clone(), new ArrayList<>(openValves), pressures);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Path path = (Path) o;

            return visitedValves.equals(path.visitedValves);
        }

        @Override
        public int hashCode() {
            return visitedValves.hashCode();
        }
    }

    static class Valve {
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
                    "flowRate=" + flowRate +
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
    }
}
