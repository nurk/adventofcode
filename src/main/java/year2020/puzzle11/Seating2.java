package year2020.puzzle11;

import java.util.List;
import java.util.function.Function;

public class Seating2 {

    private String[][] seats;

    public Seating2(List<String> plan) {
        seats = new String[plan.size()][plan.get(0).length()];

        for (int i = 0; i < plan.size(); i++) {
            char[] row = plan.get(i).toCharArray();
            for (int j = 0; j < row.length; j++) {
                char c = row[j];
                seats[i][j] = String.valueOf(c);
            }
        }
    }

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
                    } else if (seats[i][j].equals("#") && occupiedSeats > 4) {
                        newSeats[i][j] = "L";
                    } else {
                        newSeats[i][j] = seats[i][j];
                    }
                }
            }
        }
        seats = newSeats;
    }

    private int numberOfNeighboursOccupied(int i, int j) {
        int sum = 0;
        sum += verifyNeighbour((row -> row - 1), (col -> col), i, j);
        sum += verifyNeighbour((row -> row + 1), (col -> col), i, j);
        sum += verifyNeighbour((row -> row), (col -> col - 1), i, j);
        sum += verifyNeighbour((row -> row), (col -> col + 1), i, j);
        sum += verifyNeighbour((row -> row - 1), (col -> col - 1), i, j);
        sum += verifyNeighbour((row -> row - 1), (col -> col + 1), i, j);
        sum += verifyNeighbour((row -> row + 1), (col -> col - 1), i, j);
        sum += verifyNeighbour((row -> row + 1), (col -> col + 1), i, j);
        return sum;
    }

    private int verifyNeighbour(Function<Integer, Integer> row, Function<Integer, Integer> col, int i, int j) {
        while (true) {
            try {
                i = row.apply(i);
                j = col.apply(j);
                if (seats[i][j].equals("#")) {
                    return 1;
                } else if (seats[i][j].equals("L")) {
                    return 0;
                }
            } catch (Exception e) {
                return 0;
            }
        }
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
