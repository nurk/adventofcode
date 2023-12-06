package year2023.puzzle3;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Engine {
    private Place[][] places;

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
                    while (StringUtils.isNumeric(split[currentColumn])) {
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

    @Override
    public String toString() {
        return Arrays.stream(places)
                .map(row -> Arrays.stream(row)
                        .map(s -> StringUtils.leftPad(s.getValue() + "(" + s.getId() + ")", 7))
                        .collect(Collectors.joining(" ")))
                .collect(Collectors.joining("\n"));
    }
}
