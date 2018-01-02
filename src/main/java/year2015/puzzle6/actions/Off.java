package year2015.puzzle6.actions;

public class Off extends Action {
    public Off(String from, String to) {
        super(from, to);
    }

    @Override
    protected int doAction(int i) {
        //return 0;
        return Math.max(0, --i);
    }
}
