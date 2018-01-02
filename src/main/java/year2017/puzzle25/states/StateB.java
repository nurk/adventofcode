package year2017.puzzle25.states;


import year2017.puzzle25.TuringMachine;

class StateB extends State {
    @Override
    State doOneLogic(TuringMachine turingMachine) {
        turingMachine.writeValue(0);
        turingMachine.toTheRight();
        return new StateD();
    }

    @Override
    State doZeroLogic(TuringMachine turingMachine) {
        turingMachine.writeValue(0);
        turingMachine.toTheLeft();
        return new StateA();
    }
}
