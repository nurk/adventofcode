package year2020.puzzle15;

import java.util.HashMap;
import java.util.Map;

public class Puzzle15 {

    private static final Map<Integer, Difference> valueDiff = new HashMap<>();

    public static void main(String[] args) {
        //16,11,15,0,1,7
        valueDiff.put(16, new Difference(1));
        valueDiff.put(11, new Difference(2));
        valueDiff.put(15, new Difference(3));
        valueDiff.put(0, new Difference(4));
        valueDiff.put(1, new Difference(5));
        valueDiff.put(7, new Difference(6));


        int newNumber = 7;
        for (int i = 7; i < 30000001; i++) {
            int finalI = i;
            if (valueDiff.containsKey(newNumber) && valueDiff.get(newNumber).getSecondToLastSeen() != -1) {
                newNumber = valueDiff.get(newNumber).getDifference();
                valueDiff.merge(newNumber, new Difference(i), (prev, indexed) -> prev.seenAgainAt(finalI));
            } else {
                newNumber = 0;
                valueDiff.merge(newNumber, new Difference(i), (prev, indexed) -> prev.seenAgainAt(finalI));
            }
        }

        System.out.println(newNumber);
    }
}
