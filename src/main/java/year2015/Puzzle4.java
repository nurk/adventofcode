package year2015;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public class Puzzle4 {
    public static void main(String[] args) {
        String input = "yzbqklnj";
        int salt = 0;

        boolean found = false;
        while (!found) {
            salt++;
            found = StringUtils.startsWith(DigestUtils.md5Hex(input + salt), "000000");
        }

        System.out.println(salt);
    }
}
