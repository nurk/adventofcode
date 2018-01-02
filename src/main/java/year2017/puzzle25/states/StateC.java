package year2017.puzzle25.states;

import year2017.puzzle25.TuringMachine;

class StateC extends State {

    @Override
    State doOneLogic(TuringMachine turingMachine) {
        turingMachine.writeValue(1);
        turingMachine.toTheRight();
        return new StateA();
    }

    @Override
    State doZeroLogic(TuringMachine turingMachine) {
        turingMachine.writeValue(1);
        turingMachine.toTheRight();
        return new StateD();
    }
}
