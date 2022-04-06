package year2015.puzzle7;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.math.BigInteger;
import java.util.*;

public class Puzzle7 {
    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Utils.getInput("2015/input7.txt", (s) -> s));

        BigInteger a = solve(new ArrayList<>(input));

        input.removeIf(signal -> signal.endsWith("-> b"));
        input.add(a + " -> b");

        solve(input);
    }

    private static BigInteger solve(List<String> input) {
        Map<String, BigInteger> results = new HashMap<>();

        while (!input.isEmpty()) {
            for (int i = input.size() - 1; i >= 0; i--) {
                String s = input.get(i);
                String[] split = s.split(" -> ");

                Optional<Operators> operator = Arrays.stream(Operators.values())
                        .filter(o -> split[0].contains(o.getName()))
                        .findFirst();

                if (operator.isPresent()) {
                    String[] factors = split[0].split(operator.orElseThrow().getName());
                    boolean allFactorsFoundOrNumeric = true;
                    for (String factor : factors) {
                        if(StringUtils.isEmpty(factor)){
                            continue;
                        }
                        if (!StringUtils.isNumeric(factor) && !results.containsKey(factor)) {
                            allFactorsFoundOrNumeric = false;
                        }
                    }

                    if (allFactorsFoundOrNumeric) {
                        List<BigInteger> factorInts = new ArrayList<>();
                        for (String factor : factors) {
                            if(StringUtils.isEmpty(factor)){
                                continue;
                            }
                            if (StringUtils.isNumeric(factor)) {
                                factorInts.add(new BigInteger(factor));
                            } else {
                                factorInts.add(results.get(factor));
                            }
                        }
                        results.put(split[1], operator.orElseThrow().doOperation(factorInts));
                        input.remove(i);
                    }
                } else {
                    if(StringUtils.isNumeric(split[0])){
                        results.put(split[1], new BigInteger(split[0]));
                        input.remove(i);
                    } else if (results.containsKey(split[0])){
                        results.put(split[1], results.get(split[0]));
                        input.remove(i);
                    }
                }
            }
        }

        System.out.println(results.get("a"));

        return results.get("a");
    }


}
