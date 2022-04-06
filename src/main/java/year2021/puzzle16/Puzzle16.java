package year2021.puzzle16;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class Puzzle16 {
    public static void main(String[] args) {
        //String packet = new ArrayList<>(Utils.getInput("2021/input16test.txt", (s) -> s)).get(0);
        //String packetBin = hexToBinary(packet);

        parsePackets("110100101111111000101000");

    }

    private static int parsePackets(String packet) {
        int version = Integer.valueOf(StringUtils.substring(packet, 0, 3), 2);
        int type = Integer.valueOf(StringUtils.substring(packet, 3, 6), 2);
        String remainder = StringUtils.substring(packet, 6);

        switch (type) {
            case 4:
                System.out.println(parseLiteral(remainder));
                break;
            default:
                parseOperator(remainder);
                break;
        }
        System.out.println(version);
        System.out.println(type);
        System.out.println(remainder);
        return 0;
    }

    private static Pair<Integer, List<String>> parseOperator(String remainder) {
        String operatorType = remainder.substring(0, 1);
        remainder = remainder.substring(1);
        return null;
    }

    private static int parseLiteral(String remainder) {
        String value = "";
        while (remainder.startsWith("1")) {
            value = value + StringUtils.removeStart(remainder.substring(0, 5), "1");
            remainder = remainder.substring(5);
        }

        value = value + StringUtils.removeStart(remainder.substring(0, 5), "0");
        remainder = remainder.substring(5);
        System.out.println(remainder);

        return Integer.valueOf(value, 2);
    }

    private static String hexToBinary(String hex) {
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        return hex;
    }
}
