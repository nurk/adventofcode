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

public class Puzzle3b {
    public static void main(String[] args) throws URISyntaxException, IOException {
        AtomicInteger totalTriangles = new AtomicInteger();
        List<Integer> column1 = new ArrayList<>();
        List<Integer> column2 = new ArrayList<>();
        List<Integer> column3 = new ArrayList<>();

        Files.readAllLines(Utils.getInputPath("2016/input3.txt")).forEach(
                line -> {
                    List<Integer> all = new ArrayList<>();
                    Arrays.stream(StringUtils.split(line, " ")).forEach(part -> all.add(Integer.valueOf(StringUtils.trim(part))));
                    column1.add(all.get(0));
                    column2.add(all.get(1));
                    column3.add(all.get(2));
                }
        );


        List<Integer> all = new ArrayList<>();
        all.addAll(column1);
        all.addAll(column2);
        all.addAll(column3);

        for (int i = 0; i < all.size(); i = i + 3) {
            if (((all.get(i) + all.get(i + 1)) > all.get(i + 2)) && ((all.get(i) + all.get(i + 2)) > all.get(i + 1)) && ((all.get(i + 2) + all.get(i + 1)) > all.get(i))) {
                totalTriangles.getAndIncrement();
            }

        }

        System.out.println(totalTriangles);
    }
}
