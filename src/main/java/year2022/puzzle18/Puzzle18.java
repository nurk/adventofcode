package year2022.puzzle18;

import util.Utils;

import java.util.List;
import java.util.stream.Stream;

public class Puzzle18 {
    static List<Cube> cubes;

    public static void main(String[] args) {
        //Part A: 4192
        cubes = Utils.getInput("2022/input18.txt", Cube::new);
        System.out.println(cubes.stream()
                .map(Cube::exposedSides)
                .reduce(0, Integer::sum));

    }

    static class Cube {
        int x, y, z;

        public Cube(String s) {
            String[] split = s.split(",");
            x = Integer.parseInt(split[0]);
            y = Integer.parseInt(split[1]);
            z = Integer.parseInt(split[2]);
        }

        public int exposedSides() {
            int exposedSides = 6;
            for (Cube cube : cubes) {
                if (cube.equals(this)) {
                    continue;
                }
                if (cube.touches(this)) {
                    exposedSides--;
                }
                if (exposedSides == 0) {
                    break;
                }
            }
            return exposedSides;
        }

        public boolean touches(Cube other) {
            return Stream.of(Math.abs(x - other.x), Math.abs(y - other.y), Math.abs(z - other.z))
                    .sorted(Integer::compareTo)
                    .toList().equals(List.of(0, 0, 1));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Cube cube = (Cube) o;

            if (x != cube.x) {
                return false;
            }
            if (y != cube.y) {
                return false;
            }
            return z == cube.z;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            result = 31 * result + z;
            return result;
        }

        @Override
        public String toString() {
            return "Cube{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }
    }
}
