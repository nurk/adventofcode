package year2019.puzzle8;

import org.apache.commons.lang3.StringUtils;

public class Layer {
    private String numbers;

    public Layer(String numbers) {
        this.numbers = numbers;
    }

    private Long countDigits(String digit) {
        return numbers.chars()
                .mapToObj(c -> (char) c)
                .map(String::valueOf)
                .filter(digit::equals)
                .count();
    }

    public Long numberOfZeroDigits() {
        return countDigits("0");
    }

    public long getSolution() {
        return countDigits("1") * countDigits("2");
    }

    public String getPixel(int position) {
        return StringUtils.substring(numbers, position, position + 1);
    }
}
