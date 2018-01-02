package year2017.puzzle25.states;

import year2017.puzzle25.TuringMachine;

class StateE extends State {
    @Override
    State doOneLogic(TuringMachine turingMachine) {
        turingMachine.writeValue(1);
        turingMachine.toTheLeft();
        return new StateB();
    }

    @Override
    State doZeroLogic(TuringMachine turingMachine) {
        turingMachine.writeValue(1);
        turingMachine.toTheRight();
        return new StateF();
    }
}
