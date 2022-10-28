package year2016.puzzle5;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;

public class Puzzle5 {
    public static void main(String[] args) {
        String doorId = "reyedfim";
        String partOne = "";

        int index = 0;

        List<String> partTwo = new ArrayList<>();
        partTwo.add(" ");
        partTwo.add(" ");
        partTwo.add(" ");
        partTwo.add(" ");
        partTwo.add(" ");
        partTwo.add(" ");
        partTwo.add(" ");
        partTwo.add(" ");

        while (partOne.length() != 8) {
            String md5Hex = DigestUtils.md5Hex(doorId + index);
            if (md5Hex.startsWith("00000")) {
                partOne = partOne + md5Hex.charAt(5);
            }
            index++;
        }

        System.out.println(partOne);

        index = 0;
        while (partTwo.contains(" ")) {
            String md5Hex = DigestUtils.md5Hex(doorId + index);
            if (md5Hex.startsWith("00000")) {
                try {
                    String pos = "" + md5Hex.charAt(5);
                    if (" ".equals(partTwo.get(Integer.parseInt(pos)))) {
                        partTwo.set(Integer.parseInt(pos), "" + md5Hex.charAt(6));
                    }
                } catch (Exception e) {
                    // ignore
                }
            }
            index++;
        }
        System.out.println(String.join("", partTwo));
    }
}
