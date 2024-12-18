package year2024.puzzle14;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class Robot {

    private final int x, y, vX, vY;

    private int pX, pY;

    private static final int height = 103; //7;
    private static final int width = 101; //11;

    public Robot(String line) {
        String[] split = line.split(" ");
        String p = StringUtils.remove(split[0], "p=");
        String[] pSplit = p.split(",");

        x = Integer.parseInt(pSplit[0]);
        y = Integer.parseInt(pSplit[1]);
        pX = x;
        pY = y;

        String v = StringUtils.remove(split[1], "v=");
        String[] vSplit = v.split(",");

        vX = Integer.parseInt(vSplit[0]);
        vY = Integer.parseInt(vSplit[1]);
    }

    public void tick() {
        pX = pX + vX;
        if (pX < 0) {
            pX = width + pX;
        } else {
            pX = pX % width;
        }

        pY = pY + vY;
        if (pY < 0) {
            pY = height + pY;
        } else {
            pY = pY % height;
        }
    }

    public int getQuadrant() {
        if (pX == (width - 1) / 2 || pY == (height - 1) / 2) {
            return -1;
        }
        if (pX <= (width - 1) / 2) {
            if (pY <= (height - 1) / 2) {
                return 1;
            } else {
                return 3;
            }
        } else {
            if (pY <= (height - 1) / 2) {
                return 2;
            } else {
                return 4;
            }
        }
    }

    public void reset() {
        this.pX = x;
        this.pY = y;
    }

    @Override
    public String toString() {
        return "Robot{" +
                "x=" + x +
                ", y=" + y +
                ", vX=" + vX +
                ", vY=" + vY +
                ", pX=" + pX +
                ", pY=" + pY +
                '}';
    }
}
