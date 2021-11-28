package bigdecimal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BigDecimalCalculatorTest {

    @Test
    void parse() {
        String formula = "1.00000000000000000000001 - -1.2";
        BigDecimalCalculator parse = BigDecimalCalculator.parse(formula, 28);
        System.out.println(parse.error());
        System.out.println(parse.value());
    }
}