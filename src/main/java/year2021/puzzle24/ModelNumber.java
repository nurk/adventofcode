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
}
