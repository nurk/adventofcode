package year2022.puzzle13;

import util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Puzzle13 {

    private static List<SignalPair> signalPairs = new ArrayList<>();
    private static int pairIndex = 0;

    public static void main(String[] args) {
        // 2292 too low
        List<String> input = Utils.getInput("2022/input13.txt");

        int[] splitIndexes = Utils.getSplitIndexes(input);

        IntStream.range(0, splitIndexes.length - 1)
                .mapToObj(i -> input.subList(splitIndexes[i] + 1, splitIndexes[i + 1]))
                .forEach(signalPair -> signalPairs.add(new SignalPair(signalPair)));

        System.out.println(signalPairs.stream()
                .filter(SignalPair::areInRightOrder)
                .map(s -> s.index)
                .reduce(0, Integer::sum));
    }

    static class SignalPair {
        private final Signal leftSignal = new Signal();
        private final Signal rightSignal = new Signal();
        private final int index;

        public SignalPair(List<String> signalPair) {
            index = ++pairIndex;
            parseSignal(leftSignal, signalPair.get(0));
            parseSignal(rightSignal, signalPair.get(1));
        }

        public boolean areInRightOrder() {
            return leftSignal.inRightOrder(rightSignal);
        }

        private void parseSignal(Signal signal, String string) {
            Signal currentSignal = signal;
            for (String s : string.split("")) {
                switch (s) {
                    case "[" -> currentSignal = currentSignal.addChild(new Signal());

                    case "]" -> currentSignal = currentSignal.getParent();
                    case "," -> {
                    }
                    default -> currentSignal.addValue(Integer.parseInt(s));
                }
            }
        }
    }

    static class Signal {
        private Signal parent;
        private final List<Object> data = new ArrayList<>();

        public void addValue(Integer v) {
            this.data.add(v);
        }

        public Signal addChild(Signal child) {
            this.data.add(child);
            child.setParent(this);
            return child;
        }

        public Signal getParent() {
            return parent;
        }

        public void setParent(Signal parent) {
            this.parent = parent;
        }

        public boolean inRightOrder(Signal other) {
            boolean rightOrder = true;
            boolean onlyEquals = false;
            for (int i = 0; i < Math.min(data.size(), other.data.size()); i++) {
                if (data.get(i) instanceof Integer && other.data.get(i) instanceof Integer) {
                    if (data.get(i) == other.data.get(i)) {
                        onlyEquals = true;
                    }
                    if (((Integer) data.get(i)) > ((Integer) other.data.get(i))) {
                        rightOrder = false;
                    }
                } else if (data.get(i) instanceof Signal && other.data.get(i) instanceof Signal) {
                    if (!((Signal) data.get(i)).inRightOrder((Signal) other.data.get(i))) {
                        rightOrder = false;
                    }
                } else if (data.get(i) instanceof Integer) {
                    Signal newLeftSignal = new Signal();
                    newLeftSignal.addValue((Integer) data.get(i));
                    if (!newLeftSignal.inRightOrder((Signal) other.data.get(i))) {
                        rightOrder = false;
                    }
                } else {
                    Signal newRightSignal = new Signal();
                    newRightSignal.addValue((Integer) other.data.get(i));
                    if (!((Signal) data.get(i)).inRightOrder(newRightSignal)) {
                        rightOrder = false;
                    }
                }
            }

            if (onlyEquals || this.data.isEmpty() || other.data.isEmpty()) {
                if (data.size() > other.data.size()) {
                    rightOrder = false;
                }
            }

            return rightOrder;
        }
    }
}
