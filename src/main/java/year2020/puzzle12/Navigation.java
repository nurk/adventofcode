package year2020.puzzle12;

import java.util.List;

public class Navigation {
    private String heading = "E";

    private List<String> directions = List.of("N", "E", "S", "W");
    private List<String> invertedDirections = List.of("W", "S", "E", "N");

    private int north = 0;
    private int east = 0;
    private int south = 0;
    private int west = 0;

    public void move(String input) {
        String action = input.substring(0, 1);
        int value = Integer.parseInt(input.substring(1));

        switch (action) {
            case "N", "E", "S", "W" -> moveInDirection(action, value);
            case "F" -> moveInDirection(heading, value);
            case "L", "R" -> rotate(action, value);
        }
    }

    public int distance() {
        return Math.abs(north - south) + Math.abs(east - west);
    }

    private void rotate(String action, int value) {
        int numberOfRotations = value / 90;

        if (action.equals("L")) {
            int startIndex = invertedDirections.indexOf(heading);
            heading = invertedDirections.get((startIndex + numberOfRotations) % 4);
        } else {
            int startIndex = directions.indexOf(heading);
            heading = directions.get((startIndex + numberOfRotations) % 4);
        }
    }

    private void moveInDirection(String direction, int value) {
        switch (direction) {
            case "N" -> north += value;
            case "E" -> east += value;
            case "S" -> south += value;
            case "W" -> west += value;
        }
    }

}
