package year2020.puzzle20;

import com.google.common.collect.Lists;
import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Puzzle20 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> input = new ArrayList<>(Files.readAllLines(Utils.getInputPath("2020/input20.txt")));

        List<List<String>> partition = Lists.partition(input, 12);

        List<Tile> tiles = new ArrayList<>();

        for (List<String> strings : partition) {
            tiles.add(new Tile(new ArrayList<>(strings)));
        }

        for (int i = 0; i < tiles.size() - 1; i++) {
            Tile cur = tiles.get(i);
            for (int j = i + 1; j < tiles.size(); j++) {
                tiles.get(j).compare(cur);
            }
        }

        Long result = 1L;

        for (Tile tile : tiles) {
            if(tile.numberOfMatchingBorders() == 2){
                result *= tile.getId();
            }
        }

        System.out.println(result);

    }
}
