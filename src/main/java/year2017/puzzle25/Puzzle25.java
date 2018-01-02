package year2017.puzzle25;

import year2017.puzzle25.states.State;

public class Puzzle25 {
    public static void main(String[] args) {
        int steps = 12368930;
        TuringMachine turingMachine = new TuringMachine(steps);
        State stateToPerform = State.getInitialState();

        for (int i = 0; i < steps; i++) {
            stateToPerform = stateToPerform.perform(turingMachine);
        }

        System.out.println(turingMachine.getChecksum());
    }
}
