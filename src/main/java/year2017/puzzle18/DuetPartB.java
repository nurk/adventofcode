package year2017.puzzle18;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class DuetPartB {
    private final long id;
    private final Map<String, Long> registers = new HashMap<>();
    private final Deque<Long> receivedNotes = new ArrayDeque<>();
    private final List<String> lines;
    int currentPosition = 0;
    @Getter
    private int sendCount = 0;
    @Getter
    private boolean waitingForReceive = false;
    @Getter
    private boolean terminated = false;

    @Setter
    private DuetPartB otherDuet;

    public DuetPartB(long id, List<String> lines) {
        this.id = id;
        this.lines = lines;
        registers.put("p", id);
    }

    public void receive(long value) {
        waitingForReceive = false;
        receivedNotes.addLast(value);
    }

    public void performSong() {

        while (!waitingForReceive) {
            if (currentPosition < 0 || currentPosition >= lines.size()) {
                System.out.println("Duet " + id + " has terminated");
                terminated = true;
                break;
            }
            String line = lines.get(currentPosition);
            String[] split = line.split(" ");
            String command = split[0];
            long offset = 1;

            switch (command) {
                case "snd" -> {
                    sendCount++;
                    otherDuet.receive(getValue(split[1]));
                }
                case "set" -> registers.put(split[1], getValue(split[2]));
                case "add" -> registers.put(split[1], getValue(split[1]) + getValue(split[2]));
                case "mul" -> registers.put(split[1], getValue(split[1]) * getValue(split[2]));
                case "mod" -> registers.put(split[1], getValue(split[1]) % getValue(split[2]));
                case "rcv" -> {
                    if (receivedNotes.isEmpty()) {
                        waitingForReceive = true;
                        return;
                    }
                    registers.put(split[1], receivedNotes.pop());
                }
                case "jgz" -> {
                    if (getValue(split[1]) > 0) {
                        offset = getValue(split[2]);
                    }
                }
            }

            currentPosition += (int) offset;
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
