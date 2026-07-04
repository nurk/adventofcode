package year2019.puzzle16;

import java.util.List;

public class SequenceGenerator {
    private final List<Integer> sequence = List.of(0, 1, 0, -1);
    private final Integer loop;

    private Integer currentPosition = 0;

    public SequenceGenerator(Integer loop) {
        this.loop = loop + 1;
        next();
    }

    public Integer next() {
        // if loop = 1
        // currentPos = 0 => 1
        // currentPos = 1 => 0
        // currentPos = 2 => -1
        // currentPos = 3 => 0

        // if loop = 2
        // currentPos = 0 => 1
        // currentPos = 1 => 1
        // currentPos = 2 => 0
        // currentPos = 3 => 0
        // currentPos = 4 => -1
        // currentPos = 5 => -1
        // currentPos = 6 => 0
        // currentPos = 7 => 0

        int positionToGet = currentPosition / loop;
        Integer value = sequence.get(positionToGet);
        currentPosition = (currentPosition + 1) % (sequence.size() * loop);
        return value;
    }
}
