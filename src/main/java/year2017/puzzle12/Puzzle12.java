package year2017.puzzle12;

import util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Part A: 169
 * Part B: 179
 */
public class Puzzle12 {
    public static void main(String[] args) {
        List<String> input = Utils.getInput("2017/input12.txt");

        Map<String, Program> programs = new HashMap<>();
        input.forEach(line -> {
            Program program = new Program(line);
            programs.put(program.getId(), program);
        });
        programs.values().forEach(p -> p.init(programs));

        List<Program> programsInGroup = programs.values().stream()
                .filter(program -> program.isInGroup("0", new ArrayList<>()))
                .toList();

        System.out.println("Part A: " + programsInGroup.size());

        int groups = 1;
        List<Program> remainingPrograms = new ArrayList<>(programs.values());
        remainingPrograms.removeAll(programsInGroup);
        while (!remainingPrograms.isEmpty()) {
            Program program = remainingPrograms.getFirst();
            programsInGroup = remainingPrograms.stream()
                    .filter(p -> p.isInGroup(program.getId(), new ArrayList<>()))
                    .toList();
            remainingPrograms.removeAll(programsInGroup);
            groups++;
        }

        System.out.println("Part B: " + groups);
    }
}
