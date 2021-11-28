package bigdecimal;

import java.math.BigDecimal;

public enum Function {

    E(new FunctionAdapter() {
        @Override
        BigDecimal eval(BigDecimal... args) {
            return BigDecimal.ZERO;
        }
    });

    private FunctionAdapter fa;

    /** Construct a Function with the specified adapter. **/
    private Function(FunctionAdapter fa) {
        this.fa = fa;
    }

    /** Return a Function's adapter by name; null if unknown. */
    public static FunctionAdapter lookup(String name) {
        Function f;
        try {
            f = Enum.valueOf(Function.class, name.toUpperCase());
        } catch (RuntimeException e) {
            return null;
        }
        return f.fa;
    }
}
