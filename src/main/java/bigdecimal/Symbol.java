package bigdecimal;

import java.io.StreamTokenizer;

public enum Symbol {
    PLUS('+'), MINUS('-'), STAR('*'), SLASH('/'),
    OPEN('('), CLOSE(')'), COMMA(','),
    END(StreamTokenizer.TT_EOF),
    WORD(StreamTokenizer.TT_WORD),
    NUMBER(StreamTokenizer.TT_NUMBER);

    private int token;

    private Symbol(int token) {
        this.token = token;
    }

    /**
     * Return this symbol's token.
     */
    public int token() {
        return this.token;
    }

    /**
     * If printable, return character for this symbol's token.
     */
    public char toChar() {
        if (this.token < 32) {
            return '\ufffd';
        } else {
            return (char) this.token;
        }
    }

    /**
     * Return if the given token matches PLUS or MINUS.
     */
    public static boolean isAddOp(int token) {
        return token == PLUS.token || token == MINUS.token;
    }

    /**
     * Return if the given token matches STAR or SLASH.
     */
    public static boolean isMulOp(int token) {
        return token == STAR.token || token == SLASH.token;
    }
}
