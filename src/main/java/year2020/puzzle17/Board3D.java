package year2020.puzzle17;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Board3D {

    private String[][][] board;
    private final int rows;
    private final int cols;
    private final int depth;

    public Board3D(List<String> input) {
        rows = input.size() * 5;
        cols = input.get(0).length() * 5;
        depth = rows;

        board = new String[rows][cols][depth];

        allEmpty();

        int curDepth = (depth / 2);
        for (int i = 0; i < input.size(); i++) {
            int curRow = (rows / 3) + i;
            char[] chars = input.get(i).toCharArray();
            for (int j = 0; j < chars.length; j++) {
                int curCol = (cols / 3) + j;
                char aChar = chars[j];
                board[curRow][curCol][curDepth] = String.valueOf(aChar);
            }

        }
    }

    public void doCycle() {
        String[][][] newBoard = new String[rows][cols][depth];
        for (int z = 0; z < depth; z++) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    int nb = countActiveNeighboars(r, c, z);
                    if (isActive(r, c, z)) {
                        if (nb >= 2 && nb <= 3) {
                            newBoard[r][c][z] = "#";
                        } else {
                            newBoard[r][c][z] = ".";
                        }

                    } else {
                        if (nb == 3) {
                            newBoard[r][c][z] = "#";
                        } else {
                            newBoard[r][c][z] = ".";
                        }
                    }
                }
            }
        }

        board = newBoard;
    }

    public int getActive() {
        int sum = 0;
        for (int i = 0; i < board.length; i++) {
            String[][] strings = board[i];
            for (int j = 0; j < strings.length; j++) {
                String[] string = strings[j];
                for (int k = 0; k < string.length; k++) {
                    if(isActive(i,j,k)){
                        sum++;
                    }

                }
            }
        }
        return sum;
    }

    public int countActiveNeighboars(int x, int y, int z) {
        int activeNeigboars = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                for (int k = z - 1; k <= z + 1; k++) {
                    if (!(i == x && j == y && k == z)) {
                        if (isActive(i, j, k)) {
                            activeNeigboars++;
                        }
                    }
                }
            }
        }
        return activeNeigboars;
    }

    public boolean isActive(int x, int y, int z) {
        try {
            return StringUtils.equals(board[x][y][z], "#");
        } catch (Exception e) {
            return false;
        }
    }


    public void allEmpty() {
        for (int i = 0; i < board.length; i++) {
            String[][] strings = board[i];
            for (int j = 0; j < strings.length; j++) {
                String[] string = strings[j];
                for (int k = 0; k < string.length; k++) {
                    board[i][j][k] = ".";

                }
            }
        }
    }

    public void print() {
        for (int z = 0; z < depth; z++) {
            System.out.println("z = " + z);
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    System.out.print(board[r][c][z]);
                }
                System.out.println();
            }
        }
    }

}
