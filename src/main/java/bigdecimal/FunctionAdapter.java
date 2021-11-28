package bigdecimal;

import java.math.BigDecimal;

public abstract class FunctionAdapter {
    public int getCount() { return 1; }
    abstract BigDecimal eval(BigDecimal... args);
}
