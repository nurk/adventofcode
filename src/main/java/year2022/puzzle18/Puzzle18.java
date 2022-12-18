package year2022.puzzle18;

import util.Utils;

import java.util.List;
import java.util.stream.Stream;

public class Puzzle18 {
    static List<Cube> cubes;

    public static void main(String[] args) {
        //Part A: 4192
        //Part B: 3988 <-- too high.  Possibly there are encapsulate air pockets bigger than 1 block
        cubes = Utils.getInput("2022/input18.txt", Cube::new);
        Integer exposedSides = cubes.stream()
                .map(Cube::getExposedSides)
                .reduce(0, Integer::sum);
        System.out.println(exposedSides);

        int minX = cubes.stream()
                .map(c -> c.x)
                .min(Integer::compareTo)
                .orElseThrow();
        int maxX = cubes.stream()
                .map(c -> c.x)
                .max(Integer::compareTo)
                .orElseThrow();
        int minY = cubes.stream()
                .map(c -> c.y)
                .min(Integer::compareTo)
                .orElseThrow();
        int maxY = cubes.stream()
                .map(c -> c.y)
                .max(Integer::compareTo)
                .orElseThrow();
        int minZ = cubes.stream()
                .map(c -> c.z)
                .min(Integer::compareTo)
                .orElseThrow();
        int maxZ = cubes.stream()
                .map(c -> c.z)
                .max(Integer::compareTo)
                .orElseThrow();

        System.out.println();

        int encapsulated = 0;
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    if (new Cube(x, y, z).isEncapsulated()) {
                        encapsulated++;
                    }
                }
            }
        }
        System.out.println(exposedSides - encapsulated * 6);
    }

    static class Cube {
        int x, y, z;

        public Cube(String s) {
            String[] split = s.split(",");
            x = Integer.parseInt(split[0]);
            y = Integer.parseInt(split[1]);
            z = Integer.parseInt(split[2]);
        }

        public Cube(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        // only works when encapsulated air is a 1 by 1 by 1 cube
        // does not work with maze like path that might not reach the outside
        public boolean isEncapsulated() {
            boolean b = !cubes.contains(this)
                    && cubes.contains(new Cube(x - 1, y, z))
                    && cubes.contains(new Cube(x + 1, y, z))
                    && cubes.contains(new Cube(x, y - 1, z))
                    && cubes.contains(new Cube(x, y + 1, z))
                    && cubes.contains(new Cube(x, y, z - 1))
                    && cubes.contains(new Cube(x, y, z + 1));
            if (b) {
                System.out.println(this + " is encapsulated");
            }
            return b;
        }

        public int getExposedSides() {
            int exposedSides = 6;
            for (Cube cube : cubes) {
                if (cube.equals(this)) {
                    continue;
                }
                if (cube.touches(this)) {
                    //System.out.println(this + " touches " + cube);
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
