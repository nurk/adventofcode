package year2020.puzzle16;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Key {

    private String key;
    private List<Integer> validPosition = new ArrayList<>();

    public Key(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public List<Integer> getValidPosition() {
        return validPosition;
    }

    public void addPosition(int pos) {
        validPosition.add(pos);
    }

    public void removePosition(Integer pos) {
        if (validPosition.size() > 1) {
            validPosition.remove(pos);
        }
    }


    @Override
    public String toString() {
        return "Key{" +
            "key='" + key + '\'' +
            ", validPosition=" + validPosition.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(",")) +
            '}';
    }
}
