package year2022.puzzle8;

import util.Utils;

import java.util.List;
import java.util.function.Function;

public class Puzzle8 {

    private static int[][] board;

    public static void main(String[] args) {
        initBoard("2022/input8.txt");

        int visible = 0;
        int score = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (isVisible(i, j)) {
                    visible++;
                }
                int scenicScore = scenicScore(i, j);
                if (scenicScore > score) {
                    score = scenicScore;
                }
            }
        }

        System.out.println(visible);
        System.out.println(score);
    }

    private static boolean isVisible(int i, int j) {
        return isVisible(i, j, r -> r - 1, c -> c) ||
                isVisible(i, j, r -> r + 1, c -> c) ||
                isVisible(i, j, r -> r, c -> c - 1) ||
                isVisible(i, j, r -> r, c -> c + 1);
    }

    private static int scenicScore(int i, int j) {
        return scenicScore(i, j, r -> r - 1, c -> c) *
                scenicScore(i, j, r -> r + 1, c -> c) *
                scenicScore(i, j, r -> r, c -> c - 1) *
                scenicScore(i, j, r -> r, c -> c + 1);
    }

    private static boolean isVisible(int i, int j, Function<Integer, Integer> row, Function<Integer, Integer> col) {
        int r = row.apply(i);
        int c = col.apply(j);
        try {
            while (true) {
                if (board[r][c] >= board[i][j]) {
                    return false;
                }
                r = row.apply(r);
                c = col.apply(c);
            }
        } catch (Exception e) {
            return true;
        }
    }

    private static int scenicScore(int i, int j, Function<Integer, Integer> row, Function<Integer, Integer> col) {
        int score = 0;
        int r = row.apply(i);
        int c = col.apply(j);
        try {
            while (true) {
                if (board[r][c] < board[i][j]) {
                    score++;
                } else {
                    score++;
                    break;
                }
                r = row.apply(r);
                c = col.apply(c);
            }
        } catch (Exception ignored) {
        }
        return score;
    }

    private static void initBoard(String file) {
        List<String> lines = Utils.getInput(file);
        board = new int[lines.size()][lines.size()];

        for (int i = 0; i < lines.size(); i++) {
            String[] split = lines.get(i).split("");
            for (int j = 0; j < split.length; j++) {
                board[i][j] = Integer.parseInt(split[j]);
            }
        }
    }
}
