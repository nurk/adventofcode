package year2017.puzzle18;


import java.util.*;

public class Duet {
    private final Map<String, Long> registers = new HashMap<>();
    private final Deque<Long> playedNotes = new ArrayDeque<>();

    public long performSong(List<String> lines) {
        int currentPosition = 0;

        while (true) {
            if (currentPosition < 0 || currentPosition >= lines.size()) {
                return -1;
            }
            String line = lines.get(currentPosition);
            String[] split = line.split(" ");
            String command = split[0];
            long offset = 1;

            switch (command) {
                case "snd" -> playedNotes.push(getValue(split[1]));
                case "set" -> registers.put(split[1], getValue(split[2]));
                case "add" -> registers.put(split[1], getValue(split[1]) + getValue(split[2]));
                case "mul" -> registers.put(split[1], getValue(split[1]) * getValue(split[2]));
                case "mod" -> registers.put(split[1], getValue(split[1]) % getValue(split[2]));
                case "rcv" -> {
                    if (getValue(split[1]) != 0 && !playedNotes.isEmpty()) {
                        return playedNotes.peek();
                    }
                }
                case "jgz" -> {
                    if (getValue(split[1]) > 0) {
                        offset = getValue(split[2]);
                    }
                }
            }

            currentPosition += (int)offset;
        }
    }

    private long getValue(String operand) {
        try {
            return Long.parseLong(operand);
        } catch (NumberFormatException e) {
            return registers.getOrDefault(operand, 0L);
        }
    }
}
