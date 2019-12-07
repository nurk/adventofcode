package year2019;

import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicInteger;

public class Puzzle1 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        puzzleB();
    }

    private static void puzzleA() throws URISyntaxException, IOException {
        AtomicInteger total = new AtomicInteger(0);
        Files.readAllLines(Utils.getInputPath("2019/input1a.txt")).forEach(
                line -> {
                    total.getAndAdd((int) (Math.floor(Integer.valueOf(line) / 3) - 2));
                }
        );

        System.out.println(total.get());
    }

    private static void puzzleB() throws URISyntaxException, IOException {
        AtomicInteger total = new AtomicInteger(0);
        Files.readAllLines(Utils.getInputPath("2019/input1a.txt")).forEach(
                line -> {
                    total.getAndAdd(totalFuel(Integer.parseInt(line)));
                }
        );

        System.out.println(total.get());
    }

    public static int totalFuel(int value){
        int totalFuel = 0;

        int requiredFuel = value;
        do{
            requiredFuel = requiredFuel(requiredFuel);
            if(requiredFuel > 0){
                totalFuel += requiredFuel;
            }
        } while (requiredFuel > 0);

        return totalFuel;
    }

    public static int requiredFuel(int fuel){
        return (int) (Math.floor(fuel / 3) - 2);
    }
}
