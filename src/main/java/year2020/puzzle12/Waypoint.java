package year2020.puzzle12;

public class Waypoint {

    private int north = 1;
    private int east = 10;
    private int south = 0;
    private int west = 0;

    public void move(String direction, int value) {
        switch (direction) {
            case "N" -> north += value;
            case "E" -> east += value;
            case "S" -> south += value;
            case "W" -> west += value;
        }

        if (north < 0) {
            south = Math.abs(north);
            north = 0;
        }
        if (south < 0) {
            north = Math.abs(south);
            south = 0;
        }
        if (east < 0) {
            west = Math.abs(east);
            east = 0;
        }
        if (west < 0) {
            east = Math.abs(west);
            west = 0;
        }
    }

    public void rotate(String direction, int degrees) {
        int numberOfRotations = degrees / 90;
        for (int i = 0; i < numberOfRotations; i++) {
            int n, e, s, w = 0;
            if (direction.equals("R")) {
                n = west;
                e = north;
                s = east;
                w = south;
            } else {
                n = east;
                e = south;
                s = west;
                w = north;
            }
            north = n;
            east = e;
            south = s;
            west = w;
        }
    }

    public int getNorth() {
        return north;
    }

    public int getEast() {
        return east;
    }

    public int getSouth() {
        return south;
    }

    public int getWest() {
        return west;
    }
}
