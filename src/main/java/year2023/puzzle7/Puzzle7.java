package year2023.puzzle7;

import util.Utils;

import java.util.List;

/**
 * Part A: 250370104
 * Part B: 251735672
 */
public class Puzzle7 {
    public static void main(String[] args) {
        List<Bet> bets = Utils.getInput("2023/input7.txt", Bet::new);
        List<Bet> sortedBets = bets.stream()
                .sorted(Bet::compareTo)
                .toList();

        Long totalWinnings = 0L;
        for (int i = 0; i < sortedBets.size(); i++) {
            totalWinnings += (i + 1) * sortedBets.get(i).getBid();
        }

        System.out.println("Part A: " + totalWinnings);

        List<BetB> betsB = Utils.getInput("2023/input7.txt", BetB::new);
        List<BetB> sortedBetsB = betsB.stream()
                .sorted(BetB::compareTo)
                .toList();

        totalWinnings = 0L;
        for (int i = 0; i < sortedBetsB.size(); i++) {
            totalWinnings += (i + 1) * sortedBetsB.get(i).getBid();
        }

        System.out.println("Part B: " + totalWinnings);
    }
}
