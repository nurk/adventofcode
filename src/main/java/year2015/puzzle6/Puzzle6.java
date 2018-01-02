package year2015.puzzle6;

import org.apache.commons.lang3.StringUtils;
import util.Utils;
import year2015.Puzzle2;
import year2015.puzzle6.actions.Off;
import year2015.puzzle6.actions.On;
import year2015.puzzle6.actions.Toggle;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.stream.IntStream;

public class Puzzle6 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        int lights[][] = new int[1000][1000];
        Files.readAllLines(Utils.getInputPath("2015/input6.txt")).forEach(
                line -> {
                    String[] split = StringUtils.split(line, " ");
                    switch (split[0]){
                        case "on":
                            new On(split[1],split[3]).perform(lights);
                            break;
                        case "off":
                            new Off(split[1],split[3]).perform(lights);
                            break;
                        case "toggle":
                            new Toggle(split[1],split[3]).perform(lights);
                            break;
                        default:
                            throw new RuntimeException("wrong name");
                    }
                }
        );

        int totatLightsOn = 0;
        for (int[] light : lights) {
            totatLightsOn = totatLightsOn + IntStream.of(light).sum();
        }

        System.out.println(totatLightsOn);
    }
}
