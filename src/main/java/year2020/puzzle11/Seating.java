package year2020.puzzle11;

import java.util.List;

public class Seating {

    private String[][] seats;

    public Seating(List<String> plan) {
        seats = new String[plan.size()][plan.get(0).length()];

        for (int i = 0; i < plan.size(); i++) {
            char[] row = plan.get(i).toCharArray();
            for (int j = 0; j < row.length; j++) {
                char c = row[j];
                seats[i][j] = String.valueOf(c);
            }
        }
    }

    /*
    If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
Otherwise, the seat's state does not change.
Floor (.) never changes; seats don't move, and nobody sits on the floor.

After one round of these rules, every seat in the example layout becomes occupied:
     */

    public void doRound() {
        String[][] newSeats = new String[seats.length][seats[0].length];
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j].equals(".")) {
                    newSeats[i][j] = ".";
                } else {
                    int occupiedSeats = numberOfNeighboursOccupied(i, j);
                    if (seats[i][j].equals("L") && occupiedSeats == 0) {
                        newSeats[i][j] = "#";
                    } else if (seats[i][j].equals("#") && occupiedSeats > 3) {
                        newSeats[i][j] = "L";
                    } else {
                        newSeats[i][j] = seats[i][j];
                    }
                }
            }
        }
        seats = newSeats;
    }

    public int numberOfNeighboursOccupied(int i, int j) {
        int occupiedSeats = 0;
        occupiedSeats += isOccupied(i - 1, j - 1);
        occupiedSeats += isOccupied(i - 1, j);
        occupiedSeats += isOccupied(i - 1, j + 1);
        occupiedSeats += isOccupied(i, j - 1);
        occupiedSeats += isOccupied(i, j + 1);
        occupiedSeats += isOccupied(i + 1, j - 1);
        occupiedSeats += isOccupied(i + 1, j);
        occupiedSeats += isOccupied(i + 1, j + 1);

        return occupiedSeats;
    }

    public int isOccupied(int i, int j) {
        try {
            if (seats[i][j].equals("#")) {
                return 1;
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }

    public int getNumberOfOccupiedSeats() {
        int sum = 0;
        for (int i = 0; i < seats.length; i++) {
            String[] row = seats[i];
            for (int j = 0; j < row.length; j++) {
                String s = row[j];
                if (s.equals("#")) {
                    sum++;
                }
            }
        }
        return sum;
    }

    public void print() {
        for (int i = 0; i < seats.length; i++) {
            String[] row = seats[i];
            for (int j = 0; j < row.length; j++) {
                String s = row[j];
                System.out.print(s);
            }
            System.out.println();
        }
    }
}
