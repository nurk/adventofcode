package year2015.puzzle6.actions;

public class Toggle extends Action {
    public Toggle(String from, String to) {
        super(from, to);
    }

    @Override
    protected int doAction(int i) {
        //return Math.abs(i - 1);
        return i + 2;
    }
}
