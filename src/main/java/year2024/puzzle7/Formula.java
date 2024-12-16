package year2024.puzzle7;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Formula {

    @Getter
    private final long result;
    private final List<Long> terms;

    public Formula(String line) {
        this.result = Long.parseLong(StringUtils.substringBefore(line, ":"));

        this.terms = Arrays.stream(StringUtils.substringAfter(line, ": ").split(" "))
                .map(Long::parseLong)
                .toList();
    }

    public boolean isPossibleA() {
        return reduceA(new ArrayList<>(terms));
    }

    public boolean isPossibleB() {
        return reduceB(new ArrayList<>(terms));
    }

    private boolean reduceA(List<Long> terms) {
        if (terms.size() == 1 && terms.getFirst() == this.result) {
            return true;
        }
        if (terms.size() == 1 && terms.getFirst() != this.result) {
            return false;
        }
        if (terms.getFirst() > this.result) {
            return false;
        }
        long a = terms.removeFirst();
        long b = terms.removeFirst();

        List<Long> aBranch = new ArrayList<>(terms);
        List<Long> bBranch = new ArrayList<>(terms);

        aBranch.addFirst(a + b);
        bBranch.addFirst(a * b);

        if (reduceA(aBranch)) {
            return true;
        }
        return reduceA(bBranch);
    }

    private boolean reduceB(List<Long> terms) {
        if (terms.size() == 1 && terms.getFirst() == this.result) {
            return true;
        }
        if (terms.size() == 1 && terms.getFirst() != this.result) {
            return false;
        }
        if (terms.getFirst() > this.result) {
            return false;
        }
        long a = terms.removeFirst();
        long b = terms.removeFirst();

        List<Long> aBranch = new ArrayList<>(terms);
        List<Long> bBranch = new ArrayList<>(terms);
        List<Long> cBranch = new ArrayList<>(terms);

        aBranch.addFirst(a + b);
        bBranch.addFirst(a * b);
        cBranch.addFirst(Long.valueOf(a + "" + b));

        if (reduceB(aBranch)) {
            return true;
        }
        if (reduceB(bBranch)) {
            return true;
        }
        return reduceB(cBranch);
    }

    @Override
    public String toString() {
        return "Formula{" +
                "result=" + result +
                ", terms=" + terms +
                '}';
    }
}
