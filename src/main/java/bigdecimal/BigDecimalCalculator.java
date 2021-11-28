package bigdecimal;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalCalculator {
    private StreamTokenizer tokens;
    private int token;
    private BigDecimal value;
    private String error;
    private int decimals;

    private BigDecimalCalculator(String s, int decimals) {
        this.decimals = decimals;
        Reader reader = new StringReader(s);
        tokens = new StreamTokenizer(reader);
        tokens.resetSyntax();
        tokens.whitespaceChars(0, 32);
        tokens.wordChars('0', '9');
        tokens.wordChars('-', '.');
        //tokens.wordChars('+', '+');
        tokens.wordChars('a', 'z');
        tokens.wordChars('A', 'Z');
        tokens.slashSlashComments(true);
        tokens.slashStarComments(true);

        tokens.quoteChar('"');
        tokens.quoteChar('\'');
        tokens.ordinaryChar(Symbol.SLASH.toChar());

        getToken();
        value = expr();
        if (!tokenIs(Symbol.END)) {
            putError("syntax error");
        }
    }

    public static BigDecimalCalculator parse(String s, int decimals) {
        return new BigDecimalCalculator(s, decimals);
    }

    public boolean isValid() {
        return error == null;
    }

    public BigDecimal value() {
        return value.setScale(decimals, RoundingMode.HALF_UP);
    }

    public String error() {
        return error;
    }

    // expr = [addop] term {(addop) term} end
    private BigDecimal expr() {
        int sign = 1;
        accept(Symbol.PLUS);
        if (accept(Symbol.MINUS)) {
            sign = -1;
        }
        BigDecimal value = term().multiply(BigDecimal.valueOf(sign));
        while (Symbol.isAddOp(token)) {
            if (accept(Symbol.PLUS)) {
                value = value.add(term());
            }
            if (accept(Symbol.MINUS)) {
                value = value.subtract(term());
            }
        }
        return value;
    }

    // term = factor {(mulop) factor} end
    private BigDecimal term() {
        BigDecimal value = factor();
        while (Symbol.isMulOp(token)) {
            if (accept(Symbol.STAR)) {
                value = value.multiply(factor());
            }
            if (accept(Symbol.SLASH)) {
                value = value.divide(factor(), decimals, RoundingMode.HALF_UP);
            }
        }
        return value;
    }

    // factor = word | number | "(" expr ")" end
    private BigDecimal factor() {
        BigDecimal value = BigDecimal.ZERO;
        if (tokenIs(Symbol.WORD)) {
            try {
                value = new BigDecimal(tokens.sval);
                getToken();
            } catch (Exception e) {
                value = word();
            }
        } else if (accept(Symbol.OPEN)) {
            value = expr();
            expect(Symbol.CLOSE);
        } else {
            putError("factor error");
            getToken();
        }
        return value;
    }

    // word = name ["(" expr] ["," expr] ")"] end
    private BigDecimal word() {
        BigDecimal value = BigDecimal.ZERO;
        String name = tokens.sval;
        FunctionAdapter fa = Function.lookup(name);
        getToken();
        if (fa != null) {
            int count = fa.getCount();
            if (count == 0) {
                value = fa.eval();
            } else if (accept(Symbol.OPEN)) {
                BigDecimal[] args = new BigDecimal[count];
                for (int i = 0; i < count; i++) {
                    args[i] = expr();
                    if (i < count - 1) {
                        expect(Symbol.COMMA);
                    }
                }
                value = fa.eval(args);
                expect(Symbol.CLOSE);
            } else {
                putError("missing " + Symbol.OPEN.toChar());
            }
        } else {
            putError("undefined " + name);
        }
        return value;
    }

    /**
     * Fetch the next token in the stream.
     */
    private void getToken() {
        try {
            token = tokens.nextToken();
        } catch (IOException e) {
            putError("i/o error " + e.getMessage());
        }
    }

    /**
     * Return true if the current token matches the given symbol.
     */
    private boolean tokenIs(Symbol symbol) {
        return token == symbol.token();
    }

    /**
     * Require a matching symbol; gerate an error if it's unexpected.
     */
    private void expect(Symbol symbol) {
        if (accept(symbol)) {
            return;
        }
        putError("missing " + symbol.toChar());
    }

    /**
     * Advance if the current token matches the given symbol.
     */
    private boolean accept(Symbol symbol) {
        if (tokenIs(symbol)) {
            getToken();
            return true;
        }
        return false;
    }

    /**
     * Generate an error; ignore line numbers.
     */
    private void putError(String s) {
        if (error == null) {
            error = s + " at " + tokens.toString().replaceAll(",.*$", "");
        }
    }
}
