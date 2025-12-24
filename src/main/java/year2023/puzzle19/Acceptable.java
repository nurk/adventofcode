package year2023.puzzle19;


import java.util.Map;

public interface Acceptable {

    boolean isAcceptable(MachinePart machinePart, Map<String, Workflow> workflows);
}
