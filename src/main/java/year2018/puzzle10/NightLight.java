package year2018.puzzle10;

import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NightLight {
    @Getter
    private int x;
    @Getter
    private int y;
    private final int vX;
    private final int vY;

    public NightLight(String line) {
        // expected format: position=< 3, -2> velocity=<-1,  1>
        // parse four integers: x, y, vX, vY
        Pattern p = Pattern.compile(
                "position=<\\s*(-?\\d+),\\s*(-?\\d+)>\\s*velocity=<\\s*(-?\\d+),\\s*(-?\\d+)>"
        );
        Matcher m = p.matcher(line);
        if (!m.find()) {
            throw new IllegalArgumentException("Line not in expected format: " + line);
        }
        this.x = Integer.parseInt(m.group(1));
        this.y = Integer.parseInt(m.group(2));
        this.vX = Integer.parseInt(m.group(3));
        this.vY = Integer.parseInt(m.group(4));
    }

    public void move() {
        x += vX;
        y += vY;
    }

    @Override
    public String toString() {
        return "NightLight{pos=(" + x + "," + y + ") vel=(" + vX + "," + vY + ")}";
    }
}
