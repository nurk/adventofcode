package year2020.puzzle18;

import org.apache.commons.lang3.StringUtils;

public class Formula {
    private String formula;
    private long result = 0;

    public Formula(String formula) {
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
        String[] parts = part.split(" ");
        long localResult = Long.parseLong(parts[0]);

        for (int i = 1; i < parts.length; i += 2) {
            switch (parts[i]) {
                case "*":
                    localResult *= Long.parseLong(parts[i + 1]);
                    break;
                case "+":
                    localResult += Long.parseLong(parts[i + 1]);
                    break;
            }
        }

        return localResult;
    }

    public long getResult() {
        return result;
    }
}
