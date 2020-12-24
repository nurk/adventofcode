package year2020.puzzle12;

import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Puzzle12 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> input = new ArrayList<>(Files.readAllLines(Utils.getInputPath("2020/input12.txt")));

        Navigation navigation = new Navigation();
        NavigationB navigationB = new NavigationB();

        for (String s : input) {
            navigation.move(s);
            navigationB.move(s);
        }

        System.out.println(navigation.distance());
        System.out.println(navigationB.distance());
    }
}
