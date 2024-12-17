package year2024.puzzle9;

import lombok.Getter;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FileBlock {
    @Getter
    private final int size;
    @Getter
    private final String id;
    private final UUID uuid;

    public FileBlock(int size, String id) {
        this.size = size;
        this.id = id;
        this.uuid = UUID.randomUUID();
    }

    public boolean isEmpty() {
        return id.equals(Puzzle9B.EMPTY);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FileBlock fileBlock = (FileBlock) o;
        return uuid.equals(fileBlock.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return IntStream.range(0, size)
                .mapToObj(_ -> id)
                .collect(Collectors.joining(""));
    }
}
