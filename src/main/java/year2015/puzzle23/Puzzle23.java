package year2015.puzzle23;

import util.Utils;

import java.util.List;

public class Puzzle23 {

    //part A: 170
    //part B: 247
    public static void main(String[] args) {
        List<String> input = Utils.getInput("2015/input23.txt");
        long a = 1; // part b
        //long a = 0; // part a
        long b = 0;
        int index = 0;

        while (index < input.size()) {
            String[] split = input.get(index).split(" ");
            //System.out.println("a " + a + " b " + b + " " + input.get(index) + " index " + index);
            if (split[0].equals("hlf")) {
                if (split[1].contains("a")) {
                    a = a / 2;
                } else {
                    b = b / 2;
                }
                index++;
            }

            if (split[0].equals("tpl")) {
                if (split[1].contains("a")) {
                    a = a * 3;
                } else {
                    b = b * 3;
                }
                index++;
            }

            if (split[0].equals("inc")) {
                if (split[1].contains("a")) {
                    a++;
                } else {
                    b++;
                }
                index++;
            }

            if (split[0].equals("jmp")) {
                index += Integer.parseInt(split[1]);
            }

            if (split[0].equals("jie")) {
                if (split[1].contains("a")) {
                    if (a % 2 == 0) {
                        index += Integer.parseInt(split[2]);
                    } else {
                        index++;
                    }
                } else {
                    if (b % 2 == 0) {
                        index += Integer.parseInt(split[2]);
                    } else {
                        index++;
                    }
                }
            }

            if (split[0].equals("jio")) {
                if (split[1].contains("a")) {
                    if (a == 1) {
                        index += Integer.parseInt(split[2]);
                    } else {
                        index++;
                    }
                } else {
                    if (b == 1) {
                        index += Integer.parseInt(split[2]);
                    } else {
                        index++;
                    }
                }
            }
        }

        System.out.println(b);
    }
}
