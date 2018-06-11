package year2016;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Puzzle3 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        AtomicInteger totalTriangles = new AtomicInteger();
        Files.readAllLines(Utils.getInputPath("2016/input3.txt")).forEach(
                line -> {
                    List<Integer> sides = new ArrayList<>();
                    Arrays.stream(StringUtils.split(line, " ")).forEach(part -> sides.add(Integer.valueOf(StringUtils.trim(part))));
                    System.out.println(sides);
                    if (((sides.get(0) + sides.get(1)) > sides.get(2)) && ((sides.get(0) + sides.get(2)) > sides.get(1)) && ((sides.get(2) + sides.get(1)) > sides.get(0))) {
                        totalTriangles.getAndIncrement();
                    }
                }
        );
        System.out.println(totalTriangles);
    }
}
