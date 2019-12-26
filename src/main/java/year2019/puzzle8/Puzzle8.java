package year2019.puzzle8;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Puzzle8 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        SortedSet<Layer> layers = new TreeSet<>(Comparator.comparing(Layer::numberOfZeroDigits));
        List<Layer> unsortedLayers = new ArrayList<>();
        Files.readAllLines(Utils.getInputPath("2019/input8.txt")).forEach(
                line -> {
                    for (int i = 0; i < line.length(); i += 150) {
                        Layer layer = new Layer(StringUtils.substring(line, i, i + 150));
                        layers.add(layer);
                        unsortedLayers.add(layer);
                    }
                }
        );

        System.out.println("Solution 1 " + layers.first().getSolution());

        for (int j = 0; j < 6; j++) {
            System.out.println();
            for (int i = 0; i < 25; i++) {
                for (Layer layer : unsortedLayers) {
                    String pixel = layer.getPixel(i + (j * 25));
                    if (!StringUtils.equals(pixel, "2")) {
                        if (StringUtils.equals(pixel, "0")) {
                            System.out.print(" ");
                        } else {
                            System.out.print("0");
                        }
                        break;
                    }
                }
            }
        }
        System.out.println();
    }
}
