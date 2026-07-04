package year2020.puzzle22;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Part A: 32366
 * Part B: 30891
 */
public class Puzzle22 {
    static void main() {
        partA();
        partB();
    }

    private static void partA() {
        List<Integer> player1Deck = new ArrayList<>(List.of(
                23,
                32,
                46,
                47,
                27,
                35,
                1,
                16,
                37,
                50,
                15,
                11,
                14,
                31,
                4,
                38,
                21,
                39,
                26,
                22,
                3,
                2,
                8,
                45,
                19
        ));
        List<Integer> player2Deck = new ArrayList<>(List.of(
                13,
                20,
                12,
                28,
                9,
                10,
                30,
                25,
                18,
                36,
                48,
                41,
                29,
                24,
                49,
                33,
                44,
                40,
                6,
                34,
                7,
                43,
                42,
                17,
                5
        ));

//        player1Deck = new ArrayList<>(List.of(
//                9,
//                2,
//                6,
//                3,
//                1));
//
//        player2Deck = new ArrayList<>(List.of(
//                5,
//                8,
//                4,
//                7,
//                10
//        ));

        while (!(player1Deck.isEmpty() || player2Deck.isEmpty())) {
            int playerOneCard = player1Deck.removeFirst();
            int playerTwoCard = player2Deck.removeFirst();

            if (playerOneCard > playerTwoCard) {
                player1Deck.add(playerOneCard);
                player1Deck.add(playerTwoCard);
            } else {
                player2Deck.add(playerTwoCard);
                player2Deck.add(playerOneCard);
            }
        }

        System.out.println("Player 1 deck: " + player1Deck);
        System.out.println("Player 2 deck: " + player2Deck);

        List<Integer> winnerDeck = player1Deck.isEmpty() ? player2Deck : player1Deck;
        int score = 0;
        for (int i = 0; i < winnerDeck.size(); i++) {
            score += winnerDeck.get(i) * (winnerDeck.size() - i);
        }

        System.out.println("Part A: " + score);
    }

    private static void partB() {
        List<Integer> player1Deck = new ArrayList<>(List.of(
                23,
                32,
                46,
                47,
                27,
                35,
                1,
                16,
                37,
                50,
                15,
                11,
                14,
                31,
                4,
                38,
                21,
                39,
                26,
                22,
                3,
                2,
                8,
                45,
                19
        ));
        List<Integer> player2Deck = new ArrayList<>(List.of(
                13,
                20,
                12,
                28,
                9,
                10,
                30,
                25,
                18,
                36,
                48,
                41,
                29,
                24,
                49,
                33,
                44,
                40,
                6,
                34,
                7,
                43,
                42,
                17,
                5
        ));

//        player1Deck = new ArrayList<>(List.of(
//                9,
//                2,
//                6,
//                3,
//                1));
//
//        player2Deck = new ArrayList<>(List.of(
//                5,
//                8,
//                4,
//                7,
//                10
//        ));

        Winner winner = playRound(player1Deck, player2Deck, 1);

        System.out.println("Player 1 deck: " + player1Deck);
        System.out.println("Player 2 deck: " + player2Deck);

        List<Integer> winnerDeck = winner == Winner.PLAYER_ONE ? player1Deck : player2Deck;
        int score = 0;
        for (int i = 0; i < winnerDeck.size(); i++) {
            score += winnerDeck.get(i) * (winnerDeck.size() - i);
        }

        System.out.println("Part B: " + score);
    }

    private static Winner playRound(List<Integer> player1Deck, List<Integer> player2Deck, int game) {
        int round = 1;
        Set<List<Integer>> playerOnePreviousDecks = new HashSet<>();
        Set<List<Integer>> playerTwoPreviousDecks = new HashSet<>();

        while (!(player1Deck.isEmpty() || player2Deck.isEmpty())) {
            // System.out.println("Game " + game + ", Round " + round);

            if (!playerOnePreviousDecks.add(new ArrayList<>(player1Deck)) ||
                    !playerTwoPreviousDecks.add(new ArrayList<>(player2Deck))) {
                return Winner.PLAYER_ONE;
            }

            int playerOneCard = player1Deck.removeFirst();
            int playerTwoCard = player2Deck.removeFirst();

            Winner winner;
            if (player1Deck.size() >= playerOneCard && player2Deck.size() >= playerTwoCard) {
                winner = playRound(new ArrayList<>(player1Deck.subList(0, playerOneCard)),
                        new ArrayList<>(player2Deck.subList(0, playerTwoCard)),
                        game + 1);
            } else {
                if (playerOneCard > playerTwoCard) {
                    winner = Winner.PLAYER_ONE;
                } else {
                    winner = Winner.PLAYER_TWO;
                }
            }

            if (winner == Winner.PLAYER_ONE) {
                player1Deck.add(playerOneCard);
                player1Deck.add(playerTwoCard);
            } else {
                player2Deck.add(playerTwoCard);
                player2Deck.add(playerOneCard);
            }
            round++;
        }

        if (player1Deck.isEmpty()) {
            return Winner.PLAYER_TWO;
        }
        return Winner.PLAYER_ONE;
    }

    private enum Winner {
        PLAYER_ONE,
        PLAYER_TWO
    }

}
