package year2016.puzzle14;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Puzzle14 {
    static String salt = "ihaygndm";
    static Pattern threeOfTheSame = Pattern.compile(".*?([a-z\\d])\\1\\1.*", Pattern.CASE_INSENSITIVE);
    static boolean isPartA = false;
    static Map<String, String> hashes = new HashMap<>();


    // Part A: 15035
    // Part B: 19968
    public static void main(String[] args) {
        int keysFound = 0;
        int index = 0;
        while (keysFound != 64) {
            if (isKey(index)) {
                keysFound++;
            }
            index++;
        }
        System.out.println(--index);
    }

    static boolean isKey(int index) {
        String key = getKey(index);
        Matcher matcher = threeOfTheSame.matcher(key);
        if (matcher.matches()) {
            String letter = matcher.group(1);
            for (int i = index + 1; i <= index + 1000; i++) {
                if (getKey(i).contains(letter + letter + letter + letter + letter)) {
                    return true;
                }
            }
        }
        return false;
    }

    static String getKey(int index) {
        String key = salt + index;
        return hashes.computeIfAbsent(key, k -> {
            String hash = DigestUtils.md5Hex(k);
            if (!isPartA) {
                for (int i = 0; i < 2016; i++) {
                    hash = DigestUtils.md5Hex(hash);
                }
            }
            return hash;
        });
    }
}