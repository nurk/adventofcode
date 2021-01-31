package year2020.puzzle20;

import org.apache.commons.lang3.StringUtils;

public class Border {
    private String pattern = "";
    private boolean foundSame;

    public Border(String pattern) {
        this.pattern = pattern;
    }

    public void compare(Border other) {
        if (this.pattern.equals(other.pattern) || StringUtils.reverse(this.pattern).equals(other.pattern)) {
            this.setFoundSame(true);
            other.setFoundSame(true);
        }
    }

    public void setFoundSame(boolean foundSame) {
        this.foundSame = foundSame;
    }

    public boolean isFoundSame() {
        return foundSame;
    }
}
