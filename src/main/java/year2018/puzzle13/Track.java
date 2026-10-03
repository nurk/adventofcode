package year2018.puzzle13;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Track {
    private final String[][] tracks;
    @Getter
    private final List<Cart> carts = new ArrayList<>();

    public Track(List<String> input, int cols) {
        tracks = new String[input.size()][cols];
        IntStream.range(0, input.size())
                .forEach(row -> {
                    List<String> columns = Arrays.stream(input.get(row).split("")).toList();
                    IntStream.range(0, cols)
                            .forEach(column -> {
                                String value = "";
                                try {
                                    value = columns.get(column);
                                } catch (IndexOutOfBoundsException e) {
                                    // Handle the exception if needed
                                }
                                if (value.equals("^")) {
                                    carts.add(new Cart(row, column, Heading.NORTH));
                                    tracks[row][column] = "|";
                                } else if (value.equals("v")) {
                                    carts.add(new Cart(row, column, Heading.SOUTH));
                                    tracks[row][column] = "|";
                                } else if (value.equals("<")) {
                                    carts.add(new Cart(row, column, Heading.WEST));
                                    tracks[row][column] = "-";
                                } else if (value.equals(">")) {
                                    carts.add(new Cart(row, column, Heading.EAST));
                                    tracks[row][column] = "-";
                                } else {
                                    tracks[row][column] = value;
                                }
                            });
                });
    }

    public void move(Cart cart) {
        cart.move();
        String track = tracks[cart.getRow()][cart.getColumn()];
        if (track.equals("/")) {
            if (cart.getHeading() == Heading.NORTH) {
                cart.setHeading(Heading.EAST);
            } else if (cart.getHeading() == Heading.SOUTH) {
                cart.setHeading(Heading.WEST);
            } else if (cart.getHeading() == Heading.EAST) {
                cart.setHeading(Heading.NORTH);
            } else if (cart.getHeading() == Heading.WEST) {
                cart.setHeading(Heading.SOUTH);
            }
        } else if(track.equals("\\")) {
            if (cart.getHeading() == Heading.NORTH) {
                cart.setHeading(Heading.WEST);
            } else if (cart.getHeading() == Heading.SOUTH) {
                cart.setHeading(Heading.EAST);
            } else if (cart.getHeading() == Heading.EAST) {
                cart.setHeading(Heading.SOUTH);
            } else if (cart.getHeading() == Heading.WEST) {
                cart.setHeading(Heading.NORTH);
            }
        } else if (track.equals("+")) {
            cart.takeIntersection();
        }
    }

    @Override
    public String toString() {
        return Arrays.stream(tracks)
                .map(points -> String.join("", Arrays.stream(points).map(String::valueOf).toList()))
                .collect(joining("\n"));
    }
}
