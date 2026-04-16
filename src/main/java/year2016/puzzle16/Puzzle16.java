package year2016.puzzle16;

import org.apache.commons.lang3.StringUtils;

/**
 * Part A: 10010110010011110
 * Part B: 01101011101100011
 */
public class Puzzle16 {
    static void main() {
        String initialState = "10010000000110000";

        System.out.println("Part A: " + getCRC(initialState, 272));
        System.out.println("Part B: " + getCRC(initialState, 35651584));
    }

    private static String getCRC(String initialState, int length) {
        String result = initialState;

        while (result.length() < length) {
            StringBuilder reversedAndInverted = new StringBuilder();
            for (char i : StringUtils.reverse(result).toCharArray()) {
                if (i == '0') {
                    reversedAndInverted.append("1");
                } else {
                    reversedAndInverted.append("0");
                }

            }
            result = result + "0" + reversedAndInverted;
        }

        result = StringUtils.substring(result, 0, length);

        return calculateChecksum(result);
    }

    public static String calculateChecksum(String input) {
        StringBuilder checksum;

        do {
            checksum = new StringBuilder();
            for (int i = 0; i < input.length(); i = i + 2) {
                if (input.charAt(i) == input.charAt(i + 1)) {
                    checksum.append("1");
                } else {
                    checksum.append("0");
                }
            }
            input = checksum.toString();
        } while (checksum.length() % 2 == 0);

        return checksum.toString();
    }
}
