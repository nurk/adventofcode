package year2020.puzzle4;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Puzzle4 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        List<Passport> passports = setup();

        int validPassports = 0;

        for (Passport passport1 : passports) {
            if (passport1.isValid()) {
                validPassports++;
            }
        }
        System.out.println(passports.size());
        System.out.println(validPassports);
    }

    private static List<Passport> setup() throws IOException, URISyntaxException {
        List<String> data = new ArrayList<>();
        Files.readAllLines(Utils.getInputPath("2020/input4.txt")).forEach(
                line -> {
                    data.add(line);
                }
        );

        List<Passport> passports = new ArrayList<>();
        Passport passport = new Passport();
        for (String datum : data) {
            if (StringUtils.isEmpty(datum)) {
                passports.add(passport);
                passport = new Passport();
            } else {
                passport.addData(datum);
            }
        }
        passports.add(passport);
        return passports;
    }
}
