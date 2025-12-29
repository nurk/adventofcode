package year2017.puzzle18;

import util.Utils;

import java.util.List;

/**
 * Part A: 1187
 * Part B: 5969
 */
public class Puzzle18 {
    public static void main(String[] args) {
        List<String> lines = Utils.getInput("2017/input18.txt");
        Duet duet = new Duet();

        System.out.println("Part A: " + duet.performSong(lines));

        DuetPartB duet0 = new DuetPartB(0, lines);
        DuetPartB duet1 = new DuetPartB(1, lines);
        duet0.setOtherDuet(duet1);
        duet1.setOtherDuet(duet0);

        while (!((duet0.isWaitingForReceive() || duet0.isTerminated()) &&
                (duet1.isWaitingForReceive() || duet1.isTerminated()))) {
            duet0.performSong();
            duet1.performSong();
        }

        System.out.println("Part B: " + duet1.getSendCount());
    }
}
