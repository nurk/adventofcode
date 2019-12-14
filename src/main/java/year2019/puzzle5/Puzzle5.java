package year2019.puzzle5;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

public class Puzzle5 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        //IntCode intCode = new IntCode(parse("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"), 7);
        //intCode.run();
        puzzleA();
        puzzleB();
    }

    private static void puzzleA() throws URISyntaxException, IOException {
        Files.readAllLines(Utils.getInputPath("2019/input5.txt")).forEach(
                line -> {
                    IntCode intCode = new IntCode(parse(line), 1);
                    intCode.run();
                }
        );
    }

    private static void puzzleB() throws URISyntaxException, IOException {
        Files.readAllLines(Utils.getInputPath("2019/input5.txt")).forEach(
                line -> {
                    IntCode intCode = new IntCode(parse(line), 5);
                    intCode.run();
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
