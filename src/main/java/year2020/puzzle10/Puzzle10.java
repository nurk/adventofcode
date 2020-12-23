package year2020.puzzle10;

import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Puzzle10 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        List<Long> adapters = new ArrayList<>();
        Files.readAllLines(Utils.getInputPath("2020/input10.txt")).forEach(
                line -> adapters.add(Long.parseLong(line))
        );

        List<Long> originalAdapters = new ArrayList<>(adapters);
        long oneDifference = 0;
        long threeDifference = 1;

        long currentJolts = 0;

        while(!adapters.isEmpty()){
            if(adapters.contains(currentJolts + 1)){
                currentJolts += 1;
                oneDifference++;
                adapters.remove(currentJolts);
            } else if(adapters.contains(currentJolts + 2)){
                currentJolts += 2;
                adapters.remove((Long)currentJolts);
            } else if(adapters.contains(currentJolts + 3)){
                currentJolts += 3;
                threeDifference++;
                adapters.remove(currentJolts);
            }
        }

        System.out.println(currentJolts);
        System.out.println("one diff: " + oneDifference);
        System.out.println("three diff: " + threeDifference);

        System.out.println("solution: " + (oneDifference * threeDifference));
    }
}
