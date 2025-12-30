package year2020.puzzle3;

public class MapPuzzle3 {
    private String[][] map;
    private int columns;
    private int rows;
    private int currentRow = 0;
    private int currentColumn = 0;

    public MapPuzzle3(int rows, int columns) {
        map = new String[rows][columns];
        this.columns = columns;
        this.rows = rows;
    }

    public void addRow(int rowNumber, String row) {
        for (int i = 0; i < row.toCharArray().length; i++) {
            map[rowNumber][i] = String.valueOf(row.toCharArray()[i]);
        }
    }

    public void print() {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                System.out.print(map[row][col]);
            }
            System.out.println("");
        }
    }

    public void move(int right, int down) {
        currentRow = currentRow + down;
        currentColumn = (currentColumn + right) % columns;
    }

    public boolean isFinish() {
        return currentRow >= rows;
    }

    public boolean isTree() {
        return map[currentRow][currentColumn].equals("#");
    }

    public void reset() {
        currentColumn = 0;
        currentRow = 0;
    }
}
