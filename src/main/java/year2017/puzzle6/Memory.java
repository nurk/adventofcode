package year2017.puzzle6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Memory {

    private List<String> seen = new ArrayList<>();
    private List<Integer> banks = new CircularList<>();

    public Memory() {
        //4	10	4	1	8	4	9	14	5	1	14	15	0	15	3	5
        banks.add(4);
        banks.add(10);
        banks.add(4);
        banks.add(1);
        banks.add(8);
        banks.add(4);
        banks.add(9);
        banks.add(14);
        banks.add(5);
        banks.add(1);
        banks.add(14);
        banks.add(15);
        banks.add(0);
        banks.add(15);
        banks.add(3);
        banks.add(5);
        seen.add(banksToString());

        int numberOfDistributions = 0;
        int indexOfSeen;
        while (true) {
            numberOfDistributions++;
            doADistribution();
            if (seen.contains(banksToString())) {
                indexOfSeen = seen.indexOf(banksToString());
                System.out.println("index of seen " + indexOfSeen);
                break;
            }
            seen.add(banksToString());
        }

        System.out.println(numberOfDistributions);
        System.out.println(numberOfDistributions - indexOfSeen);
    }

    private void doADistribution() {
        int largest = getLargestBank();
        int largestIndex = 0;
        for (int i = 0; i < banks.size(); i++) {
            if (banks.get(i) == largest) {
                largestIndex = i;
                break;
            }
        }
        distribute(largest, largestIndex);
    }

    private void distribute(int amountToDistribute, int largestIndex) {
        banks.set(largestIndex, 0);
        for (int i = amountToDistribute; i > 0; i--) {
            largestIndex++;
            banks.set(largestIndex, banks.get(largestIndex) + 1);
        }
    }

    private int getLargestBank() {
        List<Integer> sorted = new ArrayList<>(banks);
        Collections.sort(sorted);
        return sorted.get(sorted.size() - 1);
    }

    private String banksToString() {
        return banks.stream().map(Object::toString)
                .collect(Collectors.joining(","));
    }

    private static class CircularList<T> extends ArrayList<T> {
        @Override
        public T get(int index) {
            return super.get(index % size());
        }

        @Override
        public T set(int index, T element) {
            return super.set(index % size(), element);
        }
    }
}
