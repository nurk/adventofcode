package year2015;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Puzzle2 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println(new Box("1x1x10").getWrappingPaperSize());
        System.out.println(new Box("1x1x10").getRibbonLength());
        AtomicLong total = new AtomicLong();
        AtomicLong ribbon = new AtomicLong();
        Files.readAllLines(Utils.getInputPath("2015/input2.txt")).forEach(
                line -> {
                    Box box = new Box(line);
                    total.getAndAdd(box.getWrappingPaperSize());
                    ribbon.getAndAdd(box.getRibbonLength());
                }
        );
        System.out.println(total.get());
        System.out.println(ribbon.get());
    }

    static class Box {
        Integer width;
        Integer heigth;
        Integer depth;

        Box(String dimensions) {
            String[] split = StringUtils.split(dimensions, "x");
            width = Integer.valueOf(split[0]);
            heigth = Integer.valueOf(split[1]);
            depth = Integer.valueOf(split[2]);
        }

        int getWrappingPaperSize() {
            int size = 0;
            List<Integer> sidesSurfaceArea = getSidesSurfaceArea();
            for (Integer integer : sidesSurfaceArea) {
                size = size + 2 * integer;
            }
            size = size + sidesSurfaceArea.get(0);
            return size;
        }

        int getRibbonLength() {
            ArrayList<Integer> sides = Lists.newArrayList(width, heigth, depth);
            Collections.sort(sides);
            return sides.get(0) * 2 + sides.get(1) * 2 + getVolume();
        }

        int getVolume() {
            return width * heigth * depth;
        }

        List<Integer> getSidesSurfaceArea() {
            List<Integer> list = new ArrayList<>();
            list.add(width * heigth);
            list.add(width * depth);
            list.add(heigth * depth);
            Collections.sort(list);
            return list;
        }
    }
}
