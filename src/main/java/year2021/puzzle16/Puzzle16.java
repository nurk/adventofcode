package year2021.puzzle16;

import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;

/**
 * Part A: 955
 * Part B: 158135423448
 */
public class Puzzle16 {
    static void main() {
        String input = util.Utils.getInput("2021/input16.txt").getFirst();


        String binaryInput = hexToBinary(input);
        Pair<String, Packet> result = Packet.parsePacket(binaryInput);
        Packet packet = result.getRight();

        System.out.println("Part A: " + packet.getVersionSum());
        System.out.println("Part B: " + packet.evaluate());
    }

    private static String hexToBinary(String input) {
        String binary = new BigInteger(input, 16).toString(2);
        return "0".repeat(input.length() * 4 - binary.length()) + binary;
    }
}
