package year2020.puzzle9;

import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Puzzle9 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<Long> numbers = new ArrayList<>();
        Files.readAllLines(Utils.getInputPath("2020/input9.txt")).forEach(
                line -> numbers.add(Long.parseLong(line))
        );

        long errorNumber = 0;

        for (int i = 25; i < numbers.size(); i++) {
            long numberToCheck = numbers.get(i);
            List<Long> checkList = numbers.subList(i - 25, i);

            boolean foundSum = false;
            for (int j = 0; j < checkList.size() - 1; j++) {
                for (int k = j + 1; k < checkList.size(); k++) {
                    if (checkList.get(j) + checkList.get(k) == numberToCheck) {
                        foundSum = true;
                        break;
                    }
                }
            }

            if (!foundSum) {
                errorNumber = numberToCheck;
                System.out.println("Not a sum: " + numberToCheck);
                break;
            }
        }

        for (int i = 0; i < numbers.size(); i++) {
            long sum = 0;
            int index = i;
            SortedSet<Long> usedNumbers = new TreeSet<>();
            while (sum < errorNumber) {
                usedNumbers.add(numbers.get(index));
                sum += numbers.get(index);
                index++;
            }
            if(sum == errorNumber){
                System.out.println("Found sum");
                System.out.println(usedNumbers.last() + usedNumbers.first());
                break;
            }
        }
    }
}
