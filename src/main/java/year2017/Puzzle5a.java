package year2017;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Puzzle5a {

    public static void main(String... args) throws Exception {

        ArrayList<Instruction> instructions = new ArrayList<>();
        Files.readAllLines(getInputUri()).forEach(
                line -> {
                    instructions.add(new Instruction(Integer.valueOf(line)));
                }
        );

        boolean exitFound = false;
        int stepsTaken = 0;
        int position = 0;
        while (!exitFound) {
            stepsTaken++;
            Instruction i = instructions.get(position);
            position += i.getSteps();
            i.increment();
            if (position < 0 || position >= instructions.size()) {
                exitFound = true;
            }
        }

        System.out.println("Steps taken " + stepsTaken);
    }

    private static Path getInputUri() throws URISyntaxException {
        return Paths.get(Puzzle5a.class.getClassLoader().getResource("2017/input5.txt").toURI());
    }

    static class Instruction {
        private int steps;

        Instruction(int steps) {
            this.steps = steps;
        }

        public void increment() {
            steps++;
        }

        public int getSteps() {
            return steps;
        }

    }

}
