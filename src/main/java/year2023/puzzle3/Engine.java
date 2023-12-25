package year2023.puzzle3;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Engine {
    private final Place[][] places;

    public Engine(List<String> lines) {
        places = new Place[lines.get(0).length()][lines.size()];

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            String[] split = line.split("");

            for (int j = 0; j < split.length; j++) {
                String s = split[j];

                if (StringUtils.isNumeric(s)) {
                    String currentValue = s;
                    int currentColumn = j;
                    currentColumn++;
                    while (currentColumn != split.length && StringUtils.isNumeric(split[currentColumn])) {
                        currentValue = currentValue + split[currentColumn];
                        currentColumn++;
                    }
                    for (int k = 0; k < currentValue.length(); k++) {
                        places[i][j + k] = new Place(i + "" + j, currentValue);
                    }
                    j = --currentColumn;
                } else {
                    places[i][j] = new Place(i + "" + j, s);
                }
            }
        }
    }

    public int sumOfPartNumbers() {
        Set<Place> parts = new HashSet<>();
        for (int i = 0; i < places.length; i++) {
            for (int j = 0; j < places[i].length; j++) {
                Place place = places[i][j];

                if (isPart(i, j)) {
                    parts.add(place);
                }
            }
        }

        return parts.stream()
                .map(part -> Integer.parseInt(part.getValue()))
                .mapToInt(value -> value)
                .sum();
    }

    public long gearRatio() {
        long result = 0;
        for (int i = 0; i < places.length; i++) {
            for (int j = 0; j < places[i].length; j++) {
                Place place = places[i][j];

                Set<Place> neighbourParts = getNeighbourParts(i, j);

                if (place.isGear() && neighbourParts.size() == 2) {
                    result += neighbourParts.stream()
                            .map(part -> Integer.parseInt(part.getValue()))
                            .mapToLong(value -> value)
                            .reduce(1, (left, right) -> left * right);
                }
            }
        }
        return result;
    }

    private Set<Place> getNeighbourParts(int i, int j) {
        Set<Place> parts = new HashSet<>();

        if (isPart(i - 1, j - 1)) {
            parts.add(places[i - 1][j - 1]);
        }
        if (isPart(i - 1, j)) {
            parts.add(places[i - 1][j]);
        }
        if (isPart(i - 1, j + 1)) {
            parts.add(places[i - 1][j + 1]);
        }
        if (isPart(i, j - 1)) {
            parts.add(places[i][j - 1]);
        }
        if (isPart(i, j + 1)) {
            parts.add(places[i][j + 1]);
        }
        if (isPart(i + 1, j - 1)) {
            parts.add(places[i + 1][j - 1]);
        }
        if (isPart(i + 1, j)) {
            parts.add(places[i + 1][j]);
        }
        if (isPart(i + 1, j + 1)) {
            parts.add(places[i + 1][j + 1]);
        }
        return parts;
    }

    private boolean isPart(int i, int j) {
        try {
            return places[i][j].isNumeric() && hasSymbolAsNeighbour(i, j);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean hasSymbolAsNeighbour(int i, int j) {
        int symbols = 0;
        symbols += isSymbol(i - 1, j - 1);
        symbols += isSymbol(i - 1, j);
        symbols += isSymbol(i - 1, j + 1);
        symbols += isSymbol(i, j - 1);
        symbols += isSymbol(i, j + 1);
        symbols += isSymbol(i + 1, j - 1);
        symbols += isSymbol(i + 1, j);
        symbols += isSymbol(i + 1, j + 1);

        return symbols > 0;
    }

    private int isSymbol(int i, int j) {
        try {
            if (places[i][j].isSymbol()) {
                return 1;
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }

    @Override
    public String toString() {
        return Arrays.stream(places)
                .map(row -> Arrays.stream(row)
                        .map(s -> StringUtils.leftPad(s.getValue() + "(" + s.getId() + ")", 7))
                        .collect(Collectors.joining(" ")))
                .collect(Collectors.joining("\n"));
    }
}
