package year2016.puzzle21;

import org.apache.commons.collections4.iterators.PermutationIterator;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Part A: gfdhebac
 * Part B: dhaegfbc
 */
public class Puzzle21 {

    //private static char[] input = "abcde".toCharArray();
    private static char[] input = "abcdefgh".toCharArray();
    private static List<String> instructions;

    static void main() {
//        System.out.println("Before: " + String.valueOf(input));
//        swapPosition("swap position 4 with position 0");
//        System.out.println("After: " + String.valueOf(input));
//        swapLetter("swap letter d with letter b");
//        System.out.println("After: " + String.valueOf(input));
//        reverse("reverse positions 0 through 4");
//        System.out.println("After: " + String.valueOf(input));
//        rotate("rotate left 1 step");
//        System.out.println("After: " + String.valueOf(input));
//        move("move position 1 to position 4");
//        System.out.println("After: " + String.valueOf(input));
//        move("move position 3 to position 0");
//        System.out.println("After: " + String.valueOf(input));
//        rotatePosition("rotate based on position of letter b");
//        System.out.println("After: " + String.valueOf(input));
//        rotatePosition("rotate based on position of letter d");
//        System.out.println("After: " + String.valueOf(input));

        instructions = Utils.getInput("2016/input21.txt");

        partA();
        partB();
    }

    private static void partB() {
        char[] chars = "abcdefgh".toCharArray();
        String result = "fbgdceah";
        String answer = "";

        PermutationIterator<Character> characterPermutationIterator = new PermutationIterator<>(Arrays.asList(ArrayUtils.toObject(
                chars)));

        while (characterPermutationIterator.hasNext()) {
            List<Character> next = characterPermutationIterator.next();
            input = ArrayUtils.toPrimitive(next.toArray(new Character[0]));
            doInstructions();
            if (new String(input).equals(result)) {
                answer = new String(ArrayUtils.toPrimitive(next.toArray(new Character[0])));
                break;
            }
        }

        System.out.println("Part B: " + answer);
    }

    private static void partA() {
        input = "abcdefgh".toCharArray();
        doInstructions();
        System.out.println("Part A: " + new String(input));
    }

    private static void doInstructions() {
        instructions.forEach(line -> {
            if (line.startsWith("swap position")) {
                swapPosition(line);
            } else if (line.startsWith("swap letter")) {
                swapLetter(line);
            } else if (line.startsWith("reverse positions")) {
                reverse(line);
            } else if (line.startsWith("rotate based on position of letter")) {
                rotatePosition(line);
            } else if (line.startsWith("rotate")) {
                rotate(line);
            } else if (line.startsWith("move position")) {
                move(line);
            } else {
                throw new IllegalArgumentException("invalid input " + line);
            }
        });
    }

    private static void swapPosition(String line) {
        //swap position 4 with position 0
        int swapA = Integer.parseInt(StringUtils.substringBetween(line, "swap position ", " with position "));
        int swapB = Integer.parseInt(StringUtils.substringAfter(line, "with position "));

        char a = input[swapA];

        input[swapA] = input[swapB];
        input[swapB] = a;
    }

    private static void swapLetter(String line) {
        //swap letter d with letter b
        String letterA = StringUtils.substringBetween(line, "swap letter ", " with letter ");
        String letterB = StringUtils.substringAfter(line, "with letter ");

        for (int i = 0; i < input.length; i++) {
            if (input[i] == letterA.charAt(0)) {
                input[i] = letterB.charAt(0);
            } else if (input[i] == letterB.charAt(0)) {
                input[i] = letterA.charAt(0);
            }
        }
    }

    private static void reverse(String line) {
        //reverse positions 0 through 4
        int posStart = Integer.parseInt(StringUtils.substringBetween(line, "reverse positions ", " through "));
        int posEnd = Integer.parseInt(StringUtils.substringAfter(line, "through "));

        while (posStart < posEnd) {
            char temp = input[posStart];
            input[posStart] = input[posEnd];
            input[posEnd] = temp;
            posStart++;
            posEnd--;
        }
    }

    private static void rotate(String line) {
        //rotate left 1 step
        String direction = StringUtils.substringBetween(line, "rotate ", " ");
        int steps = Integer.parseInt(StringUtils.substringBetween(line, direction + " ", " step"));

        rotate(direction, steps);
    }

    private static void rotate(String direction, int steps) {
        if (direction.equals("left")) {
            for (int i = 0; i < steps; i++) {
                char temp = input[0];
                System.arraycopy(input, 1, input, 0, input.length - 1);
                input[input.length - 1] = temp;
            }
        } else if (direction.equals("right")) {
            for (int i = 0; i < steps; i++) {
                char temp = input[input.length - 1];
                System.arraycopy(input, 0, input, 1, input.length - 1);
                input[0] = temp;
            }
        }
    }

    private static void move(String line) {
        //move position 1 to position 4
        int fromPosition = Integer.parseInt(StringUtils.substringBetween(line, "move position ", " to "));
        int toPosition = Integer.parseInt(StringUtils.substringAfter(line, "to position "));

        List<Character> list = new ArrayList<>(Arrays.asList(ArrayUtils.toObject(input)));
        char temp = list.remove(fromPosition);
        list.add(toPosition, temp);
        input = ArrayUtils.toPrimitive(list.toArray(new Character[0]));
    }

    private static void rotatePosition(String line) {
        //rotate based on position of letter b
        char letter = StringUtils.substringAfter(line, " letter ").charAt(0);
        int index = 0;
        for (int i = 0; i < input.length; i++) {
            if (input[i] == letter) {
                index = i;
                break;
            }
        }
        int steps = 1 + index;
        if (index >= 4) {
            steps++;
        }

        rotate("right", steps);
    }
}
