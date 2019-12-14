package year2019.puzzle7;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

public class Puzzle7 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        //IntCode intCode = new IntCode(parse("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"), 7);
        //intCode.run();
        puzzleA();
        //puzzleB();
    }

    private static void puzzleA() throws URISyntaxException, IOException {
        Files.readAllLines(Utils.getInputPath("2019/input7.txt")).forEach(
                line -> {
                    int[] integers = parse(line);

                    SortedSet<Integer> eOutputs = new TreeSet<>();
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            for (int k = 0; k < 5; k++) {
                                for (int l = 0; l < 5; l++) {
                                    for (int m = 0; m < 5; m++) {
                                        if (isUniquePhase("" + i + j + k + l + m)) {
                                            IntCode intCodeA = new IntCode(integers, new ArrayDeque<>(List.of(i, 0)));
                                            int outputA = intCodeA.run();
                                            IntCode intCodeB = new IntCode(integers,
                                                    new ArrayDeque<>(List.of(j, outputA)));
                                            int outputB = intCodeB.run();
                                            IntCode intCodeC = new IntCode(integers,
                                                    new ArrayDeque<>(List.of(k, outputB)));
                                            int outputC = intCodeC.run();
                                            IntCode intCodeD = new IntCode(integers,
                                                    new ArrayDeque<>(List.of(l, outputC)));
                                            int outputD = intCodeD.run();
                                            IntCode intCodeE = new IntCode(integers,
                                                    new ArrayDeque<>(List.of(m, outputD)));
                                            int outputE = intCodeE.run();
                                            eOutputs.add(outputE);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    System.out.println(eOutputs.last());
                }
        );
    }

    private static boolean isUniquePhase(String phase) {
        List<String> elements = new ArrayList<>();
        AtomicBoolean uniquePhase = new AtomicBoolean(true);
        phase.chars()
                .forEach(p -> {
                    String e = String.valueOf(p);
                    if (elements.contains(e)) {
                        uniquePhase.set(false);
                    } else {
                        elements.add(e);
                    }
                });
        return uniquePhase.get();

    }

    private static void puzzleB() throws URISyntaxException, IOException {
        /*Files.readAllLines(Utils.getInputPath("2019/input5.txt")).forEach(
                line -> {
                    IntCode intCode = new IntCode(parse(line), 5);
                    intCode.run();
                }
        );*/
    }

    public static int[] parse(String line) {
        //line = "3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0";
        String[] split = StringUtils.split(line, ",");
        int[] ints = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            ints[i] = Integer.parseInt(split[i]);
        }

        return ints;
    }
}
