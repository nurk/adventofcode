package year2017.puzzle25.states;

import year2017.puzzle25.TuringMachine;

public abstract class State {
    public final State perform(TuringMachine turingMachine) {
        if (turingMachine.getCurrentValue() == 0) {
            return doZeroLogic(turingMachine);
        }
        return doOneLogic(turingMachine);
    }

    abstract State doOneLogic(TuringMachine turingMachine);

    abstract State doZeroLogic(TuringMachine turingMachine);

    public static State getInitialState() {
        return new StateA();
    }
}
