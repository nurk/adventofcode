package year2022.puzzle19;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import util.Utils;

import java.util.*;
import java.util.stream.IntStream;

// https://github.com/RyanFoulds/AdventOfCode22/blob/main/Day19/src/main/java/xyz/foulds/aoc/year22/day19/Blueprint.java
// cheated part A and B (heavily inspired.  after doing day 24 I think I could have figured this out but I already looked at this is it is cheating)
public class Puzzle19 {

    static List<Blueprint> blueprints;

    // Part A: 1589
    // Part B: 29348
    public static void main(String[] args) {
        blueprints = Utils.getInput("2022/input19.txt", Blueprint::new);

        StopWatch s = StopWatch.createStarted();
        System.out.println("Part A: " + blueprints.stream()
                .map(blueprint -> blueprint.getQualityLevel(24))
                .reduce(0, Integer::sum));
        s.split();
        System.out.println(s.formatSplitTime());
        System.out.println("Part B: " + blueprints.stream()
                .limit(3)
                .map(blueprint -> blueprint.getMaxGeodeCount(32))
                .reduce(1, (a, b) -> a * b));
        s.split();
        System.out.println(s.formatSplitTime());
    }

    static class State {
        final int oreBots;
        final int clayBots;
        final int obsidianBots;
        final int geodeBots;
        final int ores;
        final int clays;
        final int obsidians;
        final int geodes;
        final int minute;
        final Blueprint blueprint;

        public State(int oreBots,
                     int clayBots,
                     int obsidianBots,
                     int geodeBots,
                     int ores,
                     int clays,
                     int obsidians,
                     int geodes,
                     int minute,
                     Blueprint blueprint) {
            this.oreBots = oreBots;
            this.clayBots = clayBots;
            this.obsidianBots = obsidianBots;
            this.geodeBots = geodeBots;
            this.ores = ores;
            this.clays = clays;
            this.obsidians = obsidians;
            this.geodes = geodes;
            this.minute = minute;
            this.blueprint = blueprint;
        }

        public State(Blueprint blueprint) {
            oreBots = 1;
            clayBots = 0;
            obsidianBots = 0;
            geodeBots = 0;
            ores = 0;
            clays = 0;
            obsidians = 0;
            geodes = 0;
            minute = 0;
            this.blueprint = blueprint;
        }

        @Override
        public String toString() {
            return "State{" +
                    "oreBots=" + oreBots +
                    ", clayBots=" + clayBots +
                    ", obsidianBots=" + obsidianBots +
                    ", geodeBots=" + geodeBots +
                    ", ores=" + ores +
                    ", clays=" + clays +
                    ", obsidians=" + obsidians +
                    ", geodes=" + geodes +
                    ", minute=" + minute +
                    ", blueprint=" + blueprint +
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

            State state = (State) o;

            if (oreBots != state.oreBots) {
                return false;
            }
            if (clayBots != state.clayBots) {
                return false;
            }
            if (obsidianBots != state.obsidianBots) {
                return false;
            }
            if (geodeBots != state.geodeBots) {
                return false;
            }
            if (ores != state.ores) {
                return false;
            }
            if (clays != state.clays) {
                return false;
            }
            if (obsidians != state.obsidians) {
                return false;
            }
            if (geodes != state.geodes) {
                return false;
            }
            if (minute != state.minute) {
                return false;
            }
            return blueprint.equals(state.blueprint);
        }

        @Override
        public int hashCode() {
            int result = oreBots;
            result = 31 * result + clayBots;
            result = 31 * result + obsidianBots;
            result = 31 * result + geodeBots;
            result = 31 * result + ores;
            result = 31 * result + clays;
            result = 31 * result + obsidians;
            result = 31 * result + geodes;
            result = 31 * result + minute;
            result = 31 * result + blueprint.hashCode();
            return result;
        }

        public Set<State> getNewStates() {
            Set<State> newStates = new HashSet<>();

            newStates.add(new State(oreBots,
                    clayBots,
                    obsidianBots,
                    geodeBots,
                    ores + oreBots,
                    clays + clayBots,
                    obsidians + obsidianBots,
                    geodes + geodeBots,
                    minute + 1,
                    blueprint));

            if (ores >= blueprint.oreBotCost && oreBots < blueprint.maxUsefulOreBots) {
                newStates.add(new State(oreBots + 1,
                        clayBots,
                        obsidianBots,
                        geodeBots,
                        ores + oreBots - blueprint.oreBotCost,
                        clays + clayBots,
                        obsidians + obsidianBots,
                        geodes + geodeBots,
                        minute + 1,
                        blueprint));
            }

            if (ores >= blueprint.clayBotCost && clayBots < blueprint.maxUseFulClayBots) {
                newStates.add(new State(oreBots,
                        clayBots + 1,
                        obsidianBots,
                        geodeBots,
                        ores + oreBots - blueprint.clayBotCost,
                        clays + clayBots,
                        obsidians + obsidianBots,
                        geodes + geodeBots,
                        minute + 1,
                        blueprint));
            }

            if (ores >= blueprint.obsidianBotOreCost && clays >= blueprint.obsidianBotClayCost && obsidianBots < blueprint.maxUsefulObsidianBots) {
                newStates.add(new State(oreBots,
                        clayBots,
                        obsidianBots + 1,
                        geodeBots,
                        ores + oreBots - blueprint.obsidianBotOreCost,
                        clays + clayBots - blueprint.obsidianBotClayCost,
                        obsidians + obsidianBots,
                        geodes + geodeBots,
                        minute + 1,
                        blueprint));
            }

            if (ores >= blueprint.geodeBotOreCost && obsidians >= blueprint.geodeBotObsidianCost) {
                newStates.add(new State(oreBots,
                        clayBots,
                        obsidianBots,
                        geodeBots + 1,
                        ores + oreBots - blueprint.geodeBotOreCost,
                        clays + clayBots,
                        obsidians + obsidianBots - blueprint.geodeBotObsidianCost,
                        geodes + geodeBots,
                        minute + 1,
                        blueprint));
            }

            return newStates;
        }

        public int getMaxPossibleGeodeCount(int totalMinutes) {
            // this is a value to determine the viability of spawned states
            final int minutesRemaining = totalMinutes - minute;
            return ((minutesRemaining * (minutesRemaining + 1)) / 2) + minutesRemaining * geodeBots + geodes;
        }
    }

    static class Blueprint {
        final int id;
        final int oreBotCost;
        final int clayBotCost;
        final int obsidianBotOreCost;
        final int obsidianBotClayCost;
        final int geodeBotOreCost;
        final int geodeBotObsidianCost;
        final int maxUsefulOreBots;
        final int maxUsefulObsidianBots;
        final int maxUseFulClayBots;


        Blueprint(String line) {
            String[] split = line.split(": ");
            List<String> lines = new ArrayList<>();
            lines.add(split[0]);
            lines.addAll(Arrays.asList(split[1].split("\\. ")));
            id = Integer.parseInt(StringUtils.substringAfter(lines.get(0), "Blueprint "));
            oreBotCost = Integer.parseInt(StringUtils.substringBetween(lines.get(1), "costs ", " ore"));
            clayBotCost = Integer.parseInt(StringUtils.substringBetween(lines.get(2), "costs ", " ore"));
            obsidianBotOreCost = Integer.parseInt(StringUtils.substringBetween(lines.get(3), "costs ", " ore"));
            obsidianBotClayCost = Integer.parseInt(StringUtils.substringBetween(lines.get(3), "and ", " clay"));
            geodeBotOreCost = Integer.parseInt(StringUtils.substringBetween(lines.get(4), "costs ", " ore"));
            geodeBotObsidianCost = Integer.parseInt(StringUtils.substringBetween(lines.get(4), "and ", " obsidian"));

            maxUsefulOreBots = IntStream.of(oreBotCost, clayBotCost, obsidianBotOreCost, geodeBotOreCost)
                    .max()
                    .getAsInt();
            maxUsefulObsidianBots = geodeBotObsidianCost;
            maxUseFulClayBots = obsidianBotClayCost;
        }

        int getMaxGeodeCount(final int totalMinutes) {
            return findMaxGeodeCount(totalMinutes);
        }

        private int findMaxGeodeCount(int totalMinutes) {
            // this is LIFO
            // we want to get to an end fast because we want to limit the number of viable new states
            Deque<State> toCheck = new ArrayDeque<>();
            Set<State> seen = new HashSet<>();
            int maxGeodeCount = 0;

            toCheck.addFirst(new State(this));

            while (!toCheck.isEmpty()) {
                State current = toCheck.pop();
                seen.add(current);

                if (current.minute < totalMinutes) {
                    for (State newState : current.getNewStates()) {
                        if (newState.getMaxPossibleGeodeCount(totalMinutes) > maxGeodeCount && !seen.contains(newState)) {
                            toCheck.addFirst(newState);
                        }
                    }
                } else if (current.minute == totalMinutes) {
                    maxGeodeCount = Math.max(maxGeodeCount, current.geodes);
                }
            }

            return maxGeodeCount;
        }

        int getQualityLevel(final int minute) {
            return id * getMaxGeodeCount(minute);
        }

        @Override
        public String toString() {
            return "Blueprint{" +
                    "id=" + id +
                    ", oreBotCost=" + oreBotCost +
                    ", clayBotCost=" + clayBotCost +
                    ", obsidianBotOreCost=" + obsidianBotOreCost +
                    ", obsidianBotClayCost=" + obsidianBotClayCost +
                    ", geodeBotOreCost=" + geodeBotOreCost +
                    ", geodeBotObsidianCost=" + geodeBotObsidianCost +
                    '}';
        }
    }
}
