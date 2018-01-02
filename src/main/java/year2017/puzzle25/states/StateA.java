package year2017.puzzle25.states;


import year2017.puzzle25.TuringMachine;

class StateA extends State {
    @Override
    State doOneLogic(TuringMachine turingMachine) {
        turingMachine.writeValue(0);
        turingMachine.toTheRight();
        return new StateC();
    }

    @Override
    State doZeroLogic(TuringMachine turingMachine) {
        turingMachine.writeValue(1);
        turingMachine.toTheRight();
        return new StateB();
    }
}
