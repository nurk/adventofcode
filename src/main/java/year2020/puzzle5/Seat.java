package year2020.puzzle5;

public class Seat implements Comparable<Seat> {
    private Integer seatId;
    private int lowerRow = 0;
    private int upperRow = 127;
    private int lowerCol = 0;
    private int upperCol = 7;

    public Seat(String seat) {
        char[] chars = seat.toCharArray();
        for (char aChar : chars) {
            switch (aChar) {
                case 'F':
                    upperRow = (upperRow - lowerRow - 1) / 2 + lowerRow;
                    break;
                case 'B':
                    lowerRow = upperRow - ((upperRow - lowerRow + 1) / 2) + 1;
                    break;
                case 'L':
                    upperCol = (upperCol - lowerCol - 1) / 2 + lowerCol;
                    break;
                case 'R':
                    lowerCol = upperCol - ((upperCol - lowerCol + 1) / 2) + 1;
                    break;
            }
        }
        seatId = (lowerRow * 8) + lowerCol;
    }

    @Override
    public int compareTo(Seat o) {
        return this.seatId.compareTo(o.seatId);
    }

    public void print() {
        System.out.println(seatId);
    }

    public Integer getSeatId() {
        return seatId;
    }
}
