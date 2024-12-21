package year2024.puzzle17;

import lombok.Getter;

public class InstructionPointer {
    @Getter
    private int position = 0;

    private final int maxPosition;

    public InstructionPointer(int maxPosition) {
        this.maxPosition = maxPosition;
    }

    public void advanceTwo() {
        position += 2;
    }

    public void set(Long value) {
        this.position = value.intValue();
    }

    public boolean isOutOfBounds() {
        return position > maxPosition || position < 0;
    }
}
