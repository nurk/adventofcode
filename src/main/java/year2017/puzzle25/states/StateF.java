package year2017.puzzle25.states;

import year2017.puzzle25.TuringMachine;

class StateF extends State {
    @Override
    State doOneLogic(TuringMachine turingMachine) {
        turingMachine.writeValue(1);
        turingMachine.toTheRight();
        return new StateE();
    }

    @Override
    State doZeroLogic(TuringMachine turingMachine) {
        turingMachine.writeValue(1);
        turingMachine.toTheRight();
        return new StateA();
    }
}
