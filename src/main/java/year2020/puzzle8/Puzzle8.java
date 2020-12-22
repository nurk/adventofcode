package year2020.puzzle8;

import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Puzzle8 {
    @SuppressWarnings("SuspiciousListRemoveInLoop")
    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> instructions = new ArrayList<>(Files.readAllLines(Utils.getInputPath("2020/input8.txt")));

        System.out.println("PuzzleA");
        Game puzzleA = new Game(instructions);
        puzzleA.run();

        System.out.println("PuzzleB");
        for (int i = 0; i < instructions.size(); i++) {
            String inst = instructions.get(i);
            if (inst.startsWith("jmp")) {
                instructions.remove(i);
                instructions.add(i, inst.replace("jmp", "nop"));
                Game game = new Game(instructions);
                if (game.run() == -1) {
                    instructions.remove(i);
                    instructions.add(i, inst.replace("nop", "jmp"));
                } else {
                    break;
                }
            } else if (inst.startsWith("nop")) {
                instructions.remove(i);
                instructions.add(i, inst.replace("nop", "jmp"));
                Game game = new Game(instructions);
                if (game.run() == -1) {
                    instructions.remove(i);
                    instructions.add(i, inst.replace("jmp", "nop"));
                } else {
                    break;
                }
            }
        }
    }
}
