package year2024.puzzle13;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Part A: 33427
 * Part B: 91649162972270
 */
public class Puzzle13 {
    public static void main(String[] args) {
        List<Game> games = new ArrayList<>();
        List<String> gameLines = new ArrayList<>();
        Utils.getInput("2024/input13.txt", (s) -> s).forEach(line -> {
            if (StringUtils.isEmpty(line)) {
                games.add(new Game(gameLines));
                gameLines.clear();
            } else {
                gameLines.add(line);
            }
        });
        games.add(new Game(gameLines));

        System.out.println("Cramers rules");
        System.out.println("Part A: " + games.stream()
                .map(Game::solveCramersRuleA)
                .reduce(Long::sum)
                .orElseThrow());

        System.out.println("Part B: " + games.stream()
                .map(Game::solveCramersRuleB)
                .reduce(Long::sum)
                .orElseThrow());

        System.out.println();
        System.out.println("Brute force");
        System.out.println("This will get there it will just take a lot of time.");
        System.out.println("Part A: " + games.stream()
                .map(Game::solveA)
                .reduce(Long::sum)
                .orElseThrow());

        System.out.println("Part B: " + games.stream()
                .map(Game::solveB)
                .reduce(Long::sum)
                .orElseThrow());
    }
}
