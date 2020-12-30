package year2020.puzzle17;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Board4D {

    private String[][][][] board;
    private final int rows;
    private final int cols;
    private final int depth;
    private final int time;

    public Board4D(List<String> input) {
        rows = input.size() * 5;
        cols = input.get(0).length() * 5;
        depth = rows;
        time = rows;

        board = new String[rows][cols][depth][time];

        allEmpty();

        int curDepth = (depth / 2);
        int curTime = (time / 2);
        for (int i = 0; i < input.size(); i++) {
            int curRow = (rows / 3) + i;
            char[] chars = input.get(i).toCharArray();
            for (int j = 0; j < chars.length; j++) {
                int curCol = (cols / 3) + j;
                char aChar = chars[j];
                board[curRow][curCol][curDepth][curTime] = String.valueOf(aChar);
            }

        }
    }

    public void doCycle() {
        String[][][][] newBoard = new String[rows][cols][depth][time];
        for (int t = 0; t < time; t++) {
            for (int z = 0; z < depth; z++) {
                for (int r = 0; r < rows; r++) {
                    for (int c = 0; c < cols; c++) {
                        int nb = countActiveNeighboars(r, c, z, t);
                        if (isActive(r, c, z, t)) {
                            if (nb >= 2 && nb <= 3) {
                                newBoard[r][c][z][t] = "#";
                            } else {
                                newBoard[r][c][z][t] = ".";
                            }

                        } else {
                            if (nb == 3) {
                                newBoard[r][c][z][t] = "#";
                            } else {
                                newBoard[r][c][z][t] = ".";
                            }
                        }
                    }
                }
            }
        }

        board = newBoard;
    }

    public int getActive() {
        int sum = 0;
        for (int t = 0; t < time; t++) {
            for (int z = 0; z < depth; z++) {
                for (int r = 0; r < rows; r++) {
                    for (int c = 0; c < cols; c++) {
                        if (isActive(r, c, z, t)) {
                            sum++;
                        }
                    }
                }
            }
        }
        return sum;
    }

    public int countActiveNeighboars(int x, int y, int z, int t) {
        int activeNeigboars = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                for (int k = z - 1; k <= z + 1; k++) {
                    for (int l = t - 1; l <= t + 1; l++) {
                        if (!(i == x && j == y && k == z && l == t)) {
                            if (isActive(i, j, k, l)) {
                                activeNeigboars++;
                            }
                        }
                    }
                }
            }
        }
        return activeNeigboars;
    }

    public boolean isActive(int x, int y, int z, int t) {
        try {
            return StringUtils.equals(board[x][y][z][t], "#");
        } catch (Exception e) {
            return false;
        }
    }


    public void allEmpty() {
        for (int i = 0; i < board.length; i++) {
            String[][][] strings = board[i];
            for (int j = 0; j < strings.length; j++) {
                String[][] string = strings[j];
                for (int k = 0; k < string.length; k++) {
                    String[] strings1 = string[k];
                    for (int l = 0; l < strings1.length; l++) {
                        String s = strings1[l];
                        board[i][j][k][l] = ".";
                    }
                }
            }
        }
    }
}
