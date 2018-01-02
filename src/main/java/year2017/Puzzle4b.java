package year2017;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

public class Puzzle4b {

    public static void main(String... args) throws Exception {
        AtomicInteger total = new AtomicInteger();
        AtomicInteger totalValid = new AtomicInteger();
        AtomicInteger totalPermutationValid = new AtomicInteger();

        Files.readAllLines(getInputUri()).forEach(
                line -> {
                    total.getAndIncrement();

                    String[] split = StringUtils.split(line, " ");
                    HashSet<Word> permutationSet = new HashSet<>();
                    if (split.length == Sets.newHashSet(split).size()) {
                        totalValid.getAndIncrement();
                        for (String word : split) {
                            permutationSet.add(new Word(word));
                        }
                        if (permutationSet.size() == split.length) {
                            totalPermutationValid.getAndIncrement();
                        }

                    }
                }
        );

        System.out.println("Total " + total.get());
        System.out.println("Total valid " + totalValid.get());
        System.out.println("Total permutation valid " + totalPermutationValid.get());
    }

    private static Path getInputUri() throws URISyntaxException {
        return Paths.get(Puzzle4b.class.getClassLoader().getResource("2017/input4a.txt").toURI());
    }

    static class Word {
        private String word;

        Word(String word) {
            this.word = word;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Word word1 = (Word) o;
            return this.hashCode() == word1.hashCode();
        }

        @Override
        public int hashCode() {
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            return new String(chars).hashCode();
        }
    }

}
