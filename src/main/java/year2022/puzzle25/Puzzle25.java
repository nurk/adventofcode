package year2022.puzzle25;

import util.Utils;

public class Puzzle25 {

    //Part A 2==0=0===02--210---1
    public static void main(String[] args) {
        long sum = Utils.getInput("2022/input25.txt").stream()
                .mapToLong(Puzzle25::snafuToDecimal)
                .sum();
        System.out.println("Sum: " + sum);
        String snafu = decimalToSnafu(sum);
        System.out.println("Part A: " + snafu);
        System.out.println("Sanity check for sum: " + snafuToDecimal(snafu));
    }

    static String decimalToSnafu(long sum) {
        StringBuilder result = new StringBuilder();
        while (sum != 0) {
            long remainder = sum % 5;
            sum = sum / 5;

            if (remainder <= 2) {
                result.insert(0, remainder);
            } else {
                result = new StringBuilder(remainder == 3 ? "=" + result : "-" + result);
                sum++;
            }
        }

        return result.toString();
    }

    static long snafuToDecimal(String snafu) {
        String[] split = snafu.split("");

        long sum = 0;
        for (int i = 0; i < split.length; i++) {
            long s = switch (split[i]) {
                case "2" -> 2;
                case "1" -> 1;
                case "-" -> -1;
                case "=" -> -2;
                default -> 0;
            };
            sum += s * Math.pow(5, split.length - i - 1);
        }
        return sum;
    }
}
