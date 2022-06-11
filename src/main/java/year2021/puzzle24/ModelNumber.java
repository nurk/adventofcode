package year2021.puzzle24;

import org.apache.commons.lang3.StringUtils;

public class ModelNumber {
    private String modelNumber;
    private String remaining;

    public ModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
        this.remaining = modelNumber;
    }

    public long getInput() {
        long input = Long.parseLong(StringUtils.substring(remaining, 0, 1));
        remaining = StringUtils.substring(remaining, 1);
        return input;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public String decrement() {
        long number = Long.parseLong(modelNumber);
        number--;
        while (String.valueOf(number).length() != 14 || String.valueOf(number).contains("0")) {
            number--;
        }
        modelNumber = String.valueOf(number);
        remaining = modelNumber;
        return modelNumber;
    }
}
