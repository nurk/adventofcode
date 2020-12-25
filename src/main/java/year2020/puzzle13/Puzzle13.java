package year2020.puzzle13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Puzzle13 {
    public static void main(String[] args) {
        int earliestDepartTime = 1000655;
        List<Integer> ids = Arrays.stream(
                "17,x,x,x,x,x,x,x,x,x,x,37,x,x,x,x,x,571,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,13,x,x,x,x,23,x,x,x,x,x,29,x,401,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,19"
                        .split(","))
                .filter(i -> !i.equals("x"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        List<Integer> testIds = Arrays.stream(
                "7,13,x,x,59,x,31,19"
                        .split(","))
                .filter(i -> !i.equals("x"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        solveA(earliestDepartTime, ids);
        solveB("17,x,x,x,x,x,x,x,x,x,x,37,x,x,x,x,x,571,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,13,x,x,x,x,23,x,x,x,x,x,29,x,401,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,19");
    }

    private static void solveA(int earliestDepartTime, List<Integer> ids) {
        SortedMap<Integer, Integer> map = new TreeMap<>();
        for (Integer id : ids) {
            map.put(earliestDepartTime + (id - (earliestDepartTime % id)), id);
        }

        System.out.println(map.firstKey() + " " + map.get(map.firstKey()));
        System.out.println((map.firstKey() - earliestDepartTime) * map.get(map.firstKey()));
    }

    private static void solveB(String input) {
        final List<Long> ids = new ArrayList<>();
        for (String id : input.split(",")) {
            ids.add(id.equals("x") ? -1 : Long.parseLong(id));
        }

        long lcm = -1, time = -1;
        int index = 0;
        while (true) {
            final long id = ids.get(index);
            if (id == -1) {
                index++;
                continue;
            }

            if (lcm == -1) {
                lcm = id;
                time = id - index;
                index++;
                continue;
            }

            if ((time + index) % id == 0) {
                if (++index >= ids.size()) {
                    System.out.println(time);
                    return;
                }

                lcm *= id;
                continue;
            }

            time += lcm;
        }
    }
}
