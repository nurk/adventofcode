package year2019.puzzle2;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

public class Puzzle2 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        //puzzleA();
        puzzleB();
    }

    private static void puzzleB() throws URISyntaxException, IOException {
        Files.readAllLines(Utils.getInputPath("2019/input2a.txt")).forEach(
                line -> {
                    int[] input = parse(line);
                    for (int noun = 0; noun < 100; noun++) {
                        for (int verb = 0; verb < 100; verb++) {
                            IntCode2 intCode = new IntCode2(input, noun, verb);
                            if (intCode.run() == 19690720) {
                                System.out.println(100 * noun + verb);
                                break;
                            }
                        }
                    }
                }
        );
    }

    private static void puzzleA() throws URISyntaxException, IOException {
        Files.readAllLines(Utils.getInputPath("2019/input2a.txt")).forEach(
                line -> {
                    IntCode intCode = new IntCode(parse(line));
                    System.out.println(intCode.run());
                }
        );
    }


    public static int[] parse(String line) {
        String[] split = StringUtils.split(line, ",");
        int[] ints = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            ints[i] = Integer.parseInt(split[i]);
        }

        return ints;
    }
}
