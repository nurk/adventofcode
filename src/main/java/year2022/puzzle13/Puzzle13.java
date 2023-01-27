package year2022.puzzle13;

import util.Utils;

import java.util.ArrayList;
import java.util.List;

// does not work, gave up and cheated on Puzzle13B
public class Puzzle13 {

    private static final List<SignalPair> signalPairs = new ArrayList<>();
    private static int pairIndex = 0;

    public static void main(String[] args) {
        // 2292 too low
        List<String> input = Utils.getInput("2022/input13.txt");

        Utils.splitOnBlankLine(input).forEach(signalPair -> signalPairs.add(new SignalPair(signalPair)));


        System.out.println(signalPairs.stream()
                .filter(SignalPair::areInRightOrder)
                .map(s -> s.index)
                .peek(System.out::println)
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
            boolean onlyEquals = true;

            for (int i = 0; i < Math.min(data.size(), other.data.size()); i++) {
                if (data.get(i) instanceof Integer && other.data.get(i) instanceof Integer) {
                    if (data.get(i) != other.data.get(i)) {
                        onlyEquals = false;
                    }
                    if (((Integer) data.get(i)) > ((Integer) other.data.get(i))) {
                        return false;
                    }
                } else if (data.get(i) instanceof Signal && other.data.get(i) instanceof Signal) {
                    onlyEquals = false;
                    if (!((Signal) data.get(i)).inRightOrder((Signal) other.data.get(i))) {
                        return false;
                    }
                }
                //mixed types
                else if (data.get(i) instanceof Integer) {
                    onlyEquals = false;
                    Signal newLeftSignal = new Signal();
                    newLeftSignal.addValue((Integer) data.get(i));
                    if (!newLeftSignal.inRightOrder((Signal) other.data.get(i))) {
                        return false;
                    }
                } else {
                    onlyEquals = false;
                    Signal newRightSignal = new Signal();
                    newRightSignal.addValue((Integer) other.data.get(i));
                    if (!((Signal) data.get(i)).inRightOrder(newRightSignal)) {
                        return false;
                    }
                }
            }

            if (onlyEquals && (this.data.size() > other.data.size())) {
                rightOrder = false;
            }

            return rightOrder;
        }
    }
}
