package year2018.puzzle10;

import util.Utils;

import java.util.List;

/**
 * Part A: LKPHZHHJ
 * Part B: 10159
 */
public class Puzzle10 {
    static void main() {
        List<NightLight> nightLights = Utils.getInput("2018/input10.txt", NightLight::new);

        int i=0;
        while(!draw(nightLights)) {
            nightLights.forEach(NightLight::move);
            i++;
        }
        System.out.println("Seconds: " + i);
    }

    private static boolean draw(List<NightLight> nightLights) {
        int minX = nightLights.stream()
                .map(NightLight::getX)
                .min(Integer::compareTo)
                .orElseThrow();
        int maxX = nightLights.stream()
                .map(NightLight::getX)
                .max(Integer::compareTo)
                .orElseThrow();
        int minY = nightLights.stream()
                .map(NightLight::getY)
                .min(Integer::compareTo)
                .orElseThrow();
        int maxY = nightLights.stream()
                .map(NightLight::getY)
                .max(Integer::compareTo)
                .orElseThrow();

        if (maxX - minX > 64 || maxY - minY > 64) {
            System.out.println("Too big to draw " + (maxX - minX) + " x " + maxY + " y " + minY);
            return false;
        }

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                int finalX = x;
                int finalY = y;
                if (nightLights.stream().anyMatch(nl -> nl.getX() == finalX && nl.getY() == finalY)) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        return true;
    }
}
