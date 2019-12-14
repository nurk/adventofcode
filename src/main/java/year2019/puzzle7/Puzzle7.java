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
        puzzleA();
        puzzleB();
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
                                            IntCode intCodeA = new IntCode(integers);
                                            intCodeA.setInputs(new ArrayDeque<>(List.of(i, 0)));
                                            int outputA = intCodeA.run();
                                            IntCode intCodeB = new IntCode(integers);
                                            intCodeB.setInputs(new ArrayDeque<>(List.of(j, outputA)));
                                            int outputB = intCodeB.run();
                                            IntCode intCodeC = new IntCode(integers);
                                            intCodeC.setInputs(new ArrayDeque<>(List.of(k, outputB)));
                                            int outputC = intCodeC.run();
                                            IntCode intCodeD = new IntCode(integers);
                                            intCodeD.setInputs(new ArrayDeque<>(List.of(l, outputC)));
                                            int outputD = intCodeD.run();
                                            IntCode intCodeE = new IntCode(integers);
                                            intCodeE.setInputs(new ArrayDeque<>(List.of(m, outputD)));
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

    private static void puzzleB() throws URISyntaxException, IOException {
        Files.readAllLines(Utils.getInputPath("2019/input7.txt")).forEach(
                line -> {
                    int[] integers = parse(line);

                    SortedSet<Integer> haltedOutputs = new TreeSet<>();
                    for (int i = 5; i < 10; i++) {
                        for (int j = 5; j < 10; j++) {
                            for (int k = 5; k < 10; k++) {
                                for (int l = 5; l < 10; l++) {
                                    for (int m = 5; m < 10; m++) {
                                        if (isUniquePhase("" + i + j + k + l + m)) {
                                            List<IntCode> intCodeMachines = new ArrayList<>();
                                            intCodeMachines.add(new IntCode(integers).addInput(i).addInput(0));
                                            intCodeMachines.add(new IntCode(integers).addInput(j));
                                            intCodeMachines.add(new IntCode(integers).addInput(k));
                                            intCodeMachines.add(new IntCode(integers).addInput(l));
                                            intCodeMachines.add(new IntCode(integers).addInput(m));

                                            boolean halted = false;
                                            int currentIntCodeMachine = 0;

                                            while (!halted) {
                                                IntCode intCode = intCodeMachines.get(currentIntCodeMachine);
                                                intCode.run();


                                                if (intCode.isHalted()) {
                                                    haltedOutputs.add(intCodeMachines.get(4).getResult());
                                                    halted = true;
                                                } else {
                                                    currentIntCodeMachine = (currentIntCodeMachine + 1) % 5;
                                                    intCodeMachines.get(currentIntCodeMachine)
                                                            .addInput(intCode.getResult());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    System.out.println(haltedOutputs.last());
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

    public static int[] parse(String line) {
        //line = "3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10";
        String[] split = StringUtils.split(line, ",");
        int[] ints = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            ints[i] = Integer.parseInt(split[i]);
        }

        return ints;
    }
}
