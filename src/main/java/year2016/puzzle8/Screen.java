package year2016.puzzle8;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

import java.util.Arrays;

import static java.util.stream.Collectors.*;

public class Screen {

    private String[][] screen;

    public Screen(int row, int col) {
        this.screen = new String[row][col];
        Arrays.stream(this.screen).forEach(r -> Arrays.fill(r, "."));
    }

    public void perform(String line) {
        if (Strings.CS.startsWith(line, "rect")) {
            performRect(line);
        } else if (Strings.CS.startsWith(line, "rotate column")) {
            performRotateColumn(line);
        } else if (Strings.CS.startsWith(line, "rotate row")) {
            performRotateRow(line);
        } else {
            throw new IllegalArgumentException("Unknown line: " + line);
        }
    }

    public int getOnPixelCount() {
        int count = 0;
        for (String[] strings : screen) {
            for (String string : strings) {
                if (string.equals("#")) {
                    count++;
                }
            }
        }

        return count;
    }

    private void performRotateRow(String line) {
        String[] split = Strings.CS.remove(line, "rotate row ").split(" by ");
        int row = Integer.parseInt(split[0].split("=")[1]);
        int by = Integer.parseInt(split[1]);

        String[][] clone = cloneScreen();

        for (int col = 0; col < screen[row].length; col++) {
            clone[row][(col + by) % screen[row].length] = screen[row][col];
        }

        screen = clone;
    }

    private void performRotateColumn(String line) {
        String[] split = Strings.CS.remove(line, "rotate column ").split(" by ");
        int col = Integer.parseInt(split[0].split("=")[1]);
        int by = Integer.parseInt(split[1]);

        String[][] clone = cloneScreen();

        for (int row = 0; row < screen.length; row++) {
            clone[(row + by) % screen.length][col] = screen[row][col];
        }

        screen = clone;
    }

    private void performRect(String line) {
        String[] split = StringUtils.substringAfter(line, "rect ").split("x");
        int width = Integer.parseInt(split[0]);
        int height = Integer.parseInt(split[1]);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                screen[row][col] = "#";
            }
        }
    }

    private String[][] cloneScreen() {
        String[][] cloneScreen = new String[screen.length][];
        for (int i = 0; i < screen.length; i++) {
            cloneScreen[i] = screen[i].clone();
        }
        return cloneScreen;
    }

    @Override
    public String toString() {
        return Arrays.stream(screen)
                .map(pixel -> String.join("", pixel))
                .collect(joining("\n"));
    }
}
