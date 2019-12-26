package year2019.puzzle9;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

public class Puzzle9 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        //IntCode intCode = new IntCode(parse("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"), 7);
        //intCode.run();
        puzzleA();
        puzzleB();
    }

    private static void puzzleA() throws URISyntaxException, IOException {
        Files.readAllLines(Utils.getInputPath("2019/input9.txt")).forEach(
                line -> {
                    IntCode intCode = new IntCode(parse(line), 1);
                    intCode.run();
                }
        );
    }

    private static void puzzleB() throws URISyntaxException, IOException {
        Files.readAllLines(Utils.getInputPath("2019/input9.txt")).forEach(
                line -> {
                    IntCode intCode = new IntCode(parse(line), 2);
                    intCode.run();
                }
        );
    }

    public static long[] parse(String line) {
        String[] split = StringUtils.split(line, ",");
        //String[] split = StringUtils.split("109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99", ",");
        long[] ints = new long[split.length * 2];
        for (int i = 0; i < split.length; i++) {
            ints[i] = Long.parseLong(split[i]);
        }

        return ints;
    }
}
