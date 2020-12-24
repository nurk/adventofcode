package year2020.puzzle12;

public class NavigationB {

    private int north = 0;
    private int east = 0;
    private int south = 0;
    private int west = 0;

    private Waypoint waypoint = new Waypoint();

    public void move(String input) {
        String action = input.substring(0, 1);
        int value = Integer.parseInt(input.substring(1));

        switch (action) {
            case "N", "E", "S", "W" -> waypoint.move(action, value);
            case "F" -> moveWaypoints(value);
            case "L", "R" -> waypoint.rotate(action, value);
        }
    }

    private void moveWaypoints(int value) {
        north = north + value * waypoint.getNorth();
        east = east + value * waypoint.getEast();
        south = south + value * waypoint.getSouth();
        west = west + value * waypoint.getWest();
    }

    public int distance() {
        return Math.abs(north - south) + Math.abs(east - west);
    }


}
