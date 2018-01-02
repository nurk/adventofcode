package year2015.puzzle6.actions;

public class On extends Action {
    public On(String from, String to) {
        super(from, to);
    }

    @Override
    protected int doAction(int i) {
        //return 1;
        return ++i;
    }
}
