package year2018.puzzle4;

import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;
import static year2018.puzzle4.Line.Action.*;

public class Puzzle4 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        List<Line> lines =
                Files.readAllLines(Utils.getInputPath("2018/input4.txt"))
                        .stream()
                        .map(Line::new)
                        .sorted(comparing(Line::getDateTime))
                        .collect(toList());

        Collection<Guard> guards = convertToGuards(lines);

        System.out.println(guards
                .stream()
                .max(comparing(Guard::getTotalSleepTime))
                .orElseThrow()
                .getSolution());

        System.out.println(guards
                .stream()
                .max(comparing(o -> o.minuteMostASleep().getRight()))
                .orElseThrow().getSolution());

    }

    private static Collection<Guard> convertToGuards(List<Line> lines) {
        Map<Integer, Guard> guards = new HashMap<>();

        Guard currentGuard = new Guard(0);

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).getAction() == START) {
                currentGuard = guards.computeIfAbsent(lines.get(i).getGuard(), Guard::new);
            }
            if (lines.get(i).getAction() == SLEEP) {
                currentGuard.addSleepPeriod(new SleepPeriod(lines.get(i).getDateTime(), lines.get(++i).getDateTime()));
            }
        }
        return guards.values();
    }
}
