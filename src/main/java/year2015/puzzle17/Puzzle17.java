package year2015.puzzle17;

import org.apache.commons.math3.util.CombinatoricsUtils;
import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Puzzle17 {
    public static void main(String[] args) {
        List<Integer> input = new ArrayList<>(Utils.getInput("2015/input17.txt", Integer::valueOf));

        AtomicInteger partA = new AtomicInteger(0);
        AtomicInteger minimum = new AtomicInteger(input.size());
        IntStream.range(1, input.size()).forEach(k ->
                CombinatoricsUtils.combinationsIterator(input.size(), k).forEachRemaining(combi -> {
                            if (Arrays.stream(combi)
                                    .map(input::get)
                                    .sum() == 150) {
                                partA.getAndIncrement();
                                if (minimum.get() > combi.length) {
                                    minimum.set(combi.length);
                                }
                            }
                        }
                )
        );

        AtomicInteger partB = new AtomicInteger(0);
        CombinatoricsUtils.combinationsIterator(input.size(), minimum.get()).forEachRemaining(combi -> {
                    if (Arrays.stream(combi)
                            .map(input::get)
                            .sum() == 150) {
                        partB.getAndIncrement();
                    }
                }
        );
        System.out.println(partA.get());
        System.out.println(partB.get());
    }
}
