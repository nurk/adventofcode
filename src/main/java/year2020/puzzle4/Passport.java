package year2020.puzzle4;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Passport {

    private Map<String, String> data = new HashMap<>();

    private List<String> allFields = List.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid");
    private List<String> requiredFields = List.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");

    public void addData(String inputData) {
        String[] elements = inputData.split(" ");
        for (String s : elements) {
            String[] actualData = s.split(":");
            data.put(actualData[0], actualData[1]);
        }
    }

    public boolean isValid() {
        if (data.keySet().containsAll(requiredFields)) {
            for (Map.Entry<String, String> dataElement : data.entrySet()) {
                if (!isElementValid(dataElement.getKey(), dataElement.getValue())) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    private boolean isElementValid(String key, String value) {
        switch (key) {
            case "byr":
                int byr = Integer.parseInt(value);
                return byr >= 1920 && byr <= 2002;
            case "iyr":
                int iyr = Integer.parseInt(value);
                return iyr >= 2010 && iyr <= 2020;
            case "eyr":
                int eyr = Integer.parseInt(value);
                return eyr >= 2020 && eyr <= 2030;
            case "hgt":
                if (StringUtils.contains(value, "cm")) {
                    int hgtCm = Integer.parseInt(StringUtils.remove(value, "cm"));
                    return hgtCm >= 150 && hgtCm <= 193;
                }
                if (StringUtils.contains(value, "in")) {
                    int hgtCm = Integer.parseInt(StringUtils.remove(value, "in"));
                    return hgtCm >= 59 && hgtCm <= 76;
                }
                return false;
            case "hcl":
                if (value.length() != 7) {
                    return false;
                }
                return value.matches("^#[a-fA-F0-9]{6}");
            case "ecl":
                return List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(value);
            case "pid":
                if (value.length() != 9) {
                    return false;
                }
                return StringUtils.isNumeric(value);
            case "cid":
                return true;
        }
        return false;
    }
}
