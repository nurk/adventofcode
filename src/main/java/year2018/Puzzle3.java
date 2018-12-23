package year2018;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Puzzle3 {

    private static Path getInputUri() throws URISyntaxException {
        return Paths.get(Puzzle3.class.getClassLoader().getResource("2018/input3.txt").toURI());
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        //#981 @ 190,160: 27x17
        int[][] fabric = new int[10000][10000];

        puzzleA(fabric);
        puzzleB(fabric);
    }

    private static void puzzleA(int[][] fabric) throws IOException, URISyntaxException {
        Files.readAllLines(getInputUri()).forEach(
                line -> {
                    String[] parts = StringUtils.split(line, " ");
                    String[] xAndY = StringUtils.split(StringUtils.remove(parts[2], ":"), ",");
                    String[] widthAndHeigth = StringUtils.split(parts[3], "x");

                    for (int i = getX(xAndY); i < getX(xAndY) + getWidth(widthAndHeigth); i++) {
                        for (int j = getY(xAndY); j < getY(xAndY) + getHeigth(widthAndHeigth); j++) {
                            fabric[i][j] = fabric[i][j] + 1;
                        }
                    }
                }
        );

        int largerThenOne = 0;

        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 10000; j++) {
                if (fabric[i][j] > 1) {
                    largerThenOne++;
                }
            }
        }

        System.out.println(largerThenOne);
    }

    private static void puzzleB(int[][] fabric) throws IOException, URISyntaxException {
        Files.readAllLines(getInputUri()).forEach(
                line -> {
                    String[] parts = StringUtils.split(line, " ");
                    String[] xAndY = StringUtils.split(StringUtils.remove(parts[2], ":"), ",");
                    String[] widthAndHeigth = StringUtils.split(parts[3], "x");

                    boolean allOne = true;
                    for (int i = getX(xAndY); i < getX(xAndY) + getWidth(widthAndHeigth); i++) {
                        for (int j = getY(xAndY); j < getY(xAndY) + getHeigth(widthAndHeigth); j++) {
                            if (fabric[i][j] != 1) {
                                allOne = false;
                                break;
                            }
                        }
                    }

                    if (allOne) {
                        System.out.println(parts[0]);
                    }
                }
        );
    }

    private static Integer getWidth(String[] widthAndHeigth) {
        return Integer.valueOf(widthAndHeigth[0]);
    }

    private static Integer getHeigth(String[] widthAndHeigth) {
        return Integer.valueOf(widthAndHeigth[1]);
    }

    private static Integer getX(String[] xAndY) {
        return Integer.valueOf(xAndY[0]);
    }

    private static Integer getY(String[] xAndY) {
        return Integer.valueOf(xAndY[1]);
    }
}
