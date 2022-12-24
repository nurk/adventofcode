package year2022.puzzle18;

import util.Utils;

import java.util.*;

public class Puzzle18 {
    static List<Cube> cubes;
    static List<Cube> allFeeCubes = new ArrayList<>();
    static List<Cube> encapsulatedCubes = new ArrayList<>();

    public static void main(String[] args) {
        //Part A: 4192
        //Part B: 2520
        cubes = Utils.getInput("2022/input18.txt", Cube::new);
        System.out.println("PartA: " + cubes.stream()
                .map(Cube::getExposedSidesA)
                .reduce(0, Integer::sum));


        // make a 3D space bigger that the cubes
        int minX = cubes.stream()
                .map(c -> c.x)
                .min(Integer::compareTo)
                .orElseThrow() - 1;
        int maxX = cubes.stream()
                .map(c -> c.x)
                .max(Integer::compareTo)
                .orElseThrow() + 1;
        int minY = cubes.stream()
                .map(c -> c.y)
                .min(Integer::compareTo)
                .orElseThrow() - 1;
        int maxY = cubes.stream()
                .map(c -> c.y)
                .max(Integer::compareTo)
                .orElseThrow() + 1;
        int minZ = cubes.stream()
                .map(c -> c.z)
                .min(Integer::compareTo)
                .orElseThrow() - 1;
        int maxZ = cubes.stream()
                .map(c -> c.z)
                .max(Integer::compareTo)
                .orElseThrow() + 1;

        //make a list of all the free cubes in the 3D space
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Cube c = new Cube(x, y, z);
                    if (!cubes.contains(c)) {
                        allFeeCubes.add(c);
                    }
                }
            }
        }

        //add to each free cube the adjacent cubes that are also free
        for (Cube c : allFeeCubes) {
            if (allFeeCubes.contains(new Cube(c.x - 1, c.y, c.z))) {
                c.canMoveTo.add(findCube(c.x - 1, c.y, c.z));
            }
            if (allFeeCubes.contains(new Cube(c.x + 1, c.y, c.z))) {
                c.canMoveTo.add(findCube(c.x + 1, c.y, c.z));
            }
            if (allFeeCubes.contains(new Cube(c.x, c.y - 1, c.z))) {
                c.canMoveTo.add(findCube(c.x, c.y - 1, c.z));
            }
            if (allFeeCubes.contains(new Cube(c.x, c.y + 1, c.z))) {
                c.canMoveTo.add(findCube(c.x, c.y + 1, c.z));
            }
            if (allFeeCubes.contains(new Cube(c.x, c.y, c.z - 1))) {
                c.canMoveTo.add(findCube(c.x, c.y, c.z - 1));
            }
            if (allFeeCubes.contains(new Cube(c.x, c.y, c.z + 1))) {
                c.canMoveTo.add(findCube(c.x, c.y, c.z + 1));
            }
        }

        for (Cube c : allFeeCubes) {
            //if there is no path from a free cube to some origin outside, it is encapsulated
            if (shortestPath(c, findCube(minX, minY, minZ)) == Integer.MAX_VALUE) {
                encapsulatedCubes.add(c);
            }
        }

        System.out.println("PartB: " + cubes.stream()
                .map(Cube::getExposedSidesB)
                .reduce(0, Integer::sum));
    }

    public static long shortestPath(Cube start, Cube end) {
        Queue<Cube> pq = new ArrayDeque<>(allFeeCubes.size());
        pq.add(start);

        Map<Cube, Integer> costSoFar = new HashMap<>();

        costSoFar.put(start, 0);

        while (!pq.isEmpty()) {
            Cube current = pq.remove();

            if (current.equals(end)) {
                break;
            }

            int currentCost = costSoFar.get(current);

            for (Cube neighbour : current.canMoveTo) {

                int newCost = currentCost + 1;

                if (!costSoFar.containsKey(neighbour) || newCost < costSoFar.get(neighbour)) {
                    costSoFar.put(neighbour, newCost);
                    pq.add(neighbour);
                }
            }
        }

        return costSoFar.getOrDefault(end, Integer.MAX_VALUE);
    }

    static class Cube {
        int x, y, z;
        List<Cube> canMoveTo = new ArrayList<>();

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

        public int getExposedSidesA() {
            int exposedSides = 6;

            if (cubes.contains(new Cube(x - 1, y, z))) {
                exposedSides--;
            }
            if (cubes.contains(new Cube(x + 1, y, z))) {
                exposedSides--;
            }
            if (cubes.contains(new Cube(x, y - 1, z))) {
                exposedSides--;
            }
            if (cubes.contains(new Cube(x, y + 1, z))) {
                exposedSides--;
            }
            if (cubes.contains(new Cube(x, y, z - 1))) {
                exposedSides--;
            }
            if (cubes.contains(new Cube(x, y, z + 1))) {
                exposedSides--;
            }
            return exposedSides;
        }

        public int getExposedSidesB() {
            int exposedSides = 6;

            if (cubes.contains(new Cube(x - 1, y, z)) || encapsulatedCubes.contains(new Cube(x - 1, y, z))) {
                exposedSides--;
            }
            if (cubes.contains(new Cube(x + 1, y, z)) || encapsulatedCubes.contains(new Cube(x + 1, y, z))) {
                exposedSides--;
            }
            if (cubes.contains(new Cube(x, y - 1, z)) || encapsulatedCubes.contains(new Cube(x, y - 1, z))) {
                exposedSides--;
            }
            if (cubes.contains(new Cube(x, y + 1, z)) || encapsulatedCubes.contains(new Cube(x, y + 1, z))) {
                exposedSides--;
            }
            if (cubes.contains(new Cube(x, y, z - 1)) || encapsulatedCubes.contains(new Cube(x, y, z - 1))) {
                exposedSides--;
            }
            if (cubes.contains(new Cube(x, y, z + 1)) || encapsulatedCubes.contains(new Cube(x, y, z + 1))) {
                exposedSides--;
            }
            return exposedSides;
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

    static Cube findCube(int x, int y, int z) {
        return allFeeCubes.stream()
                .filter(c -> c.x == x && c.y == y && c.z == z)
                .findFirst()
                .orElseThrow();
    }
}
