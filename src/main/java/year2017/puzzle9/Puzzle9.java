package year2017.puzzle9;

import util.Utils;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Part A: 9251
 * Part B: 4322
 */
public class Puzzle9 {

    public static void main(String[] args) {
        String input = Utils.getInput("2017/input9.txt").getFirst();

        // state machine parsing
        long groupScore = 0;
        long depth = 0;
        long garbageChars = 0;
        Deque<String> stack = new ArrayDeque<>();
        stack.push("start");

        long index = 0;
        while (index < input.length()) {
            char currentChar = input.charAt((int) index);
            switch (stack.peek()) {
                case "group":
                    if (currentChar == '{') {
                        stack.push("group");
                        depth++;
                    } else if (currentChar == '}') {
                        stack.poll();
                        groupScore += depth;
                        depth--;
                    } else if (currentChar == '<') {
                        stack.push("garbage");
                    } else if (currentChar == '!') {
                        stack.push("ignore");
                    }
                    break;
                case "garbage":
                    if (currentChar == '>') {
                        stack.poll();
                    } else if (currentChar == '!') {
                        stack.push("ignore");
                    } else {
                        garbageChars++;
                    }
                    break;
                case "ignore":
                    stack.poll();
                    break;
                default:
                    if (currentChar == '{') {
                        stack.push("group");
                        depth++;
                    }
            }
            index++;
        }

        System.out.println("Part A: " + groupScore);
        System.out.println("Part B: " + garbageChars);
    }
}
