package year2025.puzzle3;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class BatteryBank {
    private final String batteries;

    public BatteryBank(String batteries) {
        this.batteries = batteries;
    }

    public long getMaxJoltage(int numberOfDigits) {
        String maxJoltage = "";
        String remainingBatteries = batteries;
        for (int i = numberOfDigits - 1; i >= 0; i--) {
            String allButLastBatteries = remainingBatteries.substring(0, remainingBatteries.length() - i);
            String digit = getMaxDigit(allButLastBatteries);
            remainingBatteries = StringUtils.substringAfter(remainingBatteries, digit);
            maxJoltage = maxJoltage.concat(digit);
        }

        return Long.parseLong(maxJoltage);
    }

    private String getMaxDigit(String number) {
        return Arrays.stream(number.split(""))
                .map(Long::parseLong)
                .max(Long::compareTo)
                .map(String::valueOf)
                .orElseThrow();
    }
}
