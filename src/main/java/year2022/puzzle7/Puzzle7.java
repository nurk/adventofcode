package year2022.puzzle7;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Puzzle7 {

    private static final Map<String, Long> DIRECTORY_SIZES = new HashMap<>();
    private static final ArrayDeque<String> CURRENT_DIRECTORY = new ArrayDeque<>();
    private static final Long TOTAL_DISK_SPACE = 70000000L;
    private static final Long UNUSED_SPACE_NEEDED = 30000000L;

    public static void main(String[] args) {
        Utils.getInput("2022/input7.txt")
                .forEach(data -> {
                    if (StringUtils.startsWith(data, "$ cd ")) {
                        String directory = StringUtils.substringAfter(data, "$ cd ");
                        if ("..".equals(directory)) {
                            CURRENT_DIRECTORY.pop();
                        } else if (directory.equals("/")) {
                            CURRENT_DIRECTORY.clear();
                            CURRENT_DIRECTORY.push("root");
                        } else {
                            CURRENT_DIRECTORY.push(directory);
                        }
                    }
                    if (StringUtils.startsWith(data, "dir ")) {
                        DIRECTORY_SIZES.merge(queueToFqn(CURRENT_DIRECTORY), 0L, Long::sum);
                    }

                    if (StringUtils.isNumeric(data.split(" ")[0])) {
                        Deque<String> directories = CURRENT_DIRECTORY.clone();
                        while (directories.size() != 0) {
                            DIRECTORY_SIZES.merge(queueToFqn(directories), Long.valueOf(data.split(" ")[0]), Long::sum);
                            directories.pop();
                        }
                    }
                });

        System.out.println("PartA: " + DIRECTORY_SIZES.values().stream()
                .filter(val -> val <= 100000)
                .reduce(0L, Long::sum));

        Long spaceToFreeUp = UNUSED_SPACE_NEEDED - (TOTAL_DISK_SPACE - DIRECTORY_SIZES.get("root"));
        System.out.println("PartB: " + DIRECTORY_SIZES.values().stream()
                .filter(size -> size >= spaceToFreeUp)
                .sorted()
                .findFirst()
                .orElse(0L));

    }

    private static String queueToFqn(Deque<String> queue) {
        return String.join("/", queue);
    }
}
