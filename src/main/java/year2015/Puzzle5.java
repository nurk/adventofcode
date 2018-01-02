package year2015;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Puzzle5 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println(new Word("xxyxx").isReallyNiceWord());
        AtomicInteger totalNiceWords = new AtomicInteger();
        AtomicInteger totalReallyNiceWords = new AtomicInteger();
        Files.readAllLines(Utils.getInputPath("2015/input5.txt")).forEach(
                line -> {
                    Word word = new Word(line);
                    if (word.isNiceWord()) {
                        totalNiceWords.getAndIncrement();
                    }
                    if (word.isReallyNiceWord()) {
                        totalReallyNiceWords.getAndIncrement();
                    }
                }
        );
        System.out.println(totalNiceWords);
        System.out.println(totalReallyNiceWords);

    }

    static class Word {
        private String word;

        public Word(String word) {
            this.word = word;
        }

        public boolean isNiceWord() {
            return hasAtLeastThreeVowels() && hasTwiceInARowLetter() && !hasNaughtySequence();
        }

        public boolean isReallyNiceWord() {
            return hasRepeatingLetterWithOnePlaceBetween() && hasPairOfLetterThatDoNotOverlap();
        }

        private boolean hasPairOfLetterThatDoNotOverlap() {
            for (int i = 0; i < word.length()-2; i++) {
                String sequence = StringUtils.substring(word, i, i + 2);
                if (StringUtils.contains(StringUtils.replaceOnce(word, sequence, "&&"), sequence)) {
                    return true;
                }
            }
            return false;
        }

        private boolean hasRepeatingLetterWithOnePlaceBetween() {
            char[] chars = word.toCharArray();
            char previous = chars[0];
            for (int i = 1; i < chars.length - 1; i++) {
                char next = chars[i + 1];
                if (previous == next) {
                    return true;
                }
                previous = chars[i];

            }
            return false;
        }

        private boolean hasAtLeastThreeVowels() {
            int vowels = 0;
            for (char c : word.toCharArray()) {
                if (StringUtils.contains("aeiou", c)) {
                    vowels++;
                }
            }
            return vowels >= 3;
        }

        private boolean hasTwiceInARowLetter() {
            char[] chars = word.toCharArray();
            char previous = chars[0];
            for (int i = 1; i < chars.length; i++) {
                char current = chars[i];
                if (current == previous) {
                    return true;
                }
                previous = current;

            }
            return false;
        }

        private boolean hasNaughtySequence() {
            List<String> naugthyWords = Lists.newArrayList("ab", "cd", "pq", "xy");
            for (String naugthyWord : naugthyWords) {
                if (StringUtils.contains(word, naugthyWord)) {
                    return true;
                }
            }
            return false;
        }
    }
}
