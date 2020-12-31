package year2020.puzzle18;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FormulaPart2 {
    private String formula;
    private long result = 0;

    public FormulaPart2(String formula) {
        this.formula = formula;
    }

    public Long calculate() {
        String localFormula = formula;
        while (localFormula.contains("(")) {
            String part = StringUtils.substringBefore(StringUtils.substringAfterLast(localFormula, "("), ")");
            localFormula = StringUtils.replace(localFormula, "(" + part + ")", String.valueOf(calculatePart(part)));
        }
        result = calculatePart(localFormula);
        return result;
    }

    private long calculatePart(String part) {
        List<String> parts = new ArrayList<>(List.of(part.split(" ")));

        while (parts.size() > 1) {
            if (parts.contains("+")) {
                int i = parts.indexOf("+");
                long sum = Long.parseLong(parts.get(i - 1)) + Long.parseLong(parts.get(i + 1));
                parts.remove(i + 1);
                parts.remove(i);
                parts.remove(i - 1);
                parts.add(i - 1, String.valueOf(sum));
            } else if (parts.contains("*")) {
                int i = parts.indexOf("*");
                long multiple = Long.parseLong(parts.get(i - 1)) * Long.parseLong(parts.get(i + 1));
                parts.remove(i + 1);
                parts.remove(i);
                parts.remove(i - 1);
                parts.add(i - 1, String.valueOf(multiple));
            }
        }
        return Long.parseLong(parts.get(0));
    }

    public long getResult() {
        return result;
    }
}
