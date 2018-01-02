package year2015.puzzle6.actions;

import org.apache.commons.lang3.StringUtils;

public abstract class Action {

    private int x1,y1,x2,y2;

    public Action(String from, String to){
        String[] fromSplit = StringUtils.split(from, ",");
        x1 = Integer.valueOf(fromSplit[0]);
        y1 = Integer.valueOf(fromSplit[1]);
        String[] toSplit = StringUtils.split(to, ",");
        x2 = Integer.valueOf(toSplit[0]);
        y2 = Integer.valueOf(toSplit[1]);
    }

    public void perform(int[][] lights){
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                lights[x][y] = doAction(lights[x][y]);
            }

        }
    }

    protected abstract int doAction(int i);
}
