package bigdecimal;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BigDecimalCalculatorTest {

    @Test
    void parse() throws IOException {
        String formula = "(1 + 1.2) * 2 / 2";
        BigDecimalCalculator parse = BigDecimalCalculator.parse(formula, 28);
        System.out.println(parse.error());
        System.out.println(parse.value());
    }
}